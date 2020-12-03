package com.eye.common.service;

import com.eye.common.task.MemberOrderUnpaidTask;
import com.eye.core.task.TaskService;
import com.eye.core.utils.DateTimeUtil;
import com.eye.core.utils.IpUtil;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.*;
import com.eye.db.service.*;
import com.eye.db.util.MemberConstant;
import com.eye.db.util.MemberOrderUtil;
import com.eye.db.util.OrderHandleOption;
import com.eye.sms.notify.SmsNotifyService;
import com.eye.sms.notify.SmsNotifyType;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eye.common.util.CommonResponseCode.*;

@Service
public class CommonMemberOrderService {
    private final Log logger = LogFactory.getLog(CommonMemberOrderService.class);

    @Autowired
    private EyeMemberOrderService memberOrderService;
    @Autowired
    private EyeMemberPackageService packageService;
    @Autowired
    private EyeIntegralService integralService;
    @Autowired
    private EyeMemberUserService memberUserService;

    @Autowired
    private EyeUserService userService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SmsNotifyService smsNotifyService;

    //提交订单
    @Transactional
    public Object submit(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }

        Integer memberPackageId = JacksonUtil.parseInteger(body, "memberPackageId");

        if (memberPackageId == null){
            return ResponseUtil.badArgument();
        }

        EyeMemberPackage memberPackage = packageService.findById(memberPackageId);

        Integer packageId = memberPackage.getId();
        BigDecimal price = memberPackage.getPrice();

        EyeUser litemallUser = userService.findById(userId);
        EyeMemberOrder memberOrder = new EyeMemberOrder();
        memberOrder.setUserId(userId);
        memberOrder.setPackageId(packageId);
        memberOrder.setMobile(litemallUser.getMobile());
        memberOrder.setPrice(price);
        memberOrder.setStatus(MemberOrderUtil.STATUS_CREATE);
        memberOrder.setOrderSn(memberOrderService.generateOrderSn(userId));
        memberOrder.setConsignee(litemallUser.getNickname());

        memberOrderService.add(memberOrder);

        taskService.addTask(new MemberOrderUnpaidTask(memberOrder.getId()));

