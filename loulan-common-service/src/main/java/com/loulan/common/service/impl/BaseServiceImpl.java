package com.loulan.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loulan.common.service.BaseService;
import com.loulan.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    private String primaryKeyName = "id";  // 实体对象主键名称
    private Class<T> tClass;               // 描述实体类的Class类型对象

    public BaseServiceImpl() {
        tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Id.class)) {
                primaryKeyName = field.getName();
            }
        }
    }

    @Autowired
    private Mapper<T> mapper;

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     实体对象
     * */
    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 批量主键查询
     *
     * @param  ids  主键集合
     * @return      实体集合
     * */
    @Override
    public List<T> findMore(Serializable[] ids) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();

        criteria.andIn(primaryKeyName, Arrays.asList(ids));
        mapper.deleteByExample(example);

        return mapper.selectByExample(example);
    }

    /**
     * 查询所有
     *
     * @return 实体对象集合
     */
    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    /**
     * 实体条件查询
     *
     * @param t 实体对象，封装了查询条件
     * @return  实体对象集合
     */
    @Override
    public List<T> findByWhere(T t) {
        return mapper.select(t);
    }

    /**
     * sql条件查询
     *
     * @param example 条件对象
     * @return        实体对象集合
     */
    @Override
    public List<T> findByWhere(Example example) {
        return mapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @return       分页实体对象
     */
    @Override
    public PageResult findPage(Integer page, Integer size) {
        /*
        * 1. 设置分页参数
        * 2. 分页查询
        * 3. 查询结果包装成分页对象
        * 4. 转换为自定义分页对象
        * */
        PageHelper.startPage(page, size);
        List<T> list = mapper.selectAll();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 分页实体条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, T t) {

        // 没有条件返回全部
        if(t == null) {
            return findPage(page, size);
        }

        /*
         * 1. 设置分页参数
         * 2. 分页实体条件查询
         * 3. 查询结果包装成分页对象
         * 4. 转换为自定义分页对象
         * */
        PageHelper.startPage(page, size);
        List<T> list = mapper.select(t);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 分页sql条件查询
     *
     * @param  page     页码
     * @param  size     页大小
     * @param  example  条件对象
     * @return          分页实体对象
     */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, Example example) {

        // 没有条件返回全部
        if(example == null) {
            return findPage(page, size);
        }

        /*
         * 1. 设置分页参数
         * 2. 分页sql条件查询
         * 3. 查询结果包装成分页对象
         * 4. 转换为自定义分页对象
         * */
        PageHelper.startPage(page, size);
        List<T> list = mapper.selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加
     *
     * @param t 实体对象
     */
    @Override
    public void add(T t) {
        mapper.insertSelective(t);
    }

    /**
     * 批量添加
     *
     * @param ts 实体对象集合
     */
    @Override
    public void addMore(List<T> ts) {
        for (T t : ts) {
            add(t);
        }
    }

    /**
     * 修改
     *
     * @param t 实体对象
     */
    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 批量修改
     *
     * @param ts 实体对象集合
     */
    @Override
    public void updateMore(List<T> ts) {
        for (T t : ts) {
            update(t);
        }
    }

    /**
     * 批量修改
     *
     * @param t   实体对象
     * @param ids 主键集合
     */
    @Override
    public void updateMore(T t, Serializable[] ids) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();

        criteria.andIn(primaryKeyName, Arrays.asList(ids));
        mapper.updateByExampleSelective(t, example);
    }

    /**
     * 删除
     *
     * @ param id 主键
     */
    @Override
    public void delete(Serializable id) {
        mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids 主键集合
     */
    @Override
    public void deleteMore(Serializable[] ids) {
        /*
         * 1. 通过反射注解拿到主键名称
         *  1.1 先拿到实体类的class
         *  1.2 再拿到实体类所有的属性对象
         *  1.3 遍历属性对象
         *  1.4 拥有Id注解的属性就是主键
         * 2. 为了删除效率创建删除条件
         * 3. 添加 主键名称 in 条件
         * 4. 调用mapper删除方法
         * */
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();

        criteria.andIn(primaryKeyName, Arrays.asList(ids));
        mapper.deleteByExample(example);
    }

}
