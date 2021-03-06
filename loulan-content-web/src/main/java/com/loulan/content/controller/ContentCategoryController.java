package com.loulan.content.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.content.service.ContentCategoryService;
import com.loulan.pojo.TbContentCategory;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {

    @Reference
    private ContentCategoryService contentCategoryService;

    @GetMapping("/{id}")
    public TbContentCategory findOne(@PathVariable("id") Long id) {
        return contentCategoryService.findOne(id);
    }

    /**
     * 分页sql条件查询，根据分类名称搜索
     *
     * @param  page          页码
     * @param  size          页大小
     * @param  categoryName  广告分类名称
     * @return               分页实体对象
     */
    @GetMapping("/findPageByWhere")
    public PageResult findPageByWhere(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestParam(defaultValue = "") String categoryName) {
        TbContentCategory category = new TbContentCategory();
        category.setName(categoryName);
        return contentCategoryService.findPageByWhere(page, size, category);
    }

    @PutMapping
    public HttpResult add(TbContentCategory t) {
        try {
            contentCategoryService.add(t);
            return HttpResult.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpResult.fail("添加失败");
    }

    @PostMapping
    public HttpResult update(TbContentCategory t) {
        try {
            contentCategoryService.update(t);
            return HttpResult.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpResult.fail("修改失败");
    }

    @DeleteMapping("/{ids}")
    public HttpResult deleteMore(@PathVariable("ids") Serializable[] ids) {
        try {
            contentCategoryService.deleteMore(ids);
            return HttpResult.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpResult.fail("删除失败");
    }

}
