package com.loulan.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.common.service.impl.BaseServiceImpl;
import com.loulan.content.service.ContentService;
import com.loulan.mapper.ContentMapper;
import com.loulan.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

@Service(interfaceClass = ContentService.class)
public class ContentServiceImpl extends BaseServiceImpl<TbContent> implements ContentService {

    private static final String REDIS_CONTENT = "content";    // redis中内容对应的key

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ContentMapper contentMapper;

    // 删除redis缓存
    private void deleteRedis(Long categoryId) {
        try {
            redisTemplate.boundHashOps(REDIS_CONTENT).delete(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取分类下广告列表
     *
     * @param categoryId 分类Id
     */
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {

        // 先redis中查找
        List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps(REDIS_CONTENT).get(categoryId);

        // 没有则去mysql查找，然后缓存到redis
        if(list == null) {
            Example example = new Example(TbContent.class);
            Example.Criteria criteria = example.createCriteria();

            // 指定分类，并启动状态
            criteria.andEqualTo("categoryId", categoryId);
            criteria.andEqualTo("status", "1");

            // 降序排列
            example.orderBy("sortOrder").desc();

            list = contentMapper.selectByExample(example);
            redisTemplate.boundHashOps(REDIS_CONTENT).put(categoryId, list);
        }

        return list;
    }

    /**
     * 添加新广告，删除新广告对应分类下的旧有缓存
     *
     * @param  t  内容实体对象
     * */
    @Override
    public void add(TbContent t) {
        super.add(t);
        deleteRedis(t.getCategoryId());
    }

    /**
     * 更新广告，删除旧缓存
     *
     * @param  t  内容实体对象
     * */
    @Override
    public void update(TbContent t) {
        /*
        * 1. 通过id获取旧有数据
        * 2. 然后调用父类方法更新
        * 3. 对比广告所属分类是否有变化，有则清除旧分类和当前分类的广告缓存，否则只清除当前分类缓存
        * */
        TbContent oldContent = super.findOne(t.getId());

        super.update(t);

        // 分类ID不一致，那么删除旧分类广告缓存
        if(oldContent.getCategoryId().equals(t.getCategoryId())) {
            deleteRedis(oldContent.getCategoryId());
        }
        deleteRedis(t.getCategoryId());
    }

    /**
     * 批量删除广告，清除缓存
     *
     * @param  ids  主键集合
     * */
    @Override
    public void deleteMore(Serializable[] ids) {
        // 删除前获取旧数据
        List<TbContent> list = super.findMore(ids);

        super.deleteMore(ids);

        // 删除成功后根据分类ID清除缓存
        if(list != null && list.size() > 0) {
            for (TbContent content : list) {
                deleteRedis(content.getCategoryId());
            }
        }
    }

}
