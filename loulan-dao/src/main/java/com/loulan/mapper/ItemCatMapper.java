package com.loulan.mapper;

import com.loulan.pojo.TbItemCat;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ItemCatMapper extends Mapper<TbItemCat> {

    /**
     * 通过父类获取子类下拉列表
     *
     * @param  parentId  父类id
     * @return           实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    List<Map<String, String>> selectOptionListByParentId(Long parentId);

}
