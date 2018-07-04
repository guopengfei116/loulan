package com.loulan.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbItemCat;
import com.loulan.sellergoods.service.ItemCatService;
import com.loulan.vo.HttpResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 通过parentId查询相应的子分类列表
     * */
    @GetMapping("/findByParentId")
    public List<TbItemCat> findByParentId(Long parentId) {
        TbItemCat itemCat = new TbItemCat();
        itemCat.setParentId(parentId);
        return itemCatService.findByWhere(itemCat);
    }

    /**
     * 通过主键查询
     * */
    @GetMapping("/findOne")
    public TbItemCat findOne(Long id) {
        return itemCatService.findOne(id);
    }

    // 更新
    @PostMapping("/update")
    public HttpResult update(@RequestBody TbItemCat itemCat) {
        HttpResult httpResult;

        try {
            itemCatService.update(itemCat);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    // 添加
    @PostMapping("/add")
    public HttpResult add(@RequestBody TbItemCat itemCat) {
        HttpResult httpResult;

        try {
            itemCatService.add(itemCat);
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
            itemCatService.deleteMore(ids);
            httpResult = HttpResult.ok("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("删除失败");
        }

        return httpResult;
    }
}
