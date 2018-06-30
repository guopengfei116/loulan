package com.loulan.mapper;

import com.loulan.pojo.TbSpecification;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper extends Mapper<TbSpecification> {
    /**
     * 规格下拉列表
     * */
    List<Map<String, String>> selectOptionList();
}
