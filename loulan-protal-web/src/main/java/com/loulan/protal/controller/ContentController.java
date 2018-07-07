package com.loulan.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.content.ContentService;
import com.loulan.pojo.TbContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    /**
     * 获取广告列表
     *
     * @param categoryId 分类Id
     */
    @RequestMapping("/findByCategoryId")
    public List<TbContent> findByCategoryId(Long categoryId) {
        return contentService.findByCategoryId(categoryId);
    }

}

