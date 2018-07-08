package com.loulan.sellergoods.service;

import com.loulan.pojo.TbItemCat;
import com.loulan.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ItemCatService extends BaseService<TbItemCat> {

    /**
     * 通过父类获取子类下拉列表
     *
     * @param  parentId  父类id
     * @return           实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    List<Map<String, String>> selectOptionListByParentId(Long parentId);

}
