package com.loulan.mapper;

import com.loulan.pojo.TbSpecification;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper extends Mapper<TbSpecification> {

    /**
     * 实体下拉列表
     *
     * @return 实体id与specName(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    List<Map<String, String>> selectOptionList();

}
