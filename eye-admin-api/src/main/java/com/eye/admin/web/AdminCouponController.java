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
import com.eye.db.domain.EyeCoupon;
import com.eye.db.domain.EyeCouponUser;
import com.eye.db.service.EyeCouponService;
import com.eye.db.service.EyeCouponUserService;
import com.eye.db.util.CouponConstant;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "优惠券管理")
@RestController
@RequestMapping("/admin/coupon")
@Validated
public class AdminCouponController {
    private final Log logger = LogFactory.getLog(AdminCouponController.class);

    @Autowired
    private EyeCouponService couponService;
    @Autowired
    private EyeCouponUserService couponUserService;

    @ApiOperation(value = "优惠券管理查询")
    @RequiresPermissions("admin:coupon:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "优惠券管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "优惠券名称",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="type",value = "优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换",
                    required=true,paramType="path",dataType="smallint"),
            @ApiImplicitParam(name="status",value = "优惠券状态，如果是0则是正常可用；如果是1则是过期; 如果是2则是下架。",
                    required=true,paramType="path",dataType="smallint")
    })
    @GetMapping("/list")
    public Object list(String name, Short type, Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeCoupon> couponList = couponService.querySelective(name, type, status, page, limit, sort, order);
        return ResponseUtil.okList(couponList);
    }

    @ApiOperation(value = "优惠券管理查询用户")
    @RequiresPermissions("admin:coupon:listuser")
    @RequiresPermissionsDesc(menu = {"商品管理", "优惠券管理"}, button = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="couponId",value = "优惠券id", required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="status",value = "优惠券状态，如果是0则是正常可用；如果是1则是过期; 如果是2则是下架。",
                    required=true,paramType="path",dataType="smallint")
    })
    @GetMapping("/listuser")
    public Object listuser(Integer userId, Integer couponId, Short status,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeCouponUser> couponList = couponUserService.queryList(userId, couponId, status, page,
                limit, sort, order);
        return ResponseUtil.okList(couponList);
    }

    private Object validate(EyeCoupon coupon) {
        String name = coupon.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "优惠券管理添加")
    @RequiresPermissions("admin:coupon:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "优惠券管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeCoupon coupon) {
        Object error = validate(coupon);
        if (error != null) {
            return error;
        }

        // 如果是兑换码类型，则这里需要生存一个兑换码
        if (coupon.getType().equals(CouponConstant.TYPE_CODE)) {
            String code = couponService.generateCode();
            coupon.setCode(code);
        }

        couponService.add(coupon);
        return ResponseUtil.ok(coupon);
    }

    @ApiOperation(value = "优惠券管理详情")
    @RequiresPermissions("admin:coupon:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "优惠券管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "优惠券名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeCoupon coupon = couponService.findById(id);
        return ResponseUtil.ok(coupon);
    }

    @ApiOperation(value = "优惠券管理编辑")
    @RequiresPermissions("admin:coupon:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "优惠券管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeCoupon coupon) {
        Object error = validate(coupon);
        if (error != null) {
            return error;
        }
        if (couponService.updateById(coupon) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(coupon);
    }

    @ApiOperation(value = "优惠券管理删除")
    @RequiresPermissions("admin:coupon:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "优惠券管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeCoupon coupon) {
        couponService.deleteById(coupon.getId());
        return ResponseUtil.ok();
    }

}
