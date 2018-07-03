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
     * 分页搜索
     *
     * @param page 页码
     * @param size 每页数量
     * @param query 品牌查询实体
     */
    @Override
    public PageResult searchPage(Integer page, Integer size, TbSeller query) {
        if(query != null) {
            Example example = new Example(TbSeller.class);

            // 搜索条件，商家名称，店铺名称，状态
            Example.Criteria criteria = example.createCriteria();
            if (!StringUtils.isEmpty(query.getName())) {
                criteria.andLike("name", "%" + query.getName() + "%");
            }
            if (!StringUtils.isEmpty(query.getNickName())) {
                criteria.andLike("nickName", "%" + query.getNickName() + "%");
            }
            if (!StringUtils.isEmpty(query.getStatus())) {
                criteria.andEqualTo("status", query.getStatus());
            }

            return super.searchPage(page, size, example);
        }else {
            return super.findPage(page, size);
        }
    }

    /**
     * 更新状态
     *
     * @param id 主键
     * @param status   状态，0：未审核 1：已审核 2：审核未通过 3：关闭
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

    /**
     * 添加商家，新入驻商家初始状态强制为0
     *
     * @param seller 实体对象
     * */
    @Override
    public void add(TbSeller seller) {
        seller.setStatus("0");
        super.add(seller);
    }

    /**
     * 更新商家信息，更新时不能让商家任意修改状态
     *
     * @param seller 实体对象
     * */
    @Override
    public void update(TbSeller seller) {
        seller.setStatus(null);
        super.update(seller);
    }
}
