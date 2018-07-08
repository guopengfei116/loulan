package com.loulan.sellergoods.service;

import com.loulan.pojo.TbBrand;
import com.loulan.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {

    /**
     * 实体下拉列表
     *
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    List<Map<String, String>> selectOptionList();

}
