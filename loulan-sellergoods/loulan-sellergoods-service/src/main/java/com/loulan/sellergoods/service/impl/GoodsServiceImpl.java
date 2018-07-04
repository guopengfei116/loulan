package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.GoodsDescMapper;
import com.loulan.mapper.GoodsMapper;
import com.loulan.pojo.TbGoods;
import com.loulan.sellergoods.service.GoodsService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.Goods;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service(interfaceClass = GoodsService.class)
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

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
        return new Goods();
    }

    /**
     * 分页sql条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, TbGoods t) {
        /*
         * 1. 创建条件对象
         * 2. 添加 auditStatus equalTo 条件
         * 3. 添加 sellerId equalTo 条件
         * 4. 添加 goodsName like 条件
         * 5. 调用父类方法分页查询
         * */
        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();

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
     * 添加
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
         *  2.2 调用 desc-mapper 添加方法
         * 3. 添加SKU
         *  4.1 提取sku集合
         *  4.2 遍历调用sku-mapper 的添加方法
         * */
    }

    /**
     * 更新
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    @Override
    public void update(Goods t) {
        /*
         * 1. 更新SPU
         *  1.1 提取SPU
         *  1.2 调用父类更新方法
         * 2. 更新详情
         *  2.1 提取详情
         *  2.2 调用 desc-mapper 更新方法
         * 3. 删除SKU
         *  3.1 创建条件对象
         *  3.2 添加 goodsId equalTo 条件
         *  3.3 调用 sku-mapper 的条件删除方法
         * 4. 添加SKU
         *  4.1 提取sku集合
         *  4.2 遍历调用sku-mapper 的添加方法
         * */
    }

    /**
     * 批量更新状态
     *
     * @param  ids     主键集合
     * @param  status  商品审核状态
     */
    @Override
    public void updateMoreStatus(Long[] ids, String status) {
        /*
         * 修改状态为0
         * */
    }

    /**
     * 批量删除，假删除，把状态改为0
     *
     * @param  ids  主键集合
     */
    public void deleteMore(Long[] ids) {
        updateMoreStatus(ids, "0");
    }

}
