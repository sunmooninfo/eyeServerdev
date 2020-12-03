package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.system.SystemConfig;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.service.EyeSystemConfigService;

import java.util.*;

@Api(description = "系统信息")
@RestController
@RequestMapping("/admin/config")
@Validated
public class AdminConfigController {
    private final Log logger = LogFactory.getLog(AdminConfigController.class);

    @Autowired
    private EyeSystemConfigService systemConfigService;

    @ApiOperation(value = "系统信息详情")
    @RequiresPermissions("admin:config:mall:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "系统信息"}, button = "详情")
    @GetMapping("/mall")
    public Object listMall() {
        Map<String, String> map = systemConfigService.listMail();
        return ResponseUtil.ok(map);
    }

    @ApiOperation(value = "系统信息编辑")
    @RequiresPermissions("admin:config:mall:updateConfigs")
    @RequiresPermissionsDesc(menu = {"系统管理", "系统信息"}, button = "编辑")
    @PostMapping("/mall")
    public Object updateMall(@RequestBody String body) {
        Map<String, String> data = JacksonUtil.toMap(body);
        systemConfigService.updateConfig(data);
        SystemConfig.updateConfigs(data);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "系统信息运费详情")
    @RequiresPermissions("admin:config:express:list")
    @RequiresPermissionsDesc(menu = {"商城管理", "运费配置"}, button = "详情")
    @GetMapping("/express")
    public Object listExpress() {
        Map<String, String> data = systemConfigService.listExpress();
        return ResponseUtil.ok(data);
    }

    @ApiOperation(value = "系统信息运费编辑")
    @RequiresPermissions("admin:config:express:updateConfigs")
    @RequiresPermissionsDesc(menu = {"商城管理", "运费配置"}, button = "编辑")
    @PostMapping("/express")
    public Object updateExpress(@RequestBody String body) {
        Map<String, String> data = JacksonUtil.toMap(body);
        systemConfigService.updateConfig(data);
        SystemConfig.updateConfigs(data);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "系统信息订单详情")
    @RequiresPermissions("admin:config:order:list")
    @RequiresPermissionsDesc(menu = {"商城管理", "订单配置"}, button = "详情")
    @GetMapping("/order")
    public Object lisOrder() {
        Map<String, String> data = systemConfigService.listOrder();
        return ResponseUtil.ok(data);
    }

    @ApiOperation(value = "系统信息订单编辑")
    @RequiresPermissions("admin:config:order:updateConfigs")
    @RequiresPermissionsDesc(menu = {"商城管理", "订单配置"}, button = "编辑")
    @PostMapping("/order")
    public Object updateOrder(@RequestBody String body) {
        Map<String, String> data = JacksonUtil.toMap(body);
        systemConfigService.updateConfig(data);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "系统信息首页详情")
    @RequiresPermissions("admin:config:wx:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "首页管理"}, button = "详情")
    @GetMapping("/wx")
    public Object listWx() {
        Map<String, String> data = systemConfigService.listWx();
        return ResponseUtil.ok(data);
    }

    @ApiOperation(value = "系统信息首页编辑")
    @RequiresPermissions("admin:config:wx:updateConfigs")
    @RequiresPermissionsDesc(menu = {"系统管理", "首页管理"}, button = "编辑")
    @PostMapping("/wx")
    public Object updateWx(@RequestBody String body) {
        Map<String, String> data = JacksonUtil.toMap(body);
        systemConfigService.updateConfig(data);
        SystemConfig.updateConfigs(data);
        return ResponseUtil.ok();
    }
}
