package com.loulan.sellergoods.service;

import com.loulan.pojo.TbBrand;
import com.loulan.service.BaseService;
import com.loulan.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {

    /**
     * 品牌下拉列表
     *
     * @return 规格id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     * */
    List<Map<String, String>> selectOptionList();

}
