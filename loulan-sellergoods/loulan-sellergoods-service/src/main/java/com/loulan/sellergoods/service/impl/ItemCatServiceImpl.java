package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.ItemCatMapper;
import com.loulan.pojo.TbItemCat;
import com.loulan.sellergoods.service.ItemCatService;
import com.loulan.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = ItemCatService.class)
public class ItemCatServiceImpl extends BaseServiceImpl<TbItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 通过父类获取子类下拉列表
     *
     * @param  parentId  父类id
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    @Override
    public List<Map<String, String>> selectOptionListByParentId(Long parentId) {
        return itemCatMapper.selectOptionListByParentId(parentId);
    }

}
