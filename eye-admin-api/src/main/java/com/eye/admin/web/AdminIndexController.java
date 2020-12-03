package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eye.core.utils.ResponseUtil;

@Api(description = "测试管理")
@RestController
@RequestMapping("/admin/index")
//此权限暂时用不到，后期需要在做修改。
public class AdminIndexController {
    private final Log logger = LogFactory.getLog(AdminIndexController.class);

    @ApiOperation(value = "测试管理首页")
    @RequestMapping("/index")
    public Object index() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

    @ApiOperation(value = "测试管理")
    @RequiresGuest
    @RequestMapping("/guest")
    public Object guest() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

    @ApiOperation(value = "测试管理权限")
    @RequiresAuthentication
    @RequestMapping("/authn")
    public Object authn() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

    @ApiOperation(value = "测试管理用户")
    @RequiresUser
    @RequestMapping("/user")
    public Object user() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

    @ApiOperation(value = "测试管理管理员")
    @RequiresRoles("admin")
    @RequestMapping("/admin")
    public Object admin() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

    @ApiOperation(value = "测试管理管理员")
    @RequiresRoles("admin2")
    @RequestMapping("/admin2")
    public Object admin2() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

    @ApiOperation(value = "权限测试读")
    @RequiresPermissions("index:permission:read")
//    @RequiresPermissionsDesc(menu = {"其他", "权限测试"}, button = "权限读")
    @GetMapping("/read")
    public Object read() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

    @ApiOperation(value = "权限测试写")
    @RequiresPermissions("index:permission:write")
//    @RequiresPermissionsDesc(menu = {"其他", "权限测试"}, button = "权限写")
    @PostMapping("/write")
    public Object write() {
        return ResponseUtil.ok("hello world, this is admin service");
    }

}
