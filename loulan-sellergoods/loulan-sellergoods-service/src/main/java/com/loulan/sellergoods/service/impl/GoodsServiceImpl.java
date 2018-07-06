package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.GoodsDescMapper;
import com.loulan.mapper.GoodsMapper;
import com.loulan.mapper.ItemMapper;
import com.loulan.mapper.SellerMapper;
import com.loulan.pojo.TbGoods;
import com.loulan.pojo.TbGoodsDesc;
import com.loulan.pojo.TbItem;
import com.loulan.pojo.TbSeller;
import com.loulan.sellergoods.service.GoodsDescService;
import com.loulan.sellergoods.service.GoodsService;
import com.loulan.sellergoods.service.ItemService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.Goods;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 商品管理，需要注意依赖 goodsDescService 与 ItemService 业务层，防止递归依赖。
 * */
@Service(interfaceClass = GoodsService.class)
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsDescService goodsDescService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * */
    @Override
    public Goods findOne(Long id) {
        /*
         * 1. 通过主键调用父类方法获取SPU
         * 2. 通过主键调用 desc-mapper 获取详情
         * 3. 通过主键调用 sku-mapper 获取sku集合
         * 4. 封装成复合对象返回
         * */
        // spu
        TbGoods goods = super.findOne(id);

        // desc
        TbGoodsDesc desc = goodsDescService.findOne(id);

        // sku
        TbItem item = new TbItem();
        item.setGoodsId(id);
        List<TbItem> items = itemService.findByWhere(item);

        return new Goods(goods, desc, items);
    }

    /**
     * 主键查询，增加商家权限验证
     *
     * @param  id        主键
     * @param  sellerId  商家主键
     * @return           复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * */
    @Override
    public Goods findOne(Long id, String sellerId) {
        Goods goods = findOne(id);

        // 商品所属商家与当前商家必须一致
        if(!goods.getGoods().getSellerId().equals(sellerId)) {
            throw new RuntimeException("没有权限访问该商品信息");
        }

        return goods;
    }

    /**
     * 分页sql条件查询，只查询spu
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象，封装了商品SPU集合
     */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, TbGoods t) {
        /*
         * 1. 创建条件对象
         * 2. 添加 isDelete notEqualTo 1 条件，过滤删除商品
         * 2. 添加 auditStatus equalTo 条件，商品状态
         * 3. 添加 sellerId equalTo 条件，所属商家
         * 4. 添加 goodsName like 条件，商品名称
         * 5. 调用父类方法分页查询
         * */
        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andNotEqualTo("isDelete", "1");

        if(!StringUtils.isEmpty(t.getAuditStatus())) {
            criteria.andEqualTo("auditStatus", t.getAuditStatus());
        }
        if(!StringUtils.isEmpty(t.getSellerId())) {
            criteria.andEqualTo("sellerId", t.getSellerId());
        }
        if(!StringUtils.isEmpty(t.getGoodsName())) {
            criteria.andLike("goodsName", "%" + t.getGoodsName() + "%");
        }

        return super.findPageByWhere(page, size, example);
    }

    /**
     * 添加，复合对象
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    @Override
    public void add(Goods t) {
        /*
         * 1. 添加SPU
         *  1.1 提取spu
         *  1.2 调用父类添加方法
         * 2. 添加详情
         *  2.1 提取详情
         *  2.2 设置详情主键为spu主键
         *  2.2 调用 desc-mapper 添加方法
         * 3. 添加SKU
         *  4.1 提取sku集合
         *  4.2 遍历调用sku-mapper 的添加方法
         * */
        // spu
        super.add(t.getGoods());

        // desc
        t.getGoodsDesc().setGoodsId(t.getGoods().getId());
        goodsDescService.add(t.getGoodsDesc());

        // sku
        itemService.addMore(t);
    }

    /**
     * 更新，复合对象
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    @Override
    public void update(Goods t) {
        /*
         * 1. 调用父类更新SPU
         * 2. 调用 desc-service 更新详情
         * 3. 删除SKU
         *  3.1 创建条件对象
         *  3.2 添加 goodsId equalTo 条件，所属商品SPU
         *  3.3 调用 sku-mapper 的条件删除方法
         * 4. 调用sku-service 的添加方法SKU
         * */
        // spu
        super.update(t.getGoods());

        // desc
        goodsDescService.update(t.getGoodsDesc());

        // sku - delete
        TbItem item = new TbItem();
        item.setGoodsId(t.getGoods().getId());
        itemMapper.delete(item);

        // sku - add
        itemService.addMore(t);
    }

    /**
     * 批量更新，只更新spu
     *
     * @param  t    SPU实体对象，封装了审核状态，1：已删除 2：未审核 3：通过审核
     * @param  ids  主键集合
     */
    @Override
    public void updateMore(TbGoods t, Long[] ids) throws RuntimeException {

        // 状态值错误检测
        String status = t.getAuditStatus();
        if(!"1".equals(status) && !"2".equals(status) && !"3".equals(status)) {
            throw new RuntimeException("状态错误");
        }

        super.updateMore(t, ids);
    }

    /**
     * 批量删除，只删除spu，实际上是修改状态假删除
     *
     * @param  ids  主键集合
     */
    public void deleteMore(Long[] ids) {
        TbGoods goods = new TbGoods();
        goods.setAuditStatus("1");
        updateMore(goods, ids);
    }

}
