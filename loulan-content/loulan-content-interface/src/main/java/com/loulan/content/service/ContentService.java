package com.loulan.content.service;

import com.loulan.pojo.TbContent;
import com.loulan.common.service.BaseService;

import java.util.List;

public interface ContentService extends BaseService<TbContent> {

    /**
     * 获取广告列表
     *
     * @param categoryId 分类Id
     * */
    List<TbContent> findByCategoryId(Long categoryId);

}
