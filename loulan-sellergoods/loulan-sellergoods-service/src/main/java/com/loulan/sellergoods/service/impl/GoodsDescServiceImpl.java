package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.pojo.TbGoodsDesc;
import com.loulan.sellergoods.service.GoodsDescService;
import com.loulan.common.service.impl.BaseServiceImpl;

@Service(interfaceClass = GoodsDescService.class)
public class GoodsDescServiceImpl extends BaseServiceImpl<TbGoodsDesc> implements GoodsDescService {

}
