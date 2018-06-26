package com.loulan.sellergoods.service;

import com.loulan.pojo.TbBrand;

import java.util.List;

public interface BrandService {

    List<TbBrand> findAll(Integer page, Integer rows);
}
