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

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     实体对象
     * */
    @GetMapping("/findOne")
    public Specification findOne(Long id) {
        return specificationService.findOne(id);
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
                                      @RequestBody(required = false)  TbSpecification t) {
        return specificationService.findPageByWhere(page, size, t);
    }

    /**
     * 添加
     *
     * @param  t  实体对象
     * @return    执行结果对象
     * */
    @PutMapping("/add")
    public HttpResult add(@RequestBody Specification t) {
        HttpResult httpResult;

        try {
            specificationService.add(t);
            httpResult = HttpResult.ok("添加成功");
        } catch (Exception e) {
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
    public HttpResult update(@RequestBody Specification t) {
        HttpResult httpResult;

        try {
            specificationService.update(t);
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
            specificationService.deleteMore(ids);
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
