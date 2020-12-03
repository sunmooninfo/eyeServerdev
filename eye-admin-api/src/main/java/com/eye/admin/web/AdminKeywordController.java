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
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeKeyword;
import com.eye.db.service.EyeKeywordService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "推荐搜索")
@RestController
@RequestMapping("/admin/keyword")
@Validated
public class AdminKeywordController {
    private final Log logger = LogFactory.getLog(AdminKeywordController.class);

    @Autowired
    private EyeKeywordService keywordService;

    @ApiOperation(value = "推荐搜索查询")
    @RequiresPermissions("admin:keyword:list")
    @RequiresPermissionsDesc(menu = {"商城管理", "推荐搜索"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword",value = "关键字",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="url",value = "关键字的跳转链接",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String keyword, String url,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeKeyword> keywordList = keywordService.querySelective(keyword, url, page, limit, sort, order);
        return ResponseUtil.okList(keywordList);
    }

    private Object validate(EyeKeyword keywords) {
        String keyword = keywords.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "推荐搜索添加")
    @RequiresPermissions("admin:keyword:create")
    @RequiresPermissionsDesc(menu = {"商城管理", "推荐搜索"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeKeyword keyword) {
        Object error = validate(keyword);
        if (error != null) {
            return error;
        }
        keywordService.add(keyword);
        return ResponseUtil.ok(keyword);
    }

    @ApiOperation(value = "推荐搜索详情")
    @RequiresPermissions("admin:keyword:read")
    @RequiresPermissionsDesc(menu = {"商城管理", "推荐搜索"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "关键字id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeKeyword keyword = keywordService.findById(id);
        return ResponseUtil.ok(keyword);
    }

    @ApiOperation(value = "推荐搜索编辑")
    @RequiresPermissions("admin:keyword:update")
    @RequiresPermissionsDesc(menu = {"商城管理", "推荐搜索"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeKeyword keyword) {
        Object error = validate(keyword);
        if (error != null) {
            return error;
        }
        if (keywordService.updateById(keyword) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(keyword);
    }

    @ApiOperation(value = "推荐搜索删除")
    @RequiresPermissions("admin:keyword:delete")
    @RequiresPermissionsDesc(menu = {"商城管理", "推荐搜索"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeKeyword keyword) {
        Integer id = keyword.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        keywordService.deleteById(id);
        return ResponseUtil.ok();
    }

}
