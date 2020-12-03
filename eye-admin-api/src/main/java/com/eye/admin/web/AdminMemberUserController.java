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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeMemberUser;
import com.eye.db.service.EyeMemberUserService;

import java.util.List;

@Api(description = "vip管理")
@RestController
@RequestMapping("/admin/member/user")
@Validated
//此权限暂时未用到先注释掉，后期用到再修改
public class AdminMemberUserController {
    private final Log logger = LogFactory.getLog(AdminMemberUserController.class);

    @Autowired
    private EyeMemberUserService memberUserService;

    @ApiOperation(value = "会员管理查询")
    @RequiresPermissions("admin:userx:list")
//    @RequiresPermissionsDesc(menu = {"用户管理", "vip管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/list")
    public Object list(Integer userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeMemberUser> userList = memberUserService.querySelective(userId,page, limit, sort, order);
        return ResponseUtil.okList(userList);
    }
}
