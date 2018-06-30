package com.loulan.sellergoods.service;

import com.loulan.pojo.TbBrand;
import com.loulan.service.BaseService;
import com.loulan.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {

    /**
    * 搜索
    * */
    List<TbBrand> search(TbBrand query);

    /**
     * 分页搜索
     * */
    PageResult searchPage(Integer page, Integer size, TbBrand query);

    /**
     * 品牌下拉列表
     * */
    List<Map<String, String>> selectOptionList();
}
