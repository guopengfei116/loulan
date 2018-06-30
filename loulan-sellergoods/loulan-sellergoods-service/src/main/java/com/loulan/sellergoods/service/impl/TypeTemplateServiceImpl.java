package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.pojo.TbTypeTemplate;
import com.loulan.sellergoods.service.TypeTemplateService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

@Service(interfaceClass = TypeTemplateService.class)
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService {
    /**
     * 分页搜索
     *
     * @param page 页码
     * @param size 每页数量
     * @param query 查询实体
     */
    public PageResult searchPage(Integer page, Integer size, TbTypeTemplate query) {
        // 查询对象
        Example example = new Example(TbTypeTemplate.class);

        // 添加搜索条件
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(query.getName()))
            criteria.andLike("name", "%" + query.getName() + "%");

        // 返回分页数据
        return super.searchPage(page, size, example);
    }
}
