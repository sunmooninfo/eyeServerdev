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
import com.eye.db.domain.EyeAd;
import com.eye.db.service.EyeAdService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/ad")
@Validated
@Api(description = "广告管理")
public class AdminAdController {
    private final Log logger = LogFactory.getLog(AdminAdController.class);

    @Autowired
    private EyeAdService adService;

    @ApiOperation(value = "广告管理查询")
    @RequiresPermissions("admin:ad:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "广告管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "广告标题",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="content",value = "活动内容",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String name, String content,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeAd> adList = adService.querySelective(name, content, page, limit, sort, order);
        return ResponseUtil.okList(adList);
    }

    private Object validate(EyeAd ad) {
        String name = ad.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String content = ad.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "广告管理添加")
    @RequiresPermissions("admin:ad:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "广告管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeAd ad) {
        Object error = validate(ad);
        if (error != null) {
            return error;
        }
        adService.add(ad);
        return ResponseUtil.ok(ad);
    }

    @ApiOperation(value = "广告管理详情")
    @RequiresPermissions("admin:ad:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "广告管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "广告标题",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="content",value = "活动内容",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeAd ad = adService.findById(id);
        return ResponseUtil.ok(ad);
    }

    @ApiOperation(value = "广告管理编辑")
    @RequiresPermissions("admin:ad:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "广告管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeAd ad) {
        Object error = validate(ad);
        if (error != null) {
            return error;
        }
        if (adService.updateById(ad) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok(ad);
    }

    @ApiOperation(value = "广告管理删除")
    @RequiresPermissions("admin:ad:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "广告管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeAd ad) {
        Integer id = ad.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        adService.deleteById(id);
        return ResponseUtil.ok();
    }

}
