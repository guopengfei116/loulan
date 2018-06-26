package com.loulan.service;

import com.loulan.vo.PageResult;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 实体对象
     * */
    T findOne(Serializable id);

    /**
     * 查询全部
     *
     * @return 实体对象集合
     * */
    List<T> findAll();

    /**
     * 条件查询
     *
     * @param query 查询条件实体对象
     * @return 实体对象
     * */
    List<T> findByWhere(T query);

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页实体对象
     * */
    PageResult findPage(Integer page, Integer size);

    /**
     * 分页条件查询
     *
     * @param page 页码
     * @param size 每页大小
     * @param query 查询条件实体对象
     * @return 分页实体对象
     * */
    PageResult findPage(Integer page, Integer size, T query);

    /**
     * 添加实体
     *
     * @param t 实体对象
     * */
    void add(T t);

    /**
     * 更新实体
     *
     * @param t 实体对象
     * */
    void update(T t);

    /**
     * 删除实体
     *
     * @param ids 主键列表
     * */
     void deleteByIds(Serializable[] ids);
}
