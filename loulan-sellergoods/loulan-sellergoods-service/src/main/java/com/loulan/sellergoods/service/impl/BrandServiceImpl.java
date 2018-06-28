package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loulan.mapper.BrandMapper;
import com.loulan.pojo.TbBrand;
import com.loulan.sellergoods.service.BrandService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

// 使用阿里巴巴的注解注册服务
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 搜索
     *
     * @param query 品牌查询实体
     */
    @Override
    public List<TbBrand> search(TbBrand query) {
        // 创建自定义sql条件
        Example example = new Example(TbBrand.class);

        // 添加搜索条件
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(query.getFirstChar()))
            criteria.andEqualTo("firstChar", query.getFirstChar());
        if(!StringUtils.isEmpty(query.getName()))
            criteria.andLike("name", "%" + query.getName() + "%");

        // 返回搜索结果
        return brandMapper.selectByExample(example);
    }

    /**
     * 分页搜索
     *
     * @param page 页码
     * @param size 每页数量
     * @param query 品牌查询实体
     */
    @Override
    public PageResult searchPage(Integer page, Integer size, TbBrand query) {
        // 设置分页
        PageHelper.startPage(page, size);

        // 分页查询
        List<TbBrand> list = search(query);

        // 获取分页相关信息
        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
