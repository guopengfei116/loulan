package com.loulan.service.impl;

import com.loulan.content.ContentService;
import com.loulan.mapper.ContentMapper;
import com.loulan.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class ContentServiceImpl extends BaseServiceImpl<TbContent> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    /**
     * 获取广告列表
     *
     * @param categoryId 分类Id
     */
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {
        Example example = new Example(TbContent.class);
        Example.Criteria criteria = example.createCriteria();

        // 指定分类，并启动状态
        criteria.andEqualTo("categoryId", categoryId);
        criteria.andEqualTo("status", "1");

        // 降序排列
        example.orderBy("sortOrder").desc();

        return contentMapper.selectByExample(example);
    }

}
