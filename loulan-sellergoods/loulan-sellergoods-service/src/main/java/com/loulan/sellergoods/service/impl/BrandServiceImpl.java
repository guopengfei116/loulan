package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.loulan.mapper.BrandMapper;
import com.loulan.pojo.TbBrand;
import com.loulan.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// 使用阿里巴巴的注解注册服务
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return brandMapper.findAll();
    }
}
