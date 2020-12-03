package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonMemberOrderService;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/common/member/order")
@Validated
@Api(description = "会员套餐订单服务")
public class CommonMemberOrderController {
    private final Log logger = LogFactory.getLog(CommonMemberOrderController.class);

    @Autowired
    private CommonMemberOrderService memberOrderService;

    @GetMapping("list")
    @ApiOperation("用户会员套餐订单")
    @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        String info  = "展示用户订单列表";
        logger.info(getClass()+"----------------------------"+info);
        return memberOrderService.list(userId, showType, page, limit, sort, order);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param memberOrderId 订单ID
     * @return 订单详情
     */
    @GetMapping("detail")
    @ApiOperation("订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="memberOrderId",value = "会员套餐订单id",required=true,paramType="path",dataType="int")
    })
    public Object detail(@LoginUser Integer userId, @NotNull Integer memberOrderId) {
        String info  = "查询用户订单详情";
        logger.info(getClass()+"----------------------------"+info);
        return memberOrderService.detail(userId, memberOrderId);
    }

    //提交订单
    @PostMapping("submit")
    @ApiOperation("提交订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{\n" +
                    "\t\"memberPackageId(会员套餐id)\":xxx\n" +
                    "}",required=true,dataType="string")
    })
    public Object submit(@LoginUser Integer userId, @RequestBody String body) {
        String info  = "提交用户订单";
        logger.info(getClass()+"----------------------------"+info);
        return memberOrderService.submit(userId, body);
    }


    /**
     * 付款订单的预支付会话标识
     *
     * @param userId 用户ID
     * @param body   订单信息，{ memberOrderId：xxx }
     * @return 支付订单ID
     */
    @PostMapping("prepay")
    @ApiOperation("付款订单的预支付会话标识")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{\n" +
                    "\t\"memberOrderId(订单id)\":xxx\n" +
                    "}",required=true,dataType="string")
    })
    public Object prepay(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        return memberOrderService.prepay(userId, body, request);
    }

    /**
     * APP支付预支付
     * @param userId
     * @param body { orderId : xxx}
     * @param request
     * @return
     */
    @PostMapping("apppay")
    @ApiOperation("APP预支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{\n" +
                    "\t\"orderId(订单id)\":xxx\n" +
                    "}",required=true,dataType="string")
    })
    public Object apppay(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request){
        String info = "APP支付预支付";
        logger.info(info);

        return memberOrderService.apppay(userId,body,request);
    }

    /**
     * 微信H5支付
     * @param userId
     * @param body
     * @param request
     * @return
     */
    @PostMapping("h5pay")
    @ApiOperation("微信H5支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object h5pay(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        String info  = "微信支付";
        logger.info(getClass()+"----------------------------"+info);
        return memberOrderService.h5pay(userId, body, request);
    }

    /**
     * 取消订单
     *
     * @param userId 用户ID
     * @param body   订单信息，{ memberOrderId：xxx }
     * @return 取消订单操作结果
     */
    @PostMapping("cancel")
    @ApiOperation("取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{\n" +
                    "\t\"memberOrderId(订单id)\":xxx\n" +
                    "}",required=true,dataType="string")
    })
    public Object cancel(@LoginUser Integer userId, @RequestBody String body) {
        String info  = "取消用户订单";
        logger.info(getClass()+"----------------------------"+info);
        return memberOrderService.cancel(userId, body);
    }

    /**
     * 删除订单
     *
     * @param userId 用户ID
     * @param body   订单信息，{ memberOrderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("delete")
    @ApiOperation("删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{\n" +
                    "\t\"memberOrderId(订单id)\":xxx\n" +
                    "}",required=true,dataType="string")
    })
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        return memberOrderService.delete(userId, body);
    }

    /**
     * 微信付款成功或失败回调接口
     * <p>
     *  TODO
     *  注意，这里pay-notify是示例地址，建议开发者应该设立一个隐蔽的回调地址
     *
     * @param request 请求内容
     * @param response 响应内容
     * @return 操作结果
     */
    @PostMapping("pay-notify")
    @ApiOperation("会员套餐微信付款成功或失败回调接口")
    public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
        return memberOrderService.payNotify(request, response);
    }
}
