package com.loulan.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbGoods;
import com.loulan.sellergoods.service.GoodsService;
import com.loulan.vo.Goods;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * */
    @GetMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 分页sql条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     */
    @PostMapping("/findPageByWhere")
    public PageResult findPageByWhere(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestBody(required = false) TbGoods t) {
        return goodsService.findPageByWhere(page, size, t);
    }

    /**
     * 添加
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * @return    执行结果对象
     */
    @PutMapping("/add")
    public HttpResult add(@RequestBody Goods t) {
        HttpResult httpResult;

        try {
            goodsService.add(t);
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
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * @return    执行结果对象
     */
    @PostMapping("/update")
    public HttpResult update(Goods t) {
        HttpResult httpResult;

        try {
            goodsService.update(t);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    /**
     * 批量修改状态，审核状态，1：未审核 2：通过审核 3. 已驳回
     *
     * @param  status  审核状态
     * @param  ids     主键集合
     * @return         执行结果对象
     */
    @PostMapping("/updateMoreStatus")
    public HttpResult updateMoreStatus(@RequestParam String status, @RequestParam Long[] ids) {
        HttpResult httpResult;

        try {
            goodsService.updateStatusMore(status, ids);
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
    public HttpResult deleteMore(@RequestAttribute Long[] ids) {
        HttpResult httpResult;

        try {
            goodsService.deleteMore(ids);
            httpResult = HttpResult.ok("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("删除失败");
        }

        return httpResult;
    }

}
