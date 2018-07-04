package com.loulan.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbBrand;
import com.loulan.sellergoods.service.BrandService;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    // 使用duboo服务
    @Reference
    private BrandService brandService;

    // 全部
    @GetMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    // 分页
    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        return brandService.findPage(page, size);
    }

    // 条件分页
    @PostMapping("/search")
    public PageResult findPage(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer size,
                               @RequestBody(required = false) TbBrand brand) {
        return brandService.findPageByWhere(page, size, brand);
    }

    // 查询一个
    // 这里接受的ID参数必须为Long类型，因为实体类中为Long类型，Serializable会按照类型序列化
    @GetMapping("/findOne")
    public TbBrand findOne(@RequestParam("id") Long id) {
        return brandService.findOne(id);
    }

    // 更新
    @PostMapping("/update")
    public HttpResult update(@RequestBody TbBrand brand) {
        HttpResult httpResult;

        try {
            brandService.update(brand);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    // 添加
    @PostMapping("/add")
    public HttpResult add(@RequestBody TbBrand brand) {
        HttpResult httpResult;

        try {
            brandService.add(brand);
            httpResult = HttpResult.ok("添加成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("添加失败");
        }

        return httpResult;
    }

    // 批量删除
    @GetMapping("deleteByIds")
    public HttpResult deleteByIds(@RequestParam("ids") Long[] ids) {
        HttpResult httpResult;

        try {
            brandService.deleteMore(ids);
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
        return brandService.selectOptionList();
    }
}
