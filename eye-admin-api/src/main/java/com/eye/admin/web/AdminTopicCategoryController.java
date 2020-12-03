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

import com.eye.admin.vo.CategoryVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeTopic;
import com.eye.db.domain.EyeTopicCategory;
import com.eye.db.service.EyeTopicCategoryService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "专题文章管理")
@RestController
@RequestMapping("/admin/topic/category")
@Validated
//此权限暂时为使用，后期用到再做修改。
public class AdminTopicCategoryController {

    private final Log logger = LogFactory.getLog(AdminTopicCategoryController.class);

    @Autowired
    private EyeTopicCategoryService topicCategoryService;

    /**
     * 根据文章分类的parentId
     * @return
     */
    @ApiOperation(value = "专题文章管理查询")
    @RequiresPermissions("admin:topiccategory:list")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题文章管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "文章分类名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list() {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<EyeTopicCategory> topicCategoryList = this.topicCategoryService.queryByPid(0);
        for(EyeTopicCategory topicCategory : topicCategoryList){
            CategoryVo categoryVO = new CategoryVo();
            categoryVO.setId(topicCategory.getId());
            categoryVO.setDesc(topicCategory.getDesc());
            categoryVO.setIconUrl(topicCategory.getIconUrl());
            categoryVO.setPicUrl(topicCategory.getPicUrl());
            categoryVO.setKeywords(topicCategory.getKeywords());
            categoryVO.setName(topicCategory.getName());
            categoryVO.setLevel(topicCategory.getLevel());
            categoryVO.setPid(topicCategory.getPid());
            categoryVO.setSortOrder(topicCategory.getSortOrder());
            categoryVO.setDeleted(topicCategory.getDeleted());
            List<CategoryVo> children = new ArrayList<>();
            List<EyeTopicCategory> subTopicCategoryList = topicCategoryService.queryByPid(topicCategory.getId());
            for(EyeTopicCategory subTopicCategory : subTopicCategoryList){
                CategoryVo subCategoryVo = new CategoryVo();
                subCategoryVo.setId(subTopicCategory.getId());
                subCategoryVo.setDesc(subTopicCategory.getDesc());
                subCategoryVo.setIconUrl(subTopicCategory.getIconUrl());
                subCategoryVo.setPicUrl(subTopicCategory.getPicUrl());
                subCategoryVo.setKeywords(subTopicCategory.getKeywords());
                subCategoryVo.setName(subTopicCategory.getName());
                subCategoryVo.setLevel(subTopicCategory.getLevel());
                subCategoryVo.setPid(subTopicCategory.getPid());
                subCategoryVo.setSortOrder(subTopicCategory.getSortOrder());
                subCategoryVo.setDeleted(subTopicCategory.getDeleted());
                children.add(subCategoryVo);
            }
            categoryVO.setChildren(children);
            categoryVoList.add(categoryVO);
        }

        return ResponseUtil.okList(categoryVoList);
    }

    /**
     * 添加专题文章分类
     * @param topicCategory
     * @return
     */
    @ApiOperation(value = "专题文章管理添加")
    @RequiresPermissions("admin:topiccategory:create")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题文章分类"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeTopicCategory topicCategory) {
        Object error = validate(topicCategory);
        if (error != null) {
            return error;
        }
        this.topicCategoryService.add(topicCategory);
        return ResponseUtil.ok();
    }
    private Object validate(EyeTopicCategory topicategory) {
        String topicName = topicategory.getName();
        if (StringUtils.isEmpty(topicName)) {
            return ResponseUtil.badArgument();
        }

        String level = topicategory.getLevel();
        if (StringUtils.isEmpty(level)) {
            return ResponseUtil.badArgument();
        }
        if (!level.equals("L1") && !level.equals("L2")) {
            return ResponseUtil.badArgumentValue();
        }

        Integer pid = topicategory.getPid();
        if (level.equals("L2") && (pid == null)) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @ApiOperation(value = "专题文章管理详情")
    @RequiresPermissions("admin:topiccategory:read")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题文章分类"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "文章分类名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeTopicCategory EyeTopicCategory = topicCategoryService.findById(id);
        return ResponseUtil.ok(EyeTopicCategory);
    }

    /**
     * 更新专题文章分类
     * @param topicCategory
     * @return
     */
    @ApiOperation(value = "专题文章管理编辑")
    @RequiresPermissions("admin:topiccategory:update")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题文章分类"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeTopicCategory topicCategory) {
        Object error = validate(topicCategory);
        if (error != null) {
            return error;
        }
        if (this.topicCategoryService.updateById(topicCategory) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    /**
     * 根据topicCategoryId删除专题文章分类
     *
     * @return
     */
    @ApiOperation(value = "专题文章管理删除")
    @RequiresPermissions("admin:topiccategory:delete")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题文章分类"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeTopic topic) {
        Integer id = topic.getId();
        if(id == null ){
            return  ResponseUtil.badArgument();
        }
        this.topicCategoryService.deleteById(id);

        return ResponseUtil.ok();
    }

    @ApiOperation(value = "专题文章管理分类目录")
    @RequiresPermissions("admin:topiccategory:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "文章分类名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/l1")
    public Object catL1() {
        // 所有一级分类目录
        List<EyeTopicCategory> l1CatList = topicCategoryService.queryL1();
        List<Map<String, Object>> data = new ArrayList<>(l1CatList.size());
        for (EyeTopicCategory topicCategory : l1CatList) {
            Map<String, Object> d = new HashMap<>(2);
            d.put("value", topicCategory.getId());
            d.put("label", topicCategory.getName());
            data.add(d);
        }
        return ResponseUtil.okList(data);
    }
}
