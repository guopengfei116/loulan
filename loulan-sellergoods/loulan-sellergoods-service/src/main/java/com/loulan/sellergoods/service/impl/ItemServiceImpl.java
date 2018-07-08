package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.loulan.mapper.BrandMapper;
import com.loulan.mapper.ItemCatMapper;
import com.loulan.mapper.ItemMapper;
import com.loulan.mapper.SellerMapper;
import com.loulan.pojo.TbGoods;
import com.loulan.pojo.TbGoodsDesc;
import com.loulan.pojo.TbItem;
import com.loulan.sellergoods.service.ItemService;
import com.loulan.common.service.impl.BaseServiceImpl;
import com.loulan.vo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = ItemService.class)
public class ItemServiceImpl extends BaseServiceImpl<TbItem> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 设置sku标题
     *
     * @param item sku
     * @param t    复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    private void setItemTitle(TbItem item, Goods t) {
        /*
        * 1. 提取spu名称
        * 2. 提取sku的规格选项，解析成map，然后遍历获取不同规格value
        * 3. 上面的值累加作为title，使用空格隔离
        * */
        String title = t.getGoods().getGoodsName();

        Map map = JSON.parseObject(item.getSpec(), Map.class);
        if(map != null) {
            for (Object o : map.values()) {
                title = title.concat(o.toString());
            }
        }

        item.setTitle(title);
    }

    /**
     * 设置sku基本信息
     *
     * @param item sku
     * @param t    复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    private void setItemValue(TbItem item, Goods t) {
        /*
        * 1. 设置商家id与名称，来自spu的sellerId字段，名称去查seller表name字段
        * 2. 设置品牌名称，通过spu的brandId字段，去查brand表name字段
        * 3. 设置商品id，来自spu的id字段
        * 4. 设置3级分类id与名称，来自spu的category3Id字段，名称去查item_cat表name字段
        * 5. 设置封面图，来自desc的itemImages字段
        * 6. 设置创建时间与更新时间
        * */
        TbGoods goods = t.getGoods();
        TbGoodsDesc desc = t.getGoodsDesc();

        // 商家
        item.setSellerId(goods.getSellerId());
        item.setSeller(sellerMapper.selectByPrimaryKey(goods.getSellerId()).getName());

        // 品牌
        item.setBrand(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName());

        // 商品
        item.setGoodsId(goods.getId());

        // 分类
        item.setCategoryid(goods.getCategory3Id());
        item.setCategory(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());

        // 图片
        List<Map> list = JSON.parseArray(desc.getItemImages(), Map.class);
        if(list != null && list.size() > 0) {
            item.setImage(list.get(0).get("url").toString());
        }

        // 时间
        Date date = new Date();
        item.setUpdateTime(date);
        item.setCreateTime(date);
    }

    /**
     * 批量添加sku
     *
     * @param t 复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    @Override
    public void addMore(Goods t) {
        /*
         * 1. 判断spu的规格启用状态
         * 2. 启用，遍历sku添加
         * 3. 未启用，创建一个默认sku添加
         * 4. 添加sku时候，需要生成标题，并从spu与desc中提取部分信息保存
         * */
        String isEnableSpec = t.getGoods().getIsEnableSpec();

        // 启用规格，遍历添加
        if ("1".equals(isEnableSpec)) {

            List<TbItem> list = t.getItemList();
            if (list != null && list.size() > 0) {
                for (TbItem item : list) {

                    setItemTitle(item, t);
                    setItemValue(item, t);
                    super.add(item);
                }
            }

        // 未启用规格，创建默认sku添加
        } else {
            TbItem item = new TbItem();

            // 补充4个由用户填写的sku项
            item.setIsDefault("1");
            item.setStatus("0");
            item.setPrice(t.getGoods().getPrice());
            item.setNum(9999);

            setItemTitle(item, t);
            setItemValue(item, t);
            super.add(item);
        }
    }

    /**
     * 批量修改sku
     *
     * @param t 复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    @Override
    public void updateMore(Goods t) {

    }

}
