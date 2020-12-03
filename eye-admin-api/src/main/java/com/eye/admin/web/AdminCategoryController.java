package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.vo.CategoryVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeCategory;
import com.eye.db.service.EyeCategoryService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "商品类目")
@RestController
@RequestMapping("/admin/category")
@Validated
public class AdminCategoryController {
    private final Log logger = LogFactory.getLog(AdminCategoryController.class);

    @Autowired
    private EyeCategoryService categoryService;

    @ApiOperation(value = "类目管理查询")
    @RequiresPermissions("admin:category:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品类目"}, button = "查询")
    @GetMapping("/list")
    public Object list() {
        List<CategoryVo> categoryVoList = new ArrayList<>();

        List<EyeCategory> categoryList = categoryService.queryByPid(0);
        for (EyeCategory category : categoryList) {
            CategoryVo categoryVO = new CategoryVo();
            categoryVO.setId(category.getId());
            categoryVO.setDesc(category.getDesc());
            categoryVO.setIconUrl(category.getIconUrl());
            categoryVO.setPicUrl(category.getPicUrl());
            categoryVO.setKeywords(category.getKeywords());
            categoryVO.setName(category.getName());
            categoryVO.setLevel(category.getLevel());
            categoryVO.setPid(category.getPid());
            categoryVO.setSortOrder(category.getSortOrder());

            List<CategoryVo> children = new ArrayList<>();
            List<EyeCategory> subCategoryList = categoryService.queryByPid(category.getId());
            for (EyeCategory subCategory : subCategoryList) {
                CategoryVo subCategoryVo = new CategoryVo();
                subCategoryVo.setId(subCategory.getId());
                subCategoryVo.setDesc(subCategory.getDesc());
                subCategoryVo.setIconUrl(subCategory.getIconUrl());
                subCategoryVo.setPicUrl(subCategory.getPicUrl());
                subCategoryVo.setKeywords(subCategory.getKeywords());
                subCategoryVo.setName(subCategory.getName());
                subCategoryVo.setLevel(subCategory.getLevel());
                subCategoryVo.setPid(subCategory.getPid());
                subCategoryVo.setSortOrder(subCategory.getSortOrder());

                children.add(subCategoryVo);
            }

            categoryVO.setChildren(children);
            categoryVoList.add(categoryVO);
        }

        return ResponseUtil.okList(categoryVoList);
    }

    private Object validate(EyeCategory category) {
        String name = category.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        String level = category.getLevel();
        if (StringUtils.isEmpty(level)) {
            return ResponseUtil.badArgument();
        }
        if (!level.equals("L1") && !level.equals("L2")) {
            return ResponseUtil.badArgumentValue();
        }

        Integer pid = category.getPid();
        if (level.equals("L2") && (pid == null)) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @ApiOperation(value = "类目管理添加")
    @RequiresPermissions("admin:category:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品类目"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeCategory category) {
        Object error = validate(category);
        if (error != null) {
            return error;
        }
        categoryService.add(category);
        return ResponseUtil.ok(category);
    }

    @ApiOperation(value = "类目管理详情")
    @RequiresPermissions("admin:category:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品类目"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "类目id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeCategory category = categoryService.findById(id);
        return ResponseUtil.ok(category);
    }

    @ApiOperation(value = "类目管理编辑")
    @RequiresPermissions("admin:category:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品类目"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeCategory category) {
        Object error = validate(category);
        if (error != null) {
            return error;
        }

        if (categoryService.updateById(category) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "类目管理删除")
    @RequiresPermissions("admin:category:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品类目"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeCategory category) {
        Integer id = category.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        categoryService.deleteById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "类目管理获取一级类目")
    @RequiresPermissions("admin:category:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "类目id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/l1")
    public Object catL1() {
        // 所有一级分类目录
        List<EyeCategory> l1CatList = categoryService.queryL1();
        List<Map<String, Object>> data = new ArrayList<>(l1CatList.size());
        for (EyeCategory category : l1CatList) {
            Map<String, Object> d = new HashMap<>(2);
            d.put("value", category.getId());
            d.put("label", category.getName());
            data.add(d);
        }
        return ResponseUtil.okList(data);
    }
}
