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
import com.eye.admin.service.AdminOrderService;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.express.ExpressService;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Api(description = "订单管理")
@RestController
@RequestMapping("/admin/order")
@Validated
public class AdminOrderController {
    private final Log logger = LogFactory.getLog(AdminOrderController.class);

    @Autowired
    private AdminOrderService adminOrderService;
    @Autowired
    private ExpressService expressService;

    /**
     * 查询订单
     *
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @ApiOperation(value = "订单管理查询")
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "订单管理"}, button = "查询")
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
        return adminOrderService.list(userId, orderSn, start, end, orderStatusArray, page, limit, sort, order);
    }

    /**
     * 查询物流公司
     *
     * @return
     */
    @ApiOperation(value = "订单管理查询物流公司")
    @GetMapping("/channel")
    public Object channel() {
        return ResponseUtil.ok(expressService.getVendors());
    }

    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "订单管理订单详情")
    @RequiresPermissions("admin:order:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "订单管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "订单id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return adminOrderService.detail(id);
    }

    /**
     * 订单退款
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    @ApiOperation(value = "订单管理订单退款")
    @RequiresPermissions("admin:order:refund")
    @RequiresPermissionsDesc(menu = {"商品管理", "订单管理"}, button = "订单退款")
    @PostMapping("/refund")
    public Object refund(@RequestBody String body) {
        return adminOrderService.refund(body);
    }

    /**
     * 发货
     *
     * @param body 订单信息，{ orderId：xxx, shipSn: xxx, shipChannel: xxx }
     * @return 订单操作结果
     */
    @ApiOperation(value = "订单管理订单发货")
    @RequiresPermissions("admin:order:ship")
    @RequiresPermissionsDesc(menu = {"商品管理", "订单管理"}, button = "订单发货")
    @PostMapping("/ship")
    public Object ship(@RequestBody String body) {
        return adminOrderService.ship(body);
    }


    /**
     * 删除订单
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @ApiOperation(value = "订单管理订单删除")
    @RequiresPermissions("admin:order:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "订单管理"}, button = "订单删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody String body) {
        return adminOrderService.delete(body);
    }

    /**
     * 回复订单商品
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @ApiOperation(value = "订单管理订单商品回复")
    @RequiresPermissions("admin:order:reply")
    @RequiresPermissionsDesc(menu = {"商品管理", "订单管理"}, button = "订单商品回复")
    @PostMapping("/reply")
    public Object reply(@RequestBody String body) {
        return adminOrderService.reply(body);
    }
}
