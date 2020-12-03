package com.eye.db.service;

import com.eye.db.dao.EyeArticleMapper;
import com.eye.db.dao.SEyeArticleMapper;
import com.eye.db.domain.EyeArticle;
import com.eye.db.domain.EyeArticleCategory;
import com.eye.db.domain.EyeArticleExample;
import com.eye.db.domain.SEyeArticle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EyeArticleService {
    @Resource
    private EyeArticleMapper articleMapper;
    @Autowired
    private SEyeArticleMapper sEyeArticleMapper;
    @Autowired
    private EyeArticleCategoryService categoryService;

    public List<SEyeArticle> querySelective(Integer id, String title, Integer categoryId, Integer page, Integer limit, String sort, String order) {
        EyeArticleExample example = new EyeArticleExample();
        EyeArticleExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }

        if (!StringUtils.isEmpty(categoryId)) {
            criteria.andCategoryIdEqualTo(categoryId);
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause("sort_order ASC,"+sort + " " + order);
        }

        PageHelper.startPage(page, limit);

        List<EyeArticle> articles = articleMapper.selectByExampleWithBLOBs(example);
        List<SEyeArticle> articleList = new ArrayList<>();
        for (EyeArticle article : articles) {
            SEyeArticle sarticle = new SEyeArticle();

            EyeArticleCategory categoryL2 = categoryService.findById(article.getCategoryId());
            if (("L2").equals(categoryL2.getLevel())) {
                EyeArticleCategory categoryL1 = categoryService.findById(categoryL2.getPid());
                sarticle.setCategoryName(categoryL1.getName()+"/"+categoryL2.getName());
            }else {
                sarticle.setCategoryName(categoryL2.getName());
            }
            sarticle.setId(article.getId());
            sarticle.setAddTime(article.getAddTime());
            sarticle.setAddUser(article.getAddUser());
            sarticle.setCategoryId(article.getCategoryId());
            sarticle.setContext(article.getContext());
            sarticle.setDeleted(article.getDeleted());
            sarticle.setIsSearch(article.getIsSearch());
            sarticle.setKeywords(article.getKeywords());
            sarticle.setLabel(article.getLabel());
            sarticle.setLink(article.getLink());
            sarticle.setPicUrl(article.getPicUrl());
            sarticle.setSortOrder(article.getSortOrder());
            sarticle.setTitle(article.getTitle());
            sarticle.setUpdateTime(article.getUpdateTime());

            articleList.add(sarticle);

        }
        return articleList;
    }

    public List<EyeArticle> querySelective(Integer categoryId, Integer page, Integer limit, String sort, String order) {
        EyeArticleExample example = new EyeArticleExample();
        EyeArticleExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(categoryId)) {
            criteria.andCategoryIdEqualTo(categoryId);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause("sort_order ASC,"+sort + " " + order);
        }

        PageHelper.startPage(page, limit);

        return articleMapper.selectByExampleWithBLOBs(example);
    }

    public List<EyeArticle> querySelective(Integer page, Integer limit){
        EyeArticleExample example = new EyeArticleExample();
        EyeArticleExample.Criteria criteria = example.createCriteria();
        criteria.andKeywordsLike("%首页%").andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC");
        PageHelper.startPage(page, limit);
        return articleMapper.selectByExampleWithBLOBs(example);
    }

    public void add(EyeArticle article) {
        article.setAddTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.insertSelective(article);
    }

    public int update(EyeArticle article) {
        article.setUpdateTime(LocalDateTime.now());
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    public void delete(Integer id) {
        articleMapper.logicalDeleteByPrimaryKey(id);
    }

    public EyeArticle findById(Integer id) {
        EyeArticleExample example = new EyeArticleExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return articleMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<EyeArticle> findByCategoryId(Integer id) {
        EyeArticleExample example = new EyeArticleExample();
        example.or().andCategoryIdEqualTo(id).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return articleMapper.selectByExampleWithBLOBs(example);
    }

    public List<EyeArticle> findByCid(Integer id) {
        EyeArticleExample example = new EyeArticleExample();
        example.or().andCategoryIdEqualTo(id).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order ASC,add_time DESC");
        return articleMapper.selectByExample(example);
    }

    public Object search(String keywords, Integer page, Integer limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        List<SEyeArticle> list = sEyeArticleMapper.search("%" + keywords + "%", "%" + keywords + "%", sort + " " + order);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo.getList();
    }

    public int count() {
        EyeArticleExample example = new EyeArticleExample();
        example.or().andDeletedEqualTo(false);
        return (int) articleMapper.countByExample(example);
    }
}
