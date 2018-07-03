package com.loulan.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbSeller;
import com.loulan.sellergoods.service.SellerService;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {
    
    @Reference
    private SellerService sellerService;

    // 条件分页
    @RequestMapping("/search")
    public PageResult search(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size,
                             @RequestBody(required = false) TbSeller seller) {
        return sellerService.searchPage(page, size, seller);
    }

    /**
     * 更新状态
     *
     * @param id 主键
     * @param status   状态，0：未审核 1：已审核 2：审核未通过 3：关闭
     */
    @RequestMapping("/updateStatus")
    public HttpResult updateStatus(String id, String status) {
        HttpResult httpResult;

        // 通过更新方法修改状态
        try {
            sellerService.updateStatus(id, status);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }
}
