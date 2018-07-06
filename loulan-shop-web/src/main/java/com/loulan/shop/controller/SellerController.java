package com.loulan.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbSeller;
import com.loulan.sellergoods.service.SellerService;
import com.loulan.vo.HttpResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * 查询，商家只能查询自己的信息
     *
     * @return  实体对象
     * */
    @GetMapping
    public TbSeller getInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return sellerService.findOne(username);
    }

    /**
     * 商家注册，新商家默认为未审核状态，0：未审核 1：已审核 2：审核未通过 3：关闭
     *
     * @param t 实体对象
     */
    @PutMapping
    public HttpResult register(@RequestBody TbSeller t) {
        HttpResult httpResult;

        try {
            // 未审核
            t.setStatus("0");

            // 密码加密
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            t.setPassword(passwordEncoder.encode(t.getPassword()));

            sellerService.add(t);
            httpResult = HttpResult.ok("添加成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("添加失败");
        }

        return httpResult;
    }

    /**
     * 完善信息，商家没有权限修改状态，需要去掉，商家入驻时间也不能修改
     *
     * @param t 实体对象
     */
    @PostMapping
    public HttpResult update(@RequestBody TbSeller t) {
        HttpResult httpResult;

        // 通过更新方法修改状态
        try {
            t.setStatus(null);
            t.setCreateTime(null);
            sellerService.update(t);
            httpResult = HttpResult.ok("修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            httpResult = HttpResult.fail("修改失败");
        }

        return httpResult;
    }

}
