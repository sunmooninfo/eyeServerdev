package com.eye.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.service.AdminMemberOrderService;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Api(description = "超级会员订单管理")
@RestController
@RequestMapping("/admin/member/order")
@Validated
public class AdminMemberOrderController {
    private final Log logger = LogFactory.getLog(AdminMemberOrderController.class);
    @Autowired
    private AdminMemberOrderService memberOrderService;

    /**
     * 查询订单
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @ApiOperation(value = "超级会员订单管理查询")
    @RequiresPermissions("admin:memberorder:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "超级会员订单"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="orderSn",value = "订单编号",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(Integer userId, String orderSn,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                       @RequestParam(required = false) List<Short> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return memberOrderService.list(userId, orderSn, start, end, orderStatusArray, page, limit, sort, order);
    }


    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:memberorder:detail")
    @RequiresPermissionsDesc(menu = {"商品管理", "超级会员订单"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return memberOrderService.detail(id);
    }
    /**
     * 删除订单
     *
     * @param body 订单信息，{ memberOrderId：xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:memberorder:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "超级会员订单"}, button = "订单删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody String body) {
        return memberOrderService.delete(body);
    }
}
