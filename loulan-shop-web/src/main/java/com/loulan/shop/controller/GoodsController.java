package com.loulan.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbGoods;
import com.loulan.sellergoods.service.GoodsService;
import com.loulan.vo.Goods;
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
    @GetMapping("/findOne")
    public Goods findOne(Long id) {
        // 只能查询商家自己的商品
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return goodsService.findOne(id, sellerId);
    }

    /**
     * 分页sql条件查询，只查询spu
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
        // 只能查询商家自己的商品
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        t.setSellerId(sellerId);

        return goodsService.findPageByWhere(page, size, t);
    }

}
