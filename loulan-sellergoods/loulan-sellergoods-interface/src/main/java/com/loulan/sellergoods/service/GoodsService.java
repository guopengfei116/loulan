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
     * 主键查询
     * 增加商家权限验证
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
     * 更新，复合对象
     * 更新后的商品重新设置为未审核
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     */
    void update(Goods t);

    /**
     * 更新，复合对象
     * 增加商家权限验证，更新后的商品重新设置为未审核
     *
     * @param  t         复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * @param  sellerId  商家主键
     * */
    void update(Goods t, String sellerId);

    /**
     * 批量更新spu状态
     * 提供给运营商使用，禁止商家系统调用
     *
     * @param  status  审核状态，1：未审核 2：通过审核 3. 已驳回
     * @param  ids     主键集合
     */
    void updateStatusMore(String status, Long[] ids);

    /**
     * 批量更新spu
     *
     * @param  t    spu实体对象
     * @param  ids  主键集合
     */
    void updateMore(TbGoods t, Long[] ids) throws RuntimeException;

    /**
     * 批量更新spu
     * 增加商家权限的判断
     *
     * @param  t         SPU实体对象，封装了审核状态，1：未审核 2：通过审核 3. 已驳回
     * @param  ids       主键集合
     * @param  sellerId  商家主键
     * */
    void updateMore(TbGoods t, Long[] ids, String sellerId) throws RuntimeException;

    /**
     * 批量删除spu，假删除，修改isDelete状态实现，0：未删 1：已删
     *
     * @param  ids  主键集合
     */
    void deleteMore(Long[] ids);

    /**
     * 批量删除spu，假删除，修改isDelete状态实现，0：未删 1：已删
     * 增加商家权限的判断
     *
     * @param  ids       主键集合
     * @param  sellerId  商家主键
     * */
    void deleteMore(Long[] ids, String sellerId);

}