        return memberOrder.getId();
    }

    @Transactional
    public Object prepay(Integer userId, String body, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer memberOrderId = JacksonUtil.parseInteger(body, "memberOrderId");
        if (memberOrderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeMemberOrder memberOrder = memberOrderService.findById(userId,memberOrderId);

        if (memberOrder == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!memberOrder.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测是否能够取消
        OrderHandleOption handleOption = MemberOrderUtil.build(memberOrder);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }

        EyeUser user = userService.findById(userId);
        String openid = user.getWeixinOpenid();
        if (openid == null) {
            return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "订单不能支付");
        }

        WxPayMpOrderResult result = null;


        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(memberOrder.getOrderSn());
            orderRequest.setOpenid(openid);
            orderRequest.setBody("订单：" + memberOrder.getOrderSn());
            // 元转成分
            int fee = 0;
            BigDecimal actualPrice = memberOrder.getPrice();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            orderRequest.setNotifyUrl("https://wwwapidev.6eye9.com/common/member/order/pay-notify");
            orderRequest.setTradeType("JSAPI");

            result = wxPayService.createOrder(orderRequest);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(ORDER_PAY_FAIL, "订单不能支付");
        }

        if (memberOrderService.updateWithOptimisticLocker(memberOrder) == 0) {
            return ResponseUtil.updatedDateExpired();
        }
        System.out.println(result);
        return ResponseUtil.ok(result);
    }

    public Object list(Integer userId, Integer showType, Integer page, Integer limit, String sort, String order) {
        if (userId == null){
            return ResponseUtil.unlogin();
        }

        List<Short> shorts = MemberOrderUtil.orderStatus(showType);
        List<EyeMemberOrder> memberOrderList = memberOrderService.queryByOrderStatus(userId, shorts, page, limit, sort, order);

        List<Map<String, Object>> orderVoList = new ArrayList<>(memberOrderList.size());
        for (EyeMemberOrder memberOrder : memberOrderList) {
            EyeMemberPackage memberPackage = packageService.findById(memberOrder.getPackageId());
            Map<String, Object> orderVo = new HashMap<>();
            orderVo.put("id", memberOrder.getId());
            orderVo.put("name",memberPackage.getName());
            orderVo.put("months",memberPackage.getMonths());
            orderVo.put("orderSn", memberOrder.getOrderSn());
            orderVo.put("price", memberOrder.getPrice());
            orderVo.put("orderStatusText", MemberOrderUtil.orderStatusText(memberOrder));
            /*orderVo.put("handleOption", MemberOrderUtil.build(memberOrder));*/

            orderVoList.add(orderVo);
        }

        return ResponseUtil.okList(orderVoList,memberOrderList);
    }

    public Object detail(Integer userId, Integer memberOrderId) {
        if (userId == null){
            return ResponseUtil.unlogin();
        }
        if (memberOrderId == null){
            return ResponseUtil.badArgument();
        }

        EyeMemberOrder memberOrder = memberOrderService.findById(userId, memberOrderId);
        if (null == memberOrder) {
            return ResponseUtil.fail(ORDER_UNKNOWN, "订单不存在");
        }
        if (!memberOrder.getUserId().equals(userId)) {
            return ResponseUtil.fail(ORDER_INVALID, "不是当前用户的订单");
        }

        EyeMemberPackage memberPackage = packageService.findById(memberOrder.getPackageId());

        Map<String, Object> orderVo = new HashMap<String, Object>();
        orderVo.put("id", memberOrder.getId());
        orderVo.put("orderSn", memberOrder.getOrderSn());
        orderVo.put("addTime", memberOrder.getAddTime());
        orderVo.put("consignee", memberOrder.getConsignee());
        orderVo.put("mobile", memberOrder.getMobile());
        orderVo.put("price", memberOrder.getPrice());
        orderVo.put("orderStatusText", MemberOrderUtil.orderStatusText(memberOrder));
        orderVo.put("memberPackage",memberPackage);

        return ResponseUtil.ok(orderVo);
    }

    @Transactional
    public Object cancel(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer memberOrderId = JacksonUtil.parseInteger(body, "memberOrderId");
        if (memberOrderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeMemberOrder memberOrder = memberOrderService.findById(memberOrderId);

        if (memberOrder == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!memberOrder.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        LocalDateTime preUpdateTime = memberOrder.getUpdateTime();

        // 检测是否能够取消
        OrderHandleOption handleOption = MemberOrderUtil.build(memberOrder);
        if (!handleOption.isCancel()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能取消");
        }

        // 设置订单已取消状态
        memberOrder.setStatus(MemberOrderUtil.STATUS_CANCEL);
        memberOrder.setEndTime(LocalDateTime.now());
        if (memberOrderService.updateWithOptimisticLocker(memberOrder) == 0) {
            throw new RuntimeException("更新数据已失效");
        }

        return ResponseUtil.ok();
    }

    //用户删除订单
    public Object delete(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer memberOrderId = JacksonUtil.parseInteger(body, "memberOrderId");
        if (memberOrderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeMemberOrder memberOrder = memberOrderService.findById(memberOrderId);

        if (memberOrder == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!memberOrder.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        memberOrderService.deleteById(memberOrderId);

        return ResponseUtil.ok();
    }

    @Transactional
    public Object apppay(Integer userId, String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Integer memberOrderId = JacksonUtil.parseInteger(body, "memberOrderId");
        if (memberOrderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeMemberOrder order = memberOrderService.findById(userId, memberOrderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }
        // 检测是否能够取消
        OrderHandleOption handleOption = MemberOrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }
        EyeUser user = userService.findById(userId);
//        String openid = user.getWeixinOpenid();
//        if (StringUtils.isEmpty(openid)) {
//            return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "订单不能支付");
//        }
        WxPayAppOrderResult result = null;
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(order.getOrderSn());
            orderRequest.setBody("浪博科技-订单号："+order.getOrderSn());
            // 元转成分
            int fee = 0;
            BigDecimal actualPrice = order.getPrice();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            orderRequest.setNotifyUrl("https://wwwapidev.6eye9.com/common/member/order/pay-notify");
            orderRequest.setTradeType("APP");
            orderRequest.setAppid("wxd2c3ff0d5a17f689");
            result = wxPayService.createOrder(orderRequest);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseUtil.ok(result);
    }

    @Transactional
    public Object h5pay(Integer userId, String body, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer memberOrderId = JacksonUtil.parseInteger(body, "memberOrderId");
        if (memberOrderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeMemberOrder order = memberOrderService.findById(userId, memberOrderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测是否能够取消
        OrderHandleOption handleOption = MemberOrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }

        WxPayMwebOrderResult result = null;
        Map<String,Object> map = new HashMap<>();
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(order.getOrderSn());
            orderRequest.setTradeType("MWEB");
            orderRequest.setBody("订单：" + order.getOrderSn());
            // 元转成分
            int fee = 0;
            BigDecimal actualPrice = order.getPrice();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            orderRequest.setNotifyUrl("https://indexapi.ilovelearning.cn/common/member/order/pay-notify");
//            orderRequest.setTradeType("JSAPI");
            result = wxPayService.createOrder(orderRequest);
            Map<String,String> extraHeaders = new HashMap<>();
            extraHeaders.put("Referer","https://www.ilovelearning.cn");
            map.put("extraHeaders",extraHeaders);
            map.put("result",result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.ok(map);
    }

    /**
     * 微信付款成功或失败回调接口
     * <p>
     * 1. 检测当前订单是否是付款状态;
     * 2. 设置订单付款成功状态相关信息;
     * 3. 响应微信商户平台.
     *
     * @param request  请求内容
     * @param response 响应内容
     * @return 操作结果
     */
    @Transactional
    public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
        String xmlResult = null;
        try {
            xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        } catch (IOException e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail(e.getMessage());
        }

        WxPayOrderNotifyResult result = null;
        try {
            result = wxPayService.parseOrderNotifyResult(xmlResult);

            if (!WxPayConstants.ResultCode.SUCCESS.equals(result.getResultCode())) {
                logger.error(xmlResult);
                throw new WxPayException("微信通知支付失败！");
            }
            if (!WxPayConstants.ResultCode.SUCCESS.equals(result.getReturnCode())) {
                logger.error(xmlResult);
                throw new WxPayException("微信通知支付失败！");
            }
        } catch (WxPayException e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail(e.getMessage());
        }

        logger.info("处理腾讯支付平台的订单支付");
        logger.info(result);

        String orderSn = result.getOutTradeNo();
        String payId = result.getTransactionId();

        // 分转化成元
        String totalFee = BaseWxPayResult.fenToYuan(result.getTotalFee());
        EyeMemberOrder memberOrder = memberOrderService.findBySn(orderSn);
        if (memberOrder != null){
            // 检查这个订单是否已经处理过
            if (MemberOrderUtil.hasPayed(memberOrder)) {
                return WxPayNotifyResponse.success("订单已经处理成功!");
            }

            // 检查支付订单金额
            if (!totalFee.equals(memberOrder.getPrice().toString())) {
                return WxPayNotifyResponse.fail(memberOrder.getOrderSn() + " : 支付金额不符合 totalFee=" + totalFee);
            }

            memberOrder.setPayId(payId);
            memberOrder.setPayTime(LocalDateTime.now());
            memberOrder.setStatus(MemberOrderUtil.STATUS_PAY);
            if (memberOrderService.updateWithOptimisticLocker(memberOrder) == 0) {
                return WxPayNotifyResponse.fail("更新数据已失效");
            }

            Integer userId = memberOrder.getUserId();
            EyeUser litemallUser = userService.findById(userId);
            Byte userLevel = litemallUser.getUserLevel();

            if (userLevel == MemberConstant.USER_LEVEL_ON){
                //更新用户会员状态
                if (userService.updatelevel(userId) == 0){
                    return WxPayNotifyResponse.fail("更新数据已失效");
                }
            }

            //订单支付成功之后将用户添加到积分表中
            EyeIntegral uloveIntegral = new EyeIntegral();
            EyeIntegral integral = integralService.findByUser(userId);
            if (integral == null){
                uloveIntegral.setUserId(userId);
                integralService.create(uloveIntegral);
            }else {
                uloveIntegral.setId(integral.getId());
                uloveIntegral.setStatus(MemberConstant.INTEGRAL_STATUS_ON);
                integralService.update(uloveIntegral);
            }

            //更新会员套餐时间
            //获取会员套餐的详细信息,用其时间来添加用户的会员时间
            EyeMemberPackage memberPackage = packageService.findById(memberOrder.getPackageId());

            EyeMemberUser memberUser = memberUserService.findByUserID(userId);

            //判断用户之前是否购买过套餐
            if (memberUser == null){
                EyeMemberUser litemallMemberUser = new EyeMemberUser();
                Integer months = memberPackage.getMonths();

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime end = now.plusMonths(months);


                litemallMemberUser.setUserId(userId);
                litemallMemberUser.setStartTime(now);
                litemallMemberUser.setEndTime(end);
                litemallMemberUser.setAddTime(now);
                litemallMemberUser.setUpdateTime(now);
                memberUserService.add(litemallMemberUser);
            }else {
                Integer months = memberPackage.getMonths();
                LocalDateTime endTime = memberUser.getEndTime();
                LocalDateTime end = endTime.plusMonths(months);
                LocalDateTime now = LocalDateTime.now();

                memberUser.setEndTime(end);
                memberUser.setUpdateTime(now);
                memberUserService.update(memberUser);
            }


            //TODO 发送邮件和短信通知，这里采用异步发送
            // 订单支付成功以后，会发送短信给用户，以及发送邮件给管理员
            //notifyService.notifyMail("新订单通知", memberOrder.toString());
            // 这里微信的短信平台对参数长度有限制，所以将订单号只截取后6位
            //smsNotifyService.notifySmsTemplateSync(memberOrder.getMobile(), SmsNotifyType.PAY_SUCCEED, new String[]{orderSn.substring(8, 14)});

            // 请依据自己的模版消息配置更改参数
            String[] parms = new String[]{
                    memberOrder.getOrderSn(),
                    memberOrder.getPrice().toString(),
                    DateTimeUtil.getDateTimeDisplayString(memberOrder.getAddTime()),
                    memberOrder.getConsignee(),
                    memberOrder.getMobile()
            };
            // 取消订单超时未支付任务
            taskService.removeTask(new MemberOrderUnpaidTask(memberOrder.getId()));
            return WxPayNotifyResponse.success("处理成功!");

        }else {
            return WxPayNotifyResponse.fail("订单不存在 sn=" + orderSn);
        }

    }

}
