package com.loulan.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.loulan.pojo.TbSeller;
import com.loulan.sellergoods.service.SellerService;
import com.loulan.vo.HttpResult;
import com.loulan.vo.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * 主键查询
     *
     * @param  id  主键
     * @return     实体对象
     * */
    @GetMapping("/findOne")
    public TbSeller findOne(String id) {
        return sellerService.findOne(id);
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
                                      @RequestBody(required = false) TbSeller t) {
        return sellerService.findPageByWhere(page, size, t);
    }

    /**
     * 商家注册，新商家默认为未审核状态，0：未审核 1：已审核 2：审核未通过 3：关闭
     *
     * @param t 实体对象
     */
    @PutMapping("/add")
    public HttpResult add(@RequestBody TbSeller t) {
        HttpResult httpResult;

        try {
            t.setStatus("0");
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
    @PostMapping("/update")
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
