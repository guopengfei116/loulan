package com.loulan.sellergoods.service;

import com.loulan.pojo.TbSpecification;
import com.loulan.service.BaseService;
import com.loulan.vo.PageResult;
import com.loulan.vo.Specification;

import java.io.Serializable;
import java.util.List;

public interface SpecificationService extends BaseService<TbSpecification> {

    /**
     * 搜索
     * */
    List<TbSpecification> search(TbSpecification query);

    /**
     * 分页搜索
     * */
    PageResult searchPage(Integer page, Integer size, TbSpecification query);

    /**
     * 主键查找
     * */
    Specification findOne(Long id);

    /**
     * 添加
     * */
    void add(Specification specification);

    /**
     * 更新
     * */
    void update(Specification specification);

    /**
     * 删除
     * */
    void deleteByIds(Serializable[] ids);

}