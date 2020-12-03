package com.eye.cms.web;

import com.eye.cms.vo.CatVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeArticle;
import com.eye.db.domain.EyeArticleCategory;
import com.eye.db.service.EyeArticleCategoryService;
import com.eye.db.service.EyeArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cms/category")
@Validated
@Api(description = "分类服务")
public class CmsCategoryController {
    private final Log logger = LogFactory.getLog(CmsCategoryController.class);

    @Autowired
    private EyeArticleCategoryService categoryService;
    @Autowired
    private EyeArticleService articleService;

    @GetMapping("all")
    @ApiOperation("所有分类数据")
    public Object all() {

        List<CatVo> l1Categories = new ArrayList<>();
        // 所有一级分类目录
        List<EyeArticleCategory> l1CatList = categoryService.queryL1();

        for (EyeArticleCategory category : l1CatList) {
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(category.getId());
            l1CatVo.setLabel(category.getName());
            l1CatVo.setLevel(category.getLevel());
            l1CatVo.setInLink(category.getInLink());
            l1CatVo.setOutLink(category.getOutLink());
            l1CatVo.setLinkValue(category.getLinkValue());
            //所有一级分类下的文章
            List<EyeArticle> articles = articleService.findByCid(category.getId());
            l1CatVo.setArticleList(articles);

            // 所有二级分类目录
            List<EyeArticleCategory> l2CatList = categoryService.queryByPid(category.getId());
            List<CatVo> l2Cat = new ArrayList<>();
                for (EyeArticleCategory EyeCategory : l2CatList) {
                    CatVo l2CatVo = new CatVo();
                    l2CatVo.setValue(EyeCategory.getId());
                    l2CatVo.setLabel(EyeCategory.getName());
                    l2CatVo.setLevel(EyeCategory.getLevel());
                    l2CatVo.setInLink(EyeCategory.getInLink());
                    l2CatVo.setOutLink(EyeCategory.getOutLink());
                    l2CatVo.setLinkValue(EyeCategory.getLinkValue());
                    //所有二级分类目录下的文章
                    List<EyeArticle> l2articles = articleService.findByCid(EyeCategory.getId());
                    l2CatVo.setArticleList(l2articles);
                    l2Cat.add(l2CatVo);
                }

            l1CatVo.setChildren(l2Cat);
            l1Categories.add(l1CatVo);
        }

        return ResponseUtil.ok(l1Categories);
    }


    @GetMapping("/current")
    @ApiOperation("显示当前分类")
    @ApiImplicitParam(name = "id",value = "分类id",required=false,paramType="path",dataType="int")
    public Object current(@NotNull Integer id) {
        EyeArticleCategory category = categoryService.findById(id);
        if (category == null) {
            return ResponseUtil.badArgumentValue();
        }

        List<EyeArticleCategory> categoryList = categoryService.queryByPid(category.getId());
        Map<String,Object> data = new HashMap<>();
        data.put("category",category);
        data.put("categoryList",categoryList);
        return ResponseUtil.ok(data);
    }
}
