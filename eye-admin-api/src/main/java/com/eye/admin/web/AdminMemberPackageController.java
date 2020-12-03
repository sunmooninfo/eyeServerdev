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
import com.eye.db.domain.EyeMemberOrder;
import com.eye.db.domain.EyeMemberPackage;
import com.eye.db.service.EyeMemberOrderService;
import com.eye.db.service.EyeMemberPackageService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "超级会员套餐设置")
@RestController
@RequestMapping("/admin/member/package")
@Validated
public class AdminMemberPackageController {
    private final Log logger = LogFactory.getLog(AdminMemberPackageController.class);

    @Autowired
    private EyeMemberPackageService packageService;

    @Autowired
    private EyeMemberOrderService memberOrderService;

    @ApiOperation(value = "超级会员套餐设置查询")
    @RequiresPermissions("admin:memberpackage:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "超级会员套餐设置"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "会员套餐名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        List<EyeMemberPackage> packageList = packageService.querySelective(name,page,limit,sort,order);
        return ResponseUtil.okList(packageList);
    }

    @ApiOperation(value = "超级会员套餐设置查询用户")
    @RequiresPermissions("admin:memberpackage:listuser")
    //此权限现在不使用，后期使用在做修改
//    @RequiresPermissionsDesc(menu = {"用户管理", "超级会员套餐设置"}, button = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="memberPackageId",value = "vip套餐id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="status",value = "状态",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/listuser")
    public Object listuser(Integer userId, Integer memberPackageId, Short status,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order){
        List<EyeMemberOrder> memberOrderList = memberOrderService.queryList(userId,memberPackageId,status,page,limit,sort,order);
        return ResponseUtil.okList(memberOrderList);
    }

    //需要将商品id,有效期
    @ApiOperation(value = "超级会员套餐设置添加")
    @RequiresPermissions("admin:memberpackage:create")
    @RequiresPermissionsDesc(menu = {"用户管理", "超级会员套餐设置"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeMemberPackage memberPackage) {
        packageService.add(memberPackage);
        return ResponseUtil.ok(memberPackage.getId());
    }

    @ApiOperation(value = "超级会员套餐设置详情")
    @RequiresPermissions("admin:memberpackage:read")
    @RequiresPermissionsDesc(menu = {"用户管理", "超级会员套餐设置"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id){
        EyeMemberPackage memberPackage = packageService.findById(id);
        return ResponseUtil.ok(memberPackage);
    }

    @ApiOperation(value = "超级会员套餐设置编辑")
    @RequiresPermissions("admin:memberpackage:update")
    @RequiresPermissionsDesc(menu = {"用户管理", "超级会员套餐设置"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeMemberPackage memberPackage){

        if (packageService.updateById(memberPackage) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(memberPackage);
    }

    @ApiOperation(value = "超级会员套餐设置删除")
    @RequiresPermissions("admin:memberpackage:delete")
    @RequiresPermissionsDesc(menu = {"用户管理", "超级会员套餐设置"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeMemberPackage memberPackage){
        packageService.deleteById(memberPackage.getId());
        return ResponseUtil.ok();
    }
}
