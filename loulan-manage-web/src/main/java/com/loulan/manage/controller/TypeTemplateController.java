package com.loulan.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbTypeTemplate;
import com.loulan.sellergoods.service.TypeTemplateService;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    
    @Reference
    private TypeTemplateService typeTemplateService;

    // 条件分页
    @RequestMapping("/search")
    public PageResult search(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size,
                             @RequestBody(required = false) TbTypeTemplate typeTemplate) {
        return typeTemplateService.searchPage(page, size, typeTemplate);
    }

    // 通过主键查询
    @GetMapping("/findOne")
    public TbTypeTemplate findOne(Long id) {
        return typeTemplateService.findOne(id);
    }

    // 更新
    @PostMapping("/update")
    public HttpResult update(@RequestBody TbTypeTemplate typeTemplate) {
        HttpResult httpResult;

        try {
            typeTemplateService.update(typeTemplate);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    // 添加
    @PostMapping("/add")
    public HttpResult add(@RequestBody TbTypeTemplate typeTemplate) {
        HttpResult httpResult;

        try {
            typeTemplateService.add(typeTemplate);
            httpResult = HttpResult.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("添加失败");
        }

        return httpResult;
    }

    // 删除
    @GetMapping("/deleteByIds")
    public HttpResult deleteByIds(@RequestParam("ids") Long[] ids) {
        HttpResult httpResult;

        try {
            typeTemplateService.deleteByIds(ids);
            httpResult = HttpResult.ok("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("删除失败");
        }

        return httpResult;
    }
}
