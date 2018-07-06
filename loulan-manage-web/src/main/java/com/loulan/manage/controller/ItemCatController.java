package com.loulan.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbItemCat;
import com.loulan.sellergoods.service.ItemCatService;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     实体对象
     * */
    @GetMapping("/findOne")
    public TbItemCat findOne(Long id) {
        return itemCatService.findOne(id);
    }

    /**
     * 分页条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了name与parentId
     * @return       分页实体对象
     * */
    @GetMapping("/findPageByWhere")
    public PageResult findPageByWhere(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestBody(required = false) TbItemCat t) {
        return itemCatService.findPageByWhere(page, size, t);
    }

    /**
     * 添加
     *
     * @param  t  实体对象
     * @return    执行结果对象
     * */
    @PutMapping("/add")
    public HttpResult add(@RequestBody TbItemCat t) {
        HttpResult httpResult;

        try {
            itemCatService.add(t);
            httpResult = HttpResult.ok("添加成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("添加失败");
        }

        return httpResult;
    }

    /**
     * 修改
     *
     * @param  t  实体对象
     * @return    执行结果对象
     * */
    @PostMapping("/update")
    public HttpResult update(@RequestBody TbItemCat t) {
        HttpResult httpResult;

        try {
            itemCatService.update(t);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    /**
     * 批量删除
     *
     * @param  ids  主键集合
     * @return      执行结果对象
     * */
    @DeleteMapping("deleteMore")
    public HttpResult deleteMore(@RequestParam("ids") Long[] ids) {
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
