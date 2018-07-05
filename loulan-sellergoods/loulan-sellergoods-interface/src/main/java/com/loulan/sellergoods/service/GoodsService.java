package com.loulan.sellergoods.service;

import com.loulan.pojo.TbGoods;
import com.loulan.service.BaseService;
import com.loulan.vo.Goods;

public interface GoodsService extends BaseService<TbGoods> {

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * */
    Goods findOne(Long id);

    /**
     * 添加
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    void add(Goods t);

    /**
     * 更新
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    void update(Goods t);

    /**
     * 批量更新SPU
     *
     * @param  t    SPU实体对象，封装了审核状态，1：已删除 2：未审核 3：通过审核
     * @param  ids  主键集合
     */
    void updateMore(TbGoods t, Long[] ids) throws RuntimeException;

}
