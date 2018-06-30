package com.loulan.mapper;

import com.loulan.pojo.TbBrand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<TbBrand> {
    /**
     * 品牌下拉列表
     * */
    List<Map<String, String>> selectOptionList();
}
