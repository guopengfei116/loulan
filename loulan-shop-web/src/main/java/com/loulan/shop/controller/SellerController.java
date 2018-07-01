package com.loulan.shop.controller;

import com.loulan.pojo.TbSeller;
import com.loulan.sellergoods.service.SellerService;
import com.loulan.vo.HttpResult;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerController {

    @Reference
    private SellerService sellerService;

    // 更新
    @PostMapping("/update")
    public HttpResult update(@RequestBody TbSeller seller) {
        HttpResult httpResult;

        try {
            sellerService.update(seller);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

    // 添加
    @PostMapping("/add")
    public HttpResult add(@RequestBody TbSeller seller) {
        HttpResult httpResult;

        try {
            sellerService.add(seller);
            httpResult = HttpResult.ok("添加成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("添加失败");
        }

        return httpResult;
    }
}
