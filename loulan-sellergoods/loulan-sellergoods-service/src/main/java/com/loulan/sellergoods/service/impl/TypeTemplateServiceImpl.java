package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.pojo.TbTypeTemplate;
import com.loulan.sellergoods.service.TypeTemplateService;
import com.loulan.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

@Service(interfaceClass = TypeTemplateService.class)
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService {

    /**
     * 分页sql条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了查询条件
     * @return       分页实体对象
     */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, TbTypeTemplate t) {
        /*
         * 1. 创建条件对象
         * 2. 添加 name like 条件
         * 3. 调用父类方法分页查询
         * */
        Example example = new Example(TbTypeTemplate.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(t.getName())) {
            criteria.andLike("name", "%" + t.getName() + "%");
        }

        return super.findPageByWhere(page, size, example);
    }

}
