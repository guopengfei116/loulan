package com.loulan.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.common.service.impl.BaseServiceImpl;
import com.loulan.content.service.ContentCategoryService;
import com.loulan.mapper.ContentCategoryMapper;
import com.loulan.pojo.TbContent;
import com.loulan.pojo.TbContentCategory;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service(interfaceClass = ContentCategoryService.class)
public class ContentCategoryServiceImpl extends BaseServiceImpl<TbContentCategory> implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    /**
     * 分页sql条件查询
     *
     * @param  page      页码
     * @param  size      页大小
     * @param  category  实体对象
     * @return           分页实体对象
     */
    public PageResult findPageByWhere(Integer page, Integer size, TbContentCategory category) {
        Example example = new Example(TbContentCategory.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(category.getName())) {
            criteria.andLike("name", "%" + category.getName() + "%");
        }

        return super.findPageByWhere(page, size, example);
    }

}
