package com.loulan.content.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.content.service.ContentService;
import com.loulan.pojo.TbContent;
import com.loulan.vo.HttpResult;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     实体对象
     * */
    @GetMapping("/{id}")
    public TbContent findOne(@PathVariable("id") Long id) {
        return contentService.findOne(id);
    }

    /**
     * 获取分类下广告列表
     *
     * @param categoryId 分类Id
     */
    @GetMapping("/findByCategoryId")
    public List<TbContent> findByCategoryId(Long categoryId) {
        return contentService.findByCategoryId(categoryId);
    }

    /**
     * 添加新广告，删除新广告对应分类下的旧有缓存
     *
     * @param  t  内容实体对象
     * */
    @PutMapping("/add")
    public HttpResult add(TbContent t) {
        try {
            contentService.add(t);
            return HttpResult.ok("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpResult.fail("添加失败");
    }

    /**
     * 更新广告，删除旧缓存
     *
     * @param  t  内容实体对象
     * */
    @PostMapping("/update")
    public HttpResult update(TbContent t) {
        try {
            contentService.update(t);
            return HttpResult.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpResult.fail("修改失败");
    }

    /**
     * 批量删除广告，清除缓存
     *
     * @param  ids  主键集合
     * */
    @DeleteMapping("/{ids}")
    public HttpResult deleteMore(@PathVariable("ids") Serializable[] ids) {
        try {
            contentService.deleteMore(ids);
            return HttpResult.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpResult.fail("删除失败");
    }

}
