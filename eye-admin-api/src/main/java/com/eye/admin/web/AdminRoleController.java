package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.util.AdminResponseCode;
import com.eye.admin.util.Permission;
import com.eye.admin.util.PermissionUtil;
import com.eye.admin.vo.PermVo;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyePermission;
import com.eye.db.domain.EyeRole;
import com.eye.db.service.EyeAdminService;
import com.eye.db.service.EyePermissionService;
import com.eye.db.service.EyeRoleService;
import javax.validation.constraints.NotNull;
import java.util.*;
import static com.eye.admin.util.AdminResponseCode.ROLE_NAME_EXIST;
import static com.eye.admin.util.AdminResponseCode.ROLE_USER_EXIST;

@Api(description = "角色管理")
@RestController
@RequestMapping("/admin/role")
@Validated
public class AdminRoleController {
    private final Log logger = LogFactory.getLog(AdminRoleController.class);

    @Autowired
    private EyeRoleService roleService;
    @Autowired
    private EyePermissionService permissionService;
    @Autowired
    private EyeAdminService adminService;

    @ApiOperation(value = "角色查询")
    @RequiresPermissions("admin:role:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "角色查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "角色名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeRole> roleList = roleService.querySelective(name, page, limit, sort, order);
        return ResponseUtil.okList(roleList);
    }

    @ApiOperation(value = "角色查询所有信息")
    @GetMapping("/options")
    public Object options() {
        List<EyeRole> roleList = roleService.queryAll();

        List<Map<String, Object>> options = new ArrayList<>(roleList.size());
        for (EyeRole role : roleList) {
            Map<String, Object> option = new HashMap<>(2);
            option.put("value", role.getId());
            option.put("label", role.getName());
            options.add(option);
        }

        return ResponseUtil.okList(options);
    }

    @ApiOperation(value = "角色详情")
    @RequiresPermissions("admin:role:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "角色详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleId",value = "角色id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeRole role = roleService.findById(id);
        return ResponseUtil.ok(role);
    }


    private Object validate(EyeRole role) {
        String name = role.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @ApiOperation(value = "角色添加")
    @RequiresPermissions("admin:role:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "角色添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeRole role) {
        Object error = validate(role);
        if (error != null) {
            return error;
        }

        if (roleService.checkExist(role.getName())) {
            return ResponseUtil.fail(ROLE_NAME_EXIST, "角色已经存在");
        }

        roleService.add(role);

        return ResponseUtil.ok(role);
    }

    @ApiOperation(value = "角色编辑")
    @RequiresPermissions("admin:role:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "角色编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeRole role) {
        Object error = validate(role);
        if (error != null) {
            return error;
        }

        roleService.updateById(role);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "角色删除")
    @RequiresPermissions("admin:role:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "角色删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeRole role) {
        Integer id = role.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        // 如果当前角色所对应管理员仍存在，则拒绝删除角色。
        List<EyeAdmin> adminList = adminService.all();
        for (EyeAdmin admin : adminList) {
            Integer[] roleIds = admin.getRoleIds();
            for (Integer roleId : roleIds) {
                if (id.equals(roleId)) {
                    return ResponseUtil.fail(ROLE_USER_EXIST, "当前角色存在管理员，不能删除");
                }
            }
        }

        roleService.deleteById(id);
        return ResponseUtil.ok();
    }


    @Autowired
    private ApplicationContext context;
    private List<PermVo> systemPermissions = null;
    private Set<String> systemPermissionsString = null;

    private List<PermVo> getSystemPermissions() {
        final String basicPackage = "com.eye.admin";
//        final String basicPckkage1 = "com.ulove.admin";
        if (systemPermissions == null) {
            List<Permission> permissions = PermissionUtil.listPermission(context, basicPackage);
//            List<Permission> permissions1 = PermissionUtil.listPermission(context, basicPckkage1);
//            for(Permission permission : permissions1){
//                permissions.add(permission);
//            }
            systemPermissions = PermissionUtil.listPermVo(permissions);
            systemPermissionsString = PermissionUtil.listPermissionString(permissions);
        }
        return systemPermissions;
    }

    private Set<String> getAssignedPermissions(Integer roleId) {
        // 这里需要注意的是，如果存在超级权限*，那么这里需要转化成当前所有系统权限。
        // 之所以这么做，是因为前端不能识别超级权限，所以这里需要转换一下。
        Set<String> assignedPermissions = null;
        if (permissionService.checkSuperPermission(roleId)) {
            getSystemPermissions();
            assignedPermissions = systemPermissionsString;
        } else {
            assignedPermissions = permissionService.queryByRoleId(roleId);
        }

        return assignedPermissions;
    }

    /**
     * 管理员的权限情况
     *
     * @return 系统所有权限列表和管理员已分配权限
     */
    @ApiOperation(value = "角色管理权限详情")
    @RequiresPermissions("admin:role:permission:get")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "权限详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleId",value = "角色id",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/permissions")
    public Object getPermissions(Integer roleId) {
        List<PermVo> systemPermissions = getSystemPermissions();
        Set<String> assignedPermissions = getAssignedPermissions(roleId);

        Map<String, Object> data = new HashMap<>();
        data.put("systemPermissions", systemPermissions);
        data.put("assignedPermissions", assignedPermissions);
        return ResponseUtil.ok(data);
    }


    /**
     * 更新管理员的权限
     *
     * @param body
     * @return
     */
    @ApiOperation(value = "角色管理权限变更")
    @RequiresPermissions("admin:role:permission:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "角色管理"}, button = "权限变更")
    @PostMapping("/permissions")
    public Object updatePermissions(@RequestBody String body) {
        Integer roleId = JacksonUtil.parseInteger(body, "roleId");
        List<String> permissions = JacksonUtil.parseStringList(body, "permissions");
        if (roleId == null || permissions == null) {
            return ResponseUtil.badArgument();
        }

        // 如果修改的角色是超级权限，则拒绝修改。
        if (permissionService.checkSuperPermission(roleId)) {
            return ResponseUtil.fail(AdminResponseCode.ROLE_SUPER_SUPERMISSION, "当前角色的超级权限不能变更");
        }

        // 先删除旧的权限，再更新新的权限
        permissionService.deleteByRoleId(roleId);
        for (String permission : permissions) {
            EyePermission EyePermission = new EyePermission();
            EyePermission.setRoleId(roleId);
            EyePermission.setPermission(permission);
            permissionService.add(EyePermission);
        }
        return ResponseUtil.ok();
    }

}
