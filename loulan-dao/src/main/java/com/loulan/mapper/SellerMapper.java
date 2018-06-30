package com.loulan.mapper;

import com.loulan.pojo.TbSeller;
import tk.mybatis.mapper.common.Mapper;

public interface SellerMapper extends Mapper<TbSeller> {
    /**
     * 更新状态
     *
     * @param id 主键
     * @param status   状态，0：未审核 1：已审核 2：审核未通过 3：关闭
     */
    void updateStatus(String id, String status);
}
