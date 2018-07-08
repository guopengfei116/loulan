package com.loulan.sellergoods.service;

import com.loulan.pojo.TbItem;
import com.loulan.common.service.BaseService;
import com.loulan.vo.Goods;

public interface ItemService extends BaseService<TbItem> {

    /**
     * 批量添加
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    void addMore(Goods t);

    /**
     * 批量修改
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    void updateMore(Goods t);

}
