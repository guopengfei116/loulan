package com.loulan.sellergoods.service;

import com.loulan.pojo.TbTypeTemplate;
import com.loulan.service.BaseService;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService extends BaseService<TbTypeTemplate> {

    /**
     * 实体模板下拉列表
     *
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    List<Map<String,String>> selectOptionList();

}
