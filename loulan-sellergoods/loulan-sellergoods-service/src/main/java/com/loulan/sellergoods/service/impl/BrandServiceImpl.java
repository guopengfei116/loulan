package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.BrandMapper;
import com.loulan.pojo.TbBrand;
import com.loulan.sellergoods.service.BrandService;
import com.loulan.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

// 使用阿里巴巴的注解注册服务
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

}
