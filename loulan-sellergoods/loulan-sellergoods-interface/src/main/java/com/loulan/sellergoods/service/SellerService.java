package com.loulan.sellergoods.service;

import com.loulan.pojo.TbSeller;
import com.loulan.service.BaseService;
import com.loulan.vo.PageResult;

public interface SellerService extends BaseService<TbSeller> {
    /**
     * 分页搜索
     *
     * @param page 页码
     * @param size 每页数量
     * @param query 品牌查询实体
     */
    PageResult searchPage(Integer page, Integer size, TbSeller query);

    /**
     * 更新状态
     *
     * @param sellerId 主键
     * @param status 状态，0：未审核 1：已审核 2：审核未通过 3：关闭
     * */
    void updateStatus(String sellerId, String status) throws Exception;
}
