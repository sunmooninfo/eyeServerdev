package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonOrderService;
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
@RequestMapping("/common/order")
@Validated
@Api(description = "订单服务")
public class CommonOrderController {
    private final Log logger = LogFactory.getLog(CommonOrderController.class);

    @Autowired
    private CommonOrderService commonOrderService;

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 显示类型，如果是0则是全部订单
     * @param page     分页页数
     * @param limit     分页大小
     * @param sort     排序字段
     * @param order     排序方式
     * @return 订单列表
     */
    @GetMapping("list")
    @ApiOperation("订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="showType",value = "显示类型",required=true,paramType="path",dataType="int")
    })
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        String info  = "展示用户订单列表";
        logger.info(getClass()+"----------------------------"+info);
        return commonOrderService.list(userId, showType, page, limit, sort, order);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("detail")
    @ApiOperation("订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="orderId",value = "订单ID",required=true,paramType="path",dataType="int")
    })
    public Object detail(@LoginUser Integer userId, @NotNull Integer orderId) {
        String info  = "查询用户订单详情";
        logger.info(getClass()+"----------------------------"+info);
        return commonOrderService.detail(userId, orderId);
    }

    @GetMapping("showLink")
    @ApiOperation("展示订单商品详情的下载链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="orderId",value = "订单ID",required=true,paramType="path",dataType="int")
    })
    public Object showLink(@LoginUser Integer userId,@NotNull Integer orderId){
        String info  = "展示订单商品详情的下载链接";
        logger.info(getClass()+"----------------------------"+info);
        return commonOrderService.showLink(userId,orderId);
    }

    /**
     * 提交订单
     *
     * @param userId 用户ID
     * @param body   订单信息，{ cartId：xxx, addressId: xxx, couponId: xxx, message: xxx, grouponRulesId: xxx,  grouponLinkId: xxx}
     * @return 提交订单操作结果
     */
    @PostMapping("submit")
    @ApiOperation("提交订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ cartId(购物车id)：xxx, addressId(地址id): xxx, couponId(优惠券id): xxx, userCouponId(用户优惠券id): xxx, message(用户留言): xxx, grouponRulesId(团购规则id): xxx, grouponLinkId(团购活动id): xxx}",required=true,dataType="string")
    })
    public Object submit(@LoginUser Integer userId, @RequestBody String body) {
        String info  = "提交用户订单";
        logger.info(getClass()+"----------------------------"+info);
        return commonOrderService.submit(userId, body);
    }

    /**
     * 取消订单
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 取消订单操作结果
     */
    @PostMapping("cancel")
    @ApiOperation("取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object cancel(@LoginUser Integer userId, @RequestBody String body) {
        String info  = "取消用户订单";
        logger.info(getClass()+"----------------------------"+info);
        return commonOrderService.cancel(userId, body);
    }

    /**
     * 付款订单的预支付会话标识
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 支付订单ID
     */
    @PostMapping("prepay")
    @ApiOperation("付款订单的预支付会话标识")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object prepay(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        return commonOrderService.prepay(userId, body, request);
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
        return commonOrderService.h5pay(userId, body, request);
    }

    /**
     * APP支付预支付
     * @param userId
     * @param body { orderId : xxx}
     * @param request
     * @return
     */
    @PostMapping("apppay")
    @ApiOperation("APP支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object apppay(@LoginUser Integer userId,@RequestBody String body,HttpServletRequest request){
       String info = "APP支付预支付";
       logger.info(info);

        return commonOrderService.apppay(userId,body,request);
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
    @ApiOperation("微信付款成功或失败回调接口")
    public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
        return commonOrderService.payNotify(request, response);
    }

    /**
     * 订单申请退款
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    @PostMapping("refund")
    @ApiOperation("订单申请退款")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object refund(@LoginUser Integer userId, @RequestBody String body) {
        return commonOrderService.refund(userId, body);
    }

    /**
     * 确认收货
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("confirm")
    @ApiOperation("确认收货")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object confirm(@LoginUser Integer userId, @RequestBody String body) {
        return commonOrderService.confirm(userId, body);
    }

    /**
     * 删除订单
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("delete")
    @ApiOperation("删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        return commonOrderService.delete(userId, body);
    }

    /**
     * 待评价订单商品信息
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @return 待评价订单商品信息
     */
    @GetMapping("goods")
    @ApiOperation("待评价订单商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="orderId",value = "订单ID",required=true,dataType="int"),
            @ApiImplicitParam(name="goodsId",value = "商品ID",required=true,dataType="int")
    })
    public Object goods(@LoginUser Integer userId,
                        @NotNull Integer orderId,
                        @NotNull Integer goodsId) {
        return commonOrderService.goods(userId, orderId, goodsId);
    }

    /**
     * 评价订单商品
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("comment")
    @ApiOperation("评价订单商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{ orderId(订单ID)：xxx }",required=true,dataType="string")
    })
    public Object comment(@LoginUser Integer userId, @RequestBody String body) {
        return commonOrderService.comment(userId, body);
    }

    /**
     * 提交订单
     * 用户购买的是积分商城里的商品,不添加到购物车,直接添加到订单表和订单商品表中
     * @param userId 用户ID
     * @param body   订单信息，{ addressId: xxx,  message: xxx, integralGoodsId: xxx, productId: xxx, number:xxx}
     * @return 提交订单操作结果
     */
    @PostMapping("/submitIntegral")
    @ApiOperation("积分商品提交订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{addressId(地址id): xxx, message(用户留言): xxx, integralGoodsId(积分商品id): xxx, productId(商品规格id): xxx, number(积分商品数量):xxx}",required=true,dataType="string")
    })
    public Object submitIntegral(@LoginUser Integer userId, @RequestBody String body){
        String info  = "提交用户订单";
        logger.info(getClass()+"----------------------------"+info);
        return commonOrderService.submitIntegral(userId, body);
    }

}