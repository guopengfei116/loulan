package com.loulan.content.service;

import com.loulan.pojo.TbContent;
import com.loulan.common.service.BaseService;
import com.loulan.pojo.TbContentCategory;
import com.loulan.vo.PageResult;

import java.util.List;

public interface ContentService extends BaseService<TbContent> {

    /**
     * 获取分类下内容列表
     *
     * @param categoryId 分类Id
     * */
    List<TbContent> findByCategoryId(Long categoryId);

}
