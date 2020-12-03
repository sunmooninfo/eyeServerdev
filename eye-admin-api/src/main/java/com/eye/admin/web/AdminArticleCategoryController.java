package com.eye.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.vo.ArticleCategoryVo;
import com.eye.admin.vo.CatVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeArticleCategory;
import com.eye.db.service.EyeArticleCategoryService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "文章分类管理")
@RestController
@RequestMapping("/admin/categoryx")
@Validated
public class AdminArticleCategoryController {
    private final Log logger = LogFactory.getLog(AdminArticleCategoryController.class);

    @Autowired
    private EyeArticleCategoryService categoryService;

    @ApiOperation(value = "文章分类查询")
    @RequiresPermissions("admin:categoryx:list")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章分类"}, button = "查询")
    @GetMapping("/list")
    public Object list() {
        List<ArticleCategoryVo> categoryVoList = new ArrayList<>();

        List<EyeArticleCategory> categoryList = categoryService.queryByPid(0);
        for (EyeArticleCategory category : categoryList) {
            ArticleCategoryVo categoryVO = new ArticleCategoryVo();
            categoryVO.setId(category.getId());
            categoryVO.setDesc(category.getDesc());
            categoryVO.setIconUrl(category.getIconUrl());
            categoryVO.setPicUrl(category.getPicUrl());
            categoryVO.setKeywords(category.getKeywords());
            categoryVO.setName(category.getName());
            categoryVO.setLevel(category.getLevel());
            categoryVO.setPid(category.getPid());
            categoryVO.setSortOrder(category.getSortOrder());
            categoryVO.setInLink(category.getInLink());
            categoryVO.setOutLink(category.getOutLink());
            categoryVO.setLinkValue(category.getLinkValue());

            List<ArticleCategoryVo> children = new ArrayList<>();
            List<EyeArticleCategory> subCategoryList = categoryService.queryByPid(category.getId());
            for (EyeArticleCategory subCategory : subCategoryList) {
                ArticleCategoryVo subCategoryVo = new ArticleCategoryVo();
                subCategoryVo.setId(subCategory.getId());
                subCategoryVo.setDesc(subCategory.getDesc());
                subCategoryVo.setIconUrl(subCategory.getIconUrl());
                subCategoryVo.setPicUrl(subCategory.getPicUrl());
                subCategoryVo.setKeywords(subCategory.getKeywords());
                subCategoryVo.setName(subCategory.getName());
                subCategoryVo.setLevel(subCategory.getLevel());
                subCategoryVo.setPid(subCategory.getPid());
                subCategoryVo.setSortOrder(subCategory.getSortOrder());
                subCategoryVo.setInLink(subCategory.getInLink());
                subCategoryVo.setOutLink(subCategory.getOutLink());
                subCategoryVo.setLinkValue(subCategory.getLinkValue());

                children.add(subCategoryVo);
            }

            categoryVO.setChildren(children);
            categoryVoList.add(categoryVO);
        }

        return ResponseUtil.okList(categoryVoList);
    }

    @ApiOperation(value = "文章分类添加")
    @RequiresPermissions("admin:categoryx:create")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章分类"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeArticleCategory category) {

        categoryService.add(category);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "文章分类管理")
    @GetMapping("/cate")
    public Object cate() {

        List<EyeArticleCategory> l1CatList = categoryService.queryL1();
        List<CatVo> categoryList = new ArrayList<>();
        for (EyeArticleCategory l1 : l1CatList) {
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(l1.getId());
            l1CatVo.setLabel(l1.getName());

            List<EyeArticleCategory> l2CatList = categoryService.queryByPid(l1.getId());
            if (l2CatList.size()!=0){
                List<CatVo> children = new ArrayList<>(l2CatList.size());
                for (EyeArticleCategory l2 : l2CatList) {
                    CatVo l2CatVo = new CatVo();
                    l2CatVo.setValue(l2.getId());
                    l2CatVo.setLabel(l2.getName());
                    children.add(l2CatVo);
                }
                l1CatVo.setChildren(children);
            }
            categoryList.add(l1CatVo);
        }
        return ResponseUtil.okList(categoryList);
    }

    @ApiOperation(value = "文章分类详情")
    @RequiresPermissions("admin:categoryx:read")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章分类"}, button = "详情")
    @GetMapping("/read")
    @ApiImplicitParam(name="id",value = "分类id",required=true,paramType="path",dataType="int")
    public Object read(@NotNull Integer id) {
        EyeArticleCategory category = categoryService.findById(id);
        return ResponseUtil.ok(category);
    }

    @ApiOperation(value = "文章分类编辑")
    @RequiresPermissions("admin:categoryx:update")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章分类"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeArticleCategory category) {

        if (categoryService.updateById(category) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "文章分类删除")
    @RequiresPermissions("admin:categoryx:delete")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章分类"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeArticleCategory category) {

        categoryService.deleteById(category.getId());
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "文章分类管理管理获取一级类目")
    @RequiresPermissions("admin:categoryx:list")
    @GetMapping("/catL1")
    public Object catL1() {
        // 所有一级分类目录
        List<EyeArticleCategory> l1CatList = categoryService.queryL1();
        List<Map<String, Object>> data = new ArrayList<>(l1CatList.size());
        for (EyeArticleCategory category : l1CatList) {
            Map<String, Object> d = new HashMap<>(2);
            d.put("value", category.getId());
            d.put("label", category.getName());
            data.add(d);
        }
        return ResponseUtil.okList(data);
    }
}