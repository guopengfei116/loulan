package com.loulan.service;

import com.loulan.vo.PageResult;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     实体对象
     * */
    T findOne(Serializable id);

    /**
     * 查询所有
     *
     * @return 实体对象集合
     * */
    List<T> findAll();

    /**
     * 实体条件查询
     *
     * @param  t  实体对象，封装了查询条件
     * @return    实体对象集合
     * */
    List<T> findByWhere(T t);

    /**
     * sql条件查询
     *
     * @param  example  条件对象
     * @return          实体对象集合
     * */
    List<T> findByWhere(Example example);

    /**
     * 分页查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @return       分页实体对象
     * */
    PageResult findPage(Integer page, Integer size);

    /**
     * 分页实体条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     * */
    PageResult findPageByWhere(Integer page, Integer size, T t);

    /**
     * 分页sql条件查询
     *
     * @param  page     页码
     * @param  size     页大小
     * @param  example  条件对象
     * @return          分页实体对象
     * */
    PageResult findPageByWhere(Integer page, Integer size, Example example);

    /**
     * 添加
     *
     * @param t 实体对象
     * */
    void add(T t);

    /**
     * 批量添加
     *
     * @param ts 实体对象集合
     * */
    void addMore(List<T> ts);

    /**
     * 修改
     *
     * @param t 实体对象
     * */
    void update(T t);

    /**
     * 批量修改
     *
     * @param ts 实体对象集合
     * */
    void updateMore(List<T> ts);

    /**
     * 删除
     *
     * @param id 主键
     * */
     void delete(Serializable id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * */
    void deleteMore(Serializable[] ids);
}
