package com.loulan.sellergoods.service;

import com.loulan.pojo.TbSpecification;
import com.loulan.service.BaseService;
import com.loulan.vo.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService extends BaseService<TbSpecification> {

    /**
     * 主键查询
     *
     * @param id 主键
     * @return   复合规格对象，包含规格与规格选项
     */
    Specification findOne(Long id);

    /**
     * 添加
     *
     * @param t 复合规格对象，包含规格与规格选项
     * */
    void add(Specification t);

    /**
     * 更新
     *
     * @param t 复合规格对象，包含规格与规格选项
     * */
    void update(Specification t);

    /**
     * 规格下拉列表
     *
     * @return 规格id与name构成的集合：[ {id, specName}, {id, specName}, ... ]
     */
    List<Map<String, String>> selectOptionList();

}
