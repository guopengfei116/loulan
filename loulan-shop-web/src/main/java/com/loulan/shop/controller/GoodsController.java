package com.loulan.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbGoods;
import com.loulan.sellergoods.service.GoodsService;
import com.loulan.vo.Goods;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping("/{id}")
    public Goods findOne(@PathVariable Long id) {
        // 只能查询商家自己的商品
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return goodsService.findOne(id, sellerId);
    }

    /**
     * 分页sql条件查询，只查询spu，且只能查询自家的商品
     *
     * @param  page         页码
     * @param  size         页大小
     * @param  auditStatus  商品状态
     * @param  goodsName    商品名称
     * @return              分页实体对象
     */
    @GetMapping("/findPageByWhere")
    public PageResult findPageByWhere(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestParam(required = false) String auditStatus,
                                      @RequestParam(required = false) String goodsName) {

        // 获取商家ID
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();

        TbGoods goods = new TbGoods();
        goods.setAuditStatus(auditStatus);
        goods.setSellerId(sellerId);
        goods.setGoodsName(goodsName);

        return goodsService.findPageByWhere(page, size, goods);
    }

    /**
     * 添加
     *
     * @param  t  复合实体对象，包含商品SPU，商品详细，商品SKU集合
     * @return    执行结果对象
     */
    @PutMapping
    public HttpResult add(@RequestBody Goods t) {
        HttpResult httpResult;

        try {
            // 设置商品所属商家
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            t.getGoods().setSellerId(sellerId);
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
    @PostMapping
    public HttpResult update(Goods t) {
        HttpResult httpResult;

        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsService.update(t, sellerId);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    /**
     * 批量修改状态
     *
     * @param  ids  主键集合
     * @return      执行结果对象
     */
    @PostMapping("/updateMoreStatus/{ids}")
    public HttpResult updateMoreStatus(@RequestBody TbGoods t, @PathVariable Long[] ids) {
        HttpResult httpResult;

        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsService.updateMore(t, ids, sellerId);
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
    @DeleteMapping("/{ids}")
    public HttpResult deleteMore(@PathVariable Long[] ids) {
        HttpResult httpResult;

        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsService.deleteMore(ids, sellerId);
            httpResult = HttpResult.ok("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("删除失败");
        }

        return httpResult;
    }

}
