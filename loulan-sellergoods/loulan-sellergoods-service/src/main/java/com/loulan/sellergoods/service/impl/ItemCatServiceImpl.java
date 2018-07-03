package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.pojo.TbItemCat;
import com.loulan.sellergoods.service.ItemCatService;
import com.loulan.service.impl.BaseServiceImpl;

@Service(interfaceClass = ItemCatService.class)
public class ItemCatServiceImpl extends BaseServiceImpl<TbItemCat> implements ItemCatService {

}
