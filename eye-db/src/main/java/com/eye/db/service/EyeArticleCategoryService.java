package com.eye.db.service;

import com.eye.db.dao.EyeArticleCategoryMapper;
import com.eye.db.domain.EyeArticleCategory;
import com.eye.db.domain.EyeArticleCategoryExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeArticleCategoryService {
    @Resource
    private EyeArticleCategoryMapper categoryMapper;

    public void add(EyeArticleCategory category) {
        category.setAddTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insertSelective(category);
    }

    public List<EyeArticleCategory> queryByPid(int i) {

        EyeArticleCategoryExample example = new EyeArticleCategoryExample();
        example.or().andPidEqualTo(i).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return categoryMapper.selectByExample(example);
    }

    public List<EyeArticleCategory> queryL1() {
        EyeArticleCategoryExample example = new EyeArticleCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return categoryMapper.selectByExample(example);

    }

    public EyeArticleCategory findById(Integer id) {
        EyeArticleCategoryExample example = new EyeArticleCategoryExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return categoryMapper.selectOneByExample(example);
    }

    public int updateById(EyeArticleCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public List<EyeArticleCategory> query() {
        EyeArticleCategoryExample example = new EyeArticleCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC");
        PageHelper.startPage(0, 1);
        return categoryMapper.selectByExample(example);
    }
}
