package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.service.LogHelper;
import com.eye.core.utils.RegexUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.utils.bcrypt.BCryptPasswordEncoder;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.service.EyeAdminService;

import javax.validation.constraints.NotNull;

import static com.eye.admin.util.AdminResponseCode.*;

import java.util.List;

@Api(description = "人员管理")
@RestController
@RequestMapping("/admin/admin")
@Validated
public class AdminAdminController {
    private final Log logger = LogFactory.getLog(AdminAdminController.class);

    @Autowired
    private EyeAdminService adminService;
    @Autowired
    private LogHelper logHelper;

    @ApiOperation(value = "人员查询")
    @RequiresPermissions("admin:admin:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "人员管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value = "管理员名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String username,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeAdmin> adminList = adminService.querySelective(username, page, limit, sort, order);
        return ResponseUtil.okList(adminList);
    }

    private Object validate(EyeAdmin admin) {
        String name = admin.getUsername();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isUsername(name)) {
            return ResponseUtil.fail(ADMIN_INVALID_NAME, "管理员名称不符合规定");
        }
        
        String password = admin.getPassword();
        if (StringUtils.isEmpty(password) || password.length() < 6) {
            return ResponseUtil.fail(ADMIN_INVALID_PASSWORD, "管理员密码长度不能小于6");
        }
        return null;
    }

    @ApiOperation(value = "人员添加")
    @RequiresPermissions("admin:admin:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "人员管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeAdmin admin) {
        Object error = validate(admin);
        if (error != null) {
            return error;
        }

        String username = admin.getUsername();
        List<EyeAdmin> adminList = adminService.findAdmin(username);
        if (adminList.size() > 0) {
            return ResponseUtil.fail(ADMIN_NAME_EXIST, "管理员已经存在");
        }

        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
        adminService.add(admin);
        logHelper.logAuthSucceed("添加管理员", username);
        return ResponseUtil.ok(admin);
    }

    @ApiOperation(value = "人员详情")
    @RequiresPermissions("admin:admin:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "人员管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value = "管理员名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeAdmin admin = adminService.findById(id);
        return ResponseUtil.ok(admin);
    }

    @ApiOperation(value = "人员编辑")
    @RequiresPermissions("admin:admin:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "人员管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeAdmin admin) {
        Object error = validate(admin);
        if (error != null) {
            return error;
        }

        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        // 不允许管理员通过编辑接口修改密码
        admin.setPassword(null);

        if (adminService.updateById(admin) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        logHelper.logAuthSucceed("编辑管理员", admin.getUsername());
        return ResponseUtil.ok(admin);
    }

    @ApiOperation(value = "人员删除")
    @RequiresPermissions("admin:admin:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "人员管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeAdmin admin) {
        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        // 管理员不能删除自身账号
        Subject currentUser = SecurityUtils.getSubject();
        EyeAdmin currentAdmin = (EyeAdmin) currentUser.getPrincipal();
        if (currentAdmin.getId().equals(anotherAdminId)) {
            return ResponseUtil.fail(ADMIN_DELETE_NOT_ALLOWED, "管理员不能删除自己账号");
        }

        adminService.deleteById(anotherAdminId);
        logHelper.logAuthSucceed("删除管理员", admin.getUsername());
        return ResponseUtil.ok();
    }
}
