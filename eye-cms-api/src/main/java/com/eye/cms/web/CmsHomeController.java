package com.eye.cms.web;

import com.eye.cms.vo.ArticlesVo;
import com.eye.core.system.SystemConfig;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeAd;
import com.eye.db.domain.EyeArticle;
import com.eye.db.domain.EyeArticleCategory;
import com.eye.db.service.EyeAdService;
import com.eye.db.service.EyeArticleCategoryService;
import com.eye.db.service.EyeArticleService;
import com.eye.db.service.EyeSystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cms/home")
@Validated
@Api(description = "首页服务")
public class CmsHomeController {
    private final Log logger = LogFactory.getLog(CmsHomeController.class);

    @Autowired
    private EyeArticleService articleService;
    @Autowired
    private EyeArticleCategoryService categoryService;
    @Autowired
    private EyeAdService adService;
    @Autowired
    private EyeSystemConfigService configService;

    @GetMapping("/index")
    @ApiOperation("首页")
    public Object index() {
        List<EyeArticle> articles = articleService.querySelective(0, 4);

        //首页文章
        List<ArticlesVo> articlesVoList = new ArrayList<>();
        for (EyeArticle article : articles) {
            ArticlesVo articleVo = new ArticlesVo();
            articleVo.setId(article.getId());
            articleVo.setTitle(article.getTitle());
            articleVo.setKeywords(article.getKeywords());
            articleVo.setPicUrl(article.getPicUrl());
            articleVo.setLabel(article.getLabel());
            articleVo.setCategoryId(article.getCategoryId());
            articleVo.setLink(article.getLink());
            //查询二级类目
            EyeArticleCategory category = categoryService.findById(article.getCategoryId());
            articleVo.setCategoryName(category.getName());
            //查询一级类目
            EyeArticleCategory l1category = categoryService.findById(category.getPid());
            if (l1category == null){
                articleVo.setCategoryL1Id(category.getId());
                articleVo.setCategoryL1Name(category.getName());
            }else {
                articleVo.setCategoryL1Id(l1category.getId());
                articleVo.setCategoryL1Name(l1category.getName());
            }

            articlesVoList.add(articleVo);
        }

        //首页轮播图
        List<EyeAd> litemallAds = adService.queryIndex();

        Map<String, Object> date = new HashMap<>();
        date.put("litemallAds",litemallAds);
        date.put("articles",articlesVoList);

        return ResponseUtil.ok(date);
    }

    @GetMapping("/about")
    @ApiOperation("网站信息")
    public Object about(){
        Map<String, Object> date = new HashMap<>();
        date.put("bigLogo", SystemConfig.getMallBigLogo());
        date.put("smallLogo",SystemConfig.getMallSmallLogo());
        date.put("copyright",SystemConfig.getMallCopyright());
        date.put("name",SystemConfig.getMallName());
        return ResponseUtil.ok(date);
    }
}
