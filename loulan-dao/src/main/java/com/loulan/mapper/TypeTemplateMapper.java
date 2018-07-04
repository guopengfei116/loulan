package com.loulan.mapper;

import com.loulan.pojo.TbTypeTemplate;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TypeTemplateMapper extends Mapper<TbTypeTemplate> {

    /**
     * 实体下拉列表
     *
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    List<Map<String,String>> selectOptionList();

}
