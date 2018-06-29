package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loulan.mapper.SpecificationMapper;
import com.loulan.mapper.SpecificationOptionMapper;
import com.loulan.pojo.TbSpecification;
import com.loulan.pojo.TbSpecificationOption;
import com.loulan.sellergoods.service.SpecificationService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import com.loulan.vo.Specification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 搜索
     *
     * @param query 品牌查询实体
     */
    @Override
    public List<TbSpecification> search(TbSpecification query) {
        // 创建自定义sql条件
        Example example = new Example(TbSpecification.class);

        // 添加搜索条件
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(query.getSpecName()))
            criteria.andLike("specName", "%" + query.getSpecName() + "%");

        // 返回搜索结果
        return specificationMapper.selectByExample(example);
    }

    /**
     * 分页搜索
     *
     * @param page 页码
     * @param size 每页数量
     * @param query 品牌查询实体
     */
    @Override
    public PageResult searchPage(Integer page, Integer size, TbSpecification query) {
        // 设置分页
        PageHelper.startPage(page, size);

        // 分页查询
        List<TbSpecification> list = search(query);

        // 获取分页相关信息
        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 主键查找
     *
     * @param id
     */
    @Override
    public Specification findOne(Long id) {
        // 1. 查找规格
        TbSpecification spec = super.findOne(id);

        // 2. 查找选项
        Example example = new Example(TbSpecificationOption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("specId", id);
        List<TbSpecificationOption> optionList = specificationOptionMapper.selectByExample(example);

        return new Specification(spec, optionList);
    }

    /**
     * 添加
     *  @param specification
     * */
    @Override
    public void add(Specification specification) {
        // 1. 持久化规格对象
        TbSpecification spec = specification.getSpecification();
        add(spec);

        // 2. 持久化选项对象
        List<TbSpecificationOption> list = specification.getSpecificationOptionList();
        if(list != null && list.size() > 0) {
            for (TbSpecificationOption option : list) {
                option.setSpecId(spec.getId());                        // 设置选项对应的规格ID
                specificationOptionMapper.insertSelective(option);     // 持久化存储
            }
        }
    }

    /**
     * 更新
     *
     * @param specification
     */
    @Override
    public void update(Specification specification) {
        // 1. 更新规则对象
        TbSpecification spec = specification.getSpecification();
        update(spec);

        // 2. 删除规格选项
        Example example = new Example(TbSpecificationOption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("specId", spec.getId());
        specificationOptionMapper.deleteByExample(example);

        // 3. 持久化新的规则选项
        List<TbSpecificationOption> list = specification.getSpecificationOptionList();
        for (TbSpecificationOption option : list) {
            option.setSpecId(spec.getId());
            specificationOptionMapper.insertSelective(option);
        }
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(Serializable[] ids) {
        // 1. 删除规格
        super.deleteByIds(ids);  // 参数类型一样，记得加super，否则递归死循环

        // 2. 删除选项
        Example example = new Example(TbSpecificationOption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("specId", Arrays.asList(ids));
        specificationOptionMapper.deleteByExample(example);
    }

}
