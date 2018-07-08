package com.loulan.content.service;

import com.loulan.pojo.TbContent;
import com.loulan.common.service.BaseService;
import com.loulan.pojo.TbContentCategory;
import com.loulan.vo.PageResult;

import java.util.List;

public interface ContentService extends BaseService<TbContent> {

    /**
     * 获取广告列表
     *
     * @param categoryId 分类Id
     * */
    List<TbContent> findByCategoryId(Long categoryId);

    /**
     * 分页sql条件查询
     *
     * @param  page      页码
     * @param  size      页大小
     * @param  category  内容分类实体对象
     * @return           分页实体对象
     */
    PageResult findPageByWhere(Integer page, Integer size, TbContentCategory category);

}
