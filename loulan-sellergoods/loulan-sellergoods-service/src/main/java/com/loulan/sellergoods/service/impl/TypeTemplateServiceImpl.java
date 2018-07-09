package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.TypeTemplateMapper;
import com.loulan.pojo.TbTypeTemplate;
import com.loulan.sellergoods.service.TypeTemplateService;
import com.loulan.common.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = TypeTemplateService.class)
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

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

        if(t != null) {
            if(!StringUtils.isEmpty(t.getName())) {
                criteria.andLike("name", "%" + t.getName() + "%");
            }
        }

        return super.findPageByWhere(page, size, example);
    }

    /**
     * 实体下拉列表
     *
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    @Override
    public List<Map<String, String>> selectOptionList() {
        return typeTemplateMapper.selectOptionList();
    }

}
