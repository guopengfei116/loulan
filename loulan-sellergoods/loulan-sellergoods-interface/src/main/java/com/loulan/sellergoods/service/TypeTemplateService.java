package com.loulan.sellergoods.service;

import com.loulan.pojo.TbTypeTemplate;
import com.loulan.service.BaseService;
import com.loulan.vo.PageResult;

public interface TypeTemplateService extends BaseService<TbTypeTemplate> {
    /**
     * 分页搜索
     */
    PageResult searchPage(Integer page, Integer size, TbTypeTemplate query);
}
