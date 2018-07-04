package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loulan.mapper.BrandMapper;
import com.loulan.pojo.TbBrand;
import com.loulan.sellergoods.service.BrandService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

// 使用阿里巴巴的注解注册服务
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页sql条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     */
    public PageResult findPageByWhere(Integer page, Integer size, TbBrand t) {
        /*
        * 1. 创建条件对象
        * 2. 添加 name like 条件
        * 3. 添加 firstChar equalTo 条件
        * 4. 调用父类方法分页查询
        * */
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(t.getFirstChar()))
            criteria.andEqualTo("firstChar", t.getFirstChar());
        if(!StringUtils.isEmpty(t.getName()))
            criteria.andLike("name", "%" + t.getName() + "%");

        return super.findPageByWhere(page, size, example);
    }

    /**
     * 实体下拉列表
     *
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    @Override
    public List<Map<String, String>> selectOptionList() {
        return brandMapper.selectOptionList();
    }

}
