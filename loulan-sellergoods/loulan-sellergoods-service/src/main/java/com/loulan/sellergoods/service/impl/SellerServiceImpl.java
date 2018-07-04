package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.SellerMapper;
import com.loulan.pojo.TbSeller;
import com.loulan.sellergoods.service.SellerService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service(interfaceClass = SellerService.class)
public class SellerServiceImpl extends BaseServiceImpl<TbSeller> implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 分页sql条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, TbSeller t) {
        /*
         * 1. 创建条件对象
         * 2. 添加 name like 条件
         * 3. 添加 nickName like 条件
         * 4. 添加 status equalTo 条件
         * 5. 调用父类方法分页查询
         * */
        Example example = new Example(TbSeller.class);
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(t.getName())) {
            criteria.andLike("name", "%" + t.getName() + "%");
        }
        if (!StringUtils.isEmpty(t.getNickName())) {
            criteria.andLike("nickName", "%" + t.getNickName() + "%");
        }
        if (!StringUtils.isEmpty(t.getStatus())) {
            criteria.andEqualTo("status", t.getStatus());
        }

        return super.findPageByWhere(page, size, example);
    }

    /**
     * 添加商家
     * 新入驻商家初始为未审核，强制设status为0
     * 状态，0：未审核 1：已审核 2：审核未通过 3：关闭
     *
     * @param t 实体对象
     * */
    @Override
    public void add(TbSeller t) {
        t.setStatus("0");
        super.add(t);
    }

    /**
     * 更新状态
     *
     * @param id     主键
     * @param status 状态，0：未审核 1：已审核 2：审核未通过 3：关闭
     */
    @Override
    public void updateStatus(String id, String status) throws Exception {
        if(!"0".equals(status) && !"1".equals(status) && !"2".equals(status) && !"3".equals(status)) {
            throw new Exception("status数值不符合要求");
        }

        TbSeller seller = new TbSeller();
        seller.setSellerId(id);
        seller.setStatus(status);

        sellerMapper.updateByPrimaryKeySelective(seller);
    }

}
