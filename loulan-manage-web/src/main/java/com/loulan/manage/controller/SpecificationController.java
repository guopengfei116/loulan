package com.loulan.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbBrand;
import com.loulan.pojo.TbSpecification;
import com.loulan.sellergoods.service.SpecificationService;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import com.loulan.vo.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/specification")
@RestController
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    // 全部
    @GetMapping("/findAll")
    public List<TbSpecification> findAll() {
        return specificationService.findAll();
    }

    // 条件分页
    @PostMapping("/search")
    public PageResult search(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size,
                             @RequestBody(required = false)  TbSpecification specification) {
        return specificationService.searchPage(page, size, specification);
    }

    // 通过主键查询
    @GetMapping("/findOne")
    public Specification findOne(Long id) {
        return specificationService.findOne(id);
    }

    // 更新
    @PostMapping("/update")
    public HttpResult update(@RequestBody Specification specification) {
        HttpResult httpResult;

        try {
            specificationService.update(specification);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    // 添加
    @PostMapping("/add")
    public HttpResult add(@RequestBody Specification specification) {
        HttpResult httpResult;

        try {
            specificationService.add(specification);
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
            specificationService.deleteByIds(ids);
            httpResult = HttpResult.ok("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("删除失败");
        }

        return httpResult;
    }

    // 下拉菜单
    @GetMapping("/selectOptionList")
    public List<Map<String, String>> selectOptionList() {
        return specificationService.selectOptionList();
    }

}
