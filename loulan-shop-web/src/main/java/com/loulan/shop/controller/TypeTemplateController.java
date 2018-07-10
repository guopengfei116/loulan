package com.loulan.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbTypeTemplate;
import com.loulan.sellergoods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    
    @Reference
    private TypeTemplateService typeTemplateService;

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     实体对象
     * */
    @GetMapping("/{id}")
    public TbTypeTemplate findOne(@PathVariable Long id) {
        return typeTemplateService.findOne(id);
    }

}
