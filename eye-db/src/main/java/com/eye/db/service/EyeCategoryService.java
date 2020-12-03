package com.eye.db.service;

import com.eye.db.dao.EyeCategoryMapper;
import com.eye.db.domain.EyeCategory;
import com.eye.db.domain.EyeCategoryExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeCategoryService {
    @Resource
    private EyeCategoryMapper categoryMapper;
    private EyeCategory.Column[] CHANNEL = {EyeCategory.Column.id, EyeCategory.Column.name, EyeCategory.Column.iconUrl};

    public List<EyeCategory> queryL1WithoutRecommend(int offset, int limit) {
        EyeCategoryExample example = new EyeCategoryExample();
        example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<EyeCategory> queryL1(int offset, int limit) {
        EyeCategoryExample example = new EyeCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<EyeCategory> queryL1() {
        EyeCategoryExample example = new EyeCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return categoryMapper.selectByExample(example);
    }

    public List<EyeCategory> queryByPid(Integer pid) {
        EyeCategoryExample example = new EyeCategoryExample();
        example.or().andPidEqualTo(pid).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return categoryMapper.selectByExample(example);
    }

    public List<EyeCategory> queryL2ByIds(List<Integer> ids) {
        EyeCategoryExample example = new EyeCategoryExample();
        example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public EyeCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<EyeCategory> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        EyeCategoryExample example = new EyeCategoryExample();
        EyeCategoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return categoryMapper.selectByExample(example);
    }

    public int updateById(EyeCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeCategory category) {
        category.setAddTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insertSelective(category);
    }

    public List<EyeCategory> queryChannel() {
        EyeCategoryExample example = new EyeCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }
}
