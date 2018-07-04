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

    @Reference
    private BrandService brandService;

    /**
     * 主键查询
     * 这里接受的ID参数必须为Long类型，因为实体类中为Long类型，Serializable会按照类型序列化
     *
     * @param  id  主键
     * @return     实体对象
     * */
    @GetMapping("/findOne")
    public TbBrand findOne(@RequestParam("id") Long id) {
        return brandService.findOne(id);
    }

    /**
     * 分页sql条件查询
     *
     * @param  page   页码
     * @param  size   页大小
     * @param  t      实体对象，封装了查询条件
     * @return        分页实体对象
     * */
    @PostMapping("/findPageByWhere")
    public PageResult findPageByWhere(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestBody(required = false) TbBrand t) {
        return brandService.findPageByWhere(page, size, t);
    }

    /**
     * 添加
     *
     * @param  t  实体对象
     * @return    执行结果对象
     * */
    @PutMapping("/add")
    public HttpResult add(@RequestBody TbBrand t) {
        HttpResult httpResult;

        try {
            brandService.add(t);
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
    public HttpResult update(@RequestBody TbBrand t) {
        HttpResult httpResult;

        try {
            brandService.update(t);
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
            brandService.deleteMore(ids);
            httpResult = HttpResult.ok("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("删除失败");
        }

        return httpResult;
    }

    /**
     * 实体下拉列表
     *
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    @GetMapping("/selectOptionList")
    public List<Map<String, String>> selectOptionList() {
        return brandService.selectOptionList();
    }

}
