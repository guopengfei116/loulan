package com.loulan.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.loulan.mapper.ItemCatMapper;
import com.loulan.pojo.TbItemCat;
import com.loulan.sellergoods.service.ItemCatService;
import com.loulan.common.service.impl.BaseServiceImpl;
import com.loulan.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = ItemCatService.class)
public class ItemCatServiceImpl extends BaseServiceImpl<TbItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 分页条件查询
     *
     * @param  page  页码
     * @param  size  页大小
     * @param  t     实体对象，封装了name与parentId
     * @return       分页实体对象
     * */
    @Override
    public PageResult findPageByWhere(Integer page, Integer size, TbItemCat t) {
        /*
         * 1. 创建条件对象
         * 2. 添加 parentId notEqualTo 条件，指定所属父类
         * 3. 添加 name like 条件，分类名称
         * 4. 调用父类方法分页查询
         * */
        Example example = new Example(TbItemCat.class);
        Example.Criteria criteria = example.createCriteria();

        if(t != null) {
            if(t.getParentId() != null) {
                criteria.andEqualTo("parentId", t.getParentId());
            }
            if(!StringUtils.isEmpty(t.getName())) {
                criteria.andLike("name", "%" + t.getName() + "%");
            }
        }

        return super.findPageByWhere(page, size, example);
    }

    /**
     * 通过父类获取子类下拉列表
     *
     * @param  parentId  父类id
     * @return 实体id与name(as text)构成的集合：[ {id, text}, {id, text}, ... ]
     */
    @Override
    public List<Map<String, String>> selectOptionListByParentId(Long parentId) {
        return itemCatMapper.selectOptionListByParentId(parentId);
    }

}
