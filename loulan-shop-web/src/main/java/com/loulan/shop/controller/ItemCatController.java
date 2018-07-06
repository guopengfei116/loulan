package com.loulan.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.sellergoods.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 通过父类获取子类下拉列表
     *
     * @param  parentId  父类id
     * @return           实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    @GetMapping("/selectOptionListByParentId")
    public List<Map<String, String>> selectOptionListByParentId(Long parentId) {
        return itemCatService.selectOptionListByParentId(parentId);
    }

}
