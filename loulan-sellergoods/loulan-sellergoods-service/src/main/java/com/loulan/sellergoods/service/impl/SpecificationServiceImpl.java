package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.SpecificationMapper;
import com.loulan.mapper.SpecificationOptionMapper;
import com.loulan.pojo.TbSpecification;
import com.loulan.pojo.TbSpecificationOption;
import com.loulan.sellergoods.service.SpecificationService;
import com.loulan.common.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import com.loulan.vo.Specification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 主键查询
     *
     * @param id 规格主键
     * @return   复合规格对象，包含规格与规格选项
     */
    @Override
    public Specification findOne(Long id) {
        /*
         * 1. 通过主键查询规格
         * 2. 通过主键查询规格选项集合
         *  2.1 为了提供查询效率创建查询条件
         *  2.2 添加 specId equalTo 条件
         *  2.3 使用选项mapper查询
         * 3. 包装成复合对象返回
         * */
        TbSpecification spec = super.findOne(id);

        Example example = new Example(TbSpecificationOption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("specId", id);
        List<TbSpecificationOption> optionList = specificationOptionMapper.selectByExample(example);

        return new Specification(spec, optionList);
    }

    /**
     * 分页sql条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, TbSpecification t) {
        /*
         * 1. 创建条件对象
         * 2. 添加 specName like 条件
         * 3. 调用父类方法分页查询
         * */
        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(t.getSpecName())) {
            criteria.andLike("specName", "%" + t.getSpecName() + "%");
        }

        return super.findPageByWhere(page, size, example);
    }

    /**
     * 添加
     *
     * @param t 复合规格对象，包含规格与规格选项
     * */
    @Override
    public void add(Specification t) {
        /*
         * 1. 持久化规格对象
         *  1.1 提取规格对象
         *  1.2 调用父类添加方法
         * 2. 持久化规格选项对象
         *  2.1 提取规格选项集合对象
         *  2.2 遍历选项集合
         *  2.3 设置选项对象的specId为规格对象Id
         *  2.4 调用选项mapper添加方法
         * */
        TbSpecification spec = t.getSpecification();
        super.add(spec);

        List<TbSpecificationOption> optionList = t.getSpecificationOptionList();
        if(optionList != null && optionList.size() > 0) {
            for (TbSpecificationOption option : optionList) {
                option.setSpecId(spec.getId());
                specificationOptionMapper.insertSelective(option);
            }
        }
    }

    /**
     * 更新
     *
     * @param t 复合规格对象，包含规格与规格选项
     */
    @Override
    public void update(Specification t) {
        /*
         * 1. 持久化规格
         *  1.1 提取规格
         *  1.2 调用父类添加方法
         * 2. 删除旧规格选项
         *  2.1 为了删除效率创建删除条件
         *  2.2 添加 specId equalTo 条件
         *  2.3 调用选项mapper删除方法
         * 3. 添加新选项
         *  3.1 提取选项集合
         *  3.2 遍历选项集合
         *  3.3 设置选项的specId为规格对象Id
         *  3.4 调用选项mapper添加方法
         * */
        TbSpecification spec = t.getSpecification();
        super.update(spec);

        // 删除旧规格选项
        Example example = new Example(TbSpecificationOption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("specId", spec.getId());
        specificationOptionMapper.deleteByExample(example);

        // 添加新规格选项
        List<TbSpecificationOption> list = t.getSpecificationOptionList();
        for (TbSpecificationOption option : list) {
            option.setSpecId(spec.getId());
            specificationOptionMapper.insertSelective(option);
        }
    }

    /**
     * 删除
     *
     * @param ids 规格主键集合
     */
    @Override
    public void deleteMore(Serializable[] ids) {
        /*
         * 1. 调用父类方法删除规格
         * 2. 删除规格选项
         *  2.1 为了删除效率创建删除条件
         *  2.2 添加 specId equalTo 条件
         *  2.3 调用选项mapper删除方法
         * */
        super.deleteMore(ids);  // 参数类型一样，记得加super，否则递归死循环

        // 删除规格选项
        Example example = new Example(TbSpecificationOption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("specId", Arrays.asList(ids));
        specificationOptionMapper.deleteByExample(example);
    }

    /**
     * 实体下拉列表
     *
     * @return 实体id与specName(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    @Override
    public List<Map<String, String>> selectOptionList() {
        return specificationMapper.selectOptionList();
    }

}
