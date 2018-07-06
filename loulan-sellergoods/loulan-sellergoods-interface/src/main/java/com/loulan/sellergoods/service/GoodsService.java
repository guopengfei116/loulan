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
     * 主键查询，增加商家权限验证
     *
     * @param  id        主键
     * @param  sellerId  商家主键
     * @return           复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * */
    Goods findOne(Long id, String sellerId);

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
     * 更新，复合对象，增加商家权限验证
     *
     * @param  t         复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * @param  sellerId  商家主键
     * */
    void update(Goods t, String sellerId);

    /**
     * 批量更新SPU
     *
     * @param  t    SPU实体对象，封装了审核状态，1：已删除 2：未审核 3：通过审核
     * @param  ids  主键集合
     */
    void updateMore(TbGoods t, Long[] ids) throws RuntimeException;

    /**
     * 批量更新，只更新spu，只修改属于商家自己的商品
     *
     * @param  t         复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * @param  ids       主键集合
     * @param  sellerId  商家主键
     * */
    void updateMore(TbGoods t, Long[] ids, String sellerId) throws RuntimeException;

    /**
     * 批量删除，只删除spu，实际上是修改状态假删除
     *
     * @param  ids  主键集合
     */
    void deleteMore(Long[] ids);

    /**
     * 批量删除，只删除spu，实际上是修改状态假删除，但是只能删除属于商家自己的商品
     *
     * @param  ids       主键集合
     * @param  sellerId  商家主键
     * */
    void deleteMore(Long[] ids, String sellerId);

}
