package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeUserService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "用户管理")
@RestController
@RequestMapping("/admin/user")
@Validated
public class AdminUserController {
    private final Log logger = LogFactory.getLog(AdminUserController.class);

    @Autowired
    private EyeUserService userService;

    @ApiOperation(value = "会员管理查询")
    @RequiresPermissions("admin:user:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "会员管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value = "用户名",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="mobile",value = "电话",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="userLevel",value = "用户标准",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String username, String mobile, Byte userLevel,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeUser> userList = userService.querySelective(username, mobile, userLevel, page, limit, sort, order);
        return ResponseUtil.okList(userList);
    }

    @ApiOperation(value = "用户管理详情")
    @RequiresPermissions("admin:user:list")
//    @RequiresPermissionsDesc(menu = {"用户管理", "会员管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "用户id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/detail")
    public Object userDetail(@NotNull Integer id) {
        EyeUser user=userService.findById(id);
        return ResponseUtil.ok(user);
    }

    @ApiOperation(value = "用户管理编辑")
    @RequiresPermissions("admin:user:list")
//    @RequiresPermissionsDesc(menu = {"用户管理", "会员管理"}, button = "编辑")
    @PostMapping("/update")
    public Object userUpdate(@RequestBody EyeUser user) {
        return ResponseUtil.ok(userService.updateById(user));
    }

    @ApiOperation(value = "用户管理查询名字")
    @RequiresPermissions("admin:user:list")
    @GetMapping("/name")
    public Object name(){
        List<EyeUser> list = userService.queryName();
        List<Map<String, Object>> data = new ArrayList<>(list.size());
        for (EyeUser user : list) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("value",user.getId());
            map.put("name",user.getNickname());
            data.add(map);
        }
        return ResponseUtil.okList(data);
    }
}
