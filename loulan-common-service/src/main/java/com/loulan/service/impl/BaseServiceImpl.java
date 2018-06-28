package com.loulan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loulan.service.BaseService;
import com.loulan.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 实体对象
     */
    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询全部
     *
     * @return 实体对象集合
     */
    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    /**
     * 条件查询
     *
     * @param query 查询条件实体对象
     * @return 实体对象
     */
    @Override
    public List<T> findByWhere(T query) {
        return mapper.select(query);
    }

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页实体对象
     */
    @Override
    public PageResult findPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<T> list = mapper.selectAll();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 分页条件查询
     * 通过反射获取实体属性，全部使用like模糊查询
     *
     * @param page  页码
     * @param size  每页大小
     * @param query 查询条件实体对象
     * @return 分页实体对象
     */
    @Override
    public PageResult findPage(Integer page, Integer size, T query) {
        PageHelper.startPage(page, size);
        List<T> list = mapper.select(query);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加实体
     *
     * @param t 实体对象
     */
    @Override
    public void add(T t) {
        mapper.insertSelective(t);
    }

    /**
     * 更新实体
     *
     * @param t 实体对象
     */
    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 删除实体
     *
     * @param ids 主键列表
     */
    @Override
    public void deleteByIds(Serializable[] ids) {
        if(ids != null && ids.length > 0) {
            for (Serializable id : ids) {
                mapper.deleteByPrimaryKey(id);
            }
        }
    }
}
