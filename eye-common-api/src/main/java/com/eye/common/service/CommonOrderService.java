package com.eye.common.service;

import com.eye.common.dto.EyeOrderGoodsVo;
import com.eye.common.task.OrderUnpaidTask;
import com.eye.core.qcode.QCodeService;
import com.eye.core.task.TaskService;
import com.eye.core.utils.DateTimeUtil;
import com.eye.core.utils.IpUtil;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.*;
import com.eye.db.service.*;
import com.eye.db.util.*;
import com.eye.express.ExpressService;
import com.eye.express.dao.ExpressInfo;
import com.eye.mail.notify.MailNotifyService;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

/**
 * 订单服务
 *
 * <p>
 * 订单状态：
 * 101 订单生成，未支付；102，下单后未支付用户取消；103，下单后未支付超时系统自动取消
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，但是退款取消；
 * 301 商家发货，用户未确认；
 * 401 用户确认收货； 402 用户没有确认收货超过一定时间，系统自动确认收货；
 *
 * <p>
 * 用户操作：
 * 当101用户未付款时，此时用户可以进行的操作是取消订单，或者付款操作
 * 当201支付完成而商家未发货时，此时用户可以取消订单并申请退款
 * 当301商家已发货时，此时用户可以有确认收货的操作
 * 当401用户确认收货以后，此时用户可以进行的操作是删除订单，评价商品，申请售后，或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除订单，评价商品，申请售后，或者再次购买
 */
@Service
public class CommonOrderService {
    private final Log logger = LogFactory.getLog(CommonOrderService.class);

    @Autowired
    private EyeUserService userService;
    @Autowired
    private EyeOrderService orderService;
    @Autowired
    private EyeOrderGoodsService orderGoodsService;
    @Autowired
    private EyeAddressService addressService;
    @Autowired
    private EyeCartService cartService;
    @Autowired
    private EyeAccessoryService uloveAccessoryService;
    @Autowired
    private EyeGoodsProductService productService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private SmsNotifyService smsNotifyService;
    @Autowired
    private MailNotifyService mailNotifyService;
    @Autowired
    private EyeGrouponRulesService grouponRulesService;
    @Autowired
    private EyeGrouponService grouponService;
    @Autowired
    private QCodeService qCodeService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private EyeCommentService commentService;
    @Autowired
    private EyeCouponUserService couponUserService;
    @Autowired
    private CouponVerifyService couponVerifyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private EyeAftersaleService aftersaleService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeGoodsProductService goodsProductService;
    @Autowired
    private EyeIntegralService integralService;
    @Autowired
    private EyeIntegralGoodsService integralGoodsService;
    @Autowired
    private EyeMemberGoodsService memberGoodsService;
    @Autowired
    private EyeUserService uloveUserService;
    @Autowired
    private EyeGrouponCartService grouponCartService;

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息：
     *                 0，全部订单；
     *                 1，待付款；
     *                 2，待发货；
     *                 3，待收货；
     *                 4，待评价。
     * @param page     分页页数
     * @param limit     分页大小
     * @return 订单列表
     */
    public Object list(Integer userId, Integer showType, Integer page, Integer limit, String sort, String order) {

        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<Short> orderStatus = OrderUtil.orderStatus(showType);
        List<EyeOrder> orderList = orderService.queryByOrderStatus(userId, orderStatus, page, limit, sort, order);

        List<Map<String, Object>> orderVoList = new ArrayList<>(orderList.size());
        for (EyeOrder o : orderList) {
            Map<String, Object> orderVo = new HashMap<>();
            orderVo.put("id", o.getId());
            orderVo.put("orderSn", o.getOrderSn());
            orderVo.put("actualPrice", o.getActualPrice());
            orderVo.put("orderStatusText", OrderUtil.orderStatusText(o));
            orderVo.put("handleOption", OrderUtil.build(o));
            orderVo.put("aftersaleStatus", o.getAftersaleStatus());

            EyeGroupon groupon = grouponService.queryByOrderId(o.getId());
            if (groupon != null) {
                orderVo.put("isGroupin", true);
            } else {
                orderVo.put("isGroupin", false);
            }

            List<EyeOrderGoods> orderGoodsList = orderGoodsService.queryByOid(o.getId());
            List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
            for (EyeOrderGoods orderGoods : orderGoodsList) {
                Map<String, Object> orderGoodsVo = new HashMap<>();
                orderGoodsVo.put("id", orderGoods.getId());
                orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
                orderGoodsVo.put("number", orderGoods.getNumber());
                orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
                orderGoodsVo.put("specifications", orderGoods.getSpecifications());
                orderGoodsVo.put("price",orderGoods.getPrice());
                orderGoodsVoList.add(orderGoodsVo);
            }
            orderVo.put("goodsList", orderGoodsVoList);

            orderVoList.add(orderVo);
        }

        return ResponseUtil.okList(orderVoList, orderList);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    public Object detail(Integer userId, Integer orderId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 订单信息
        EyeOrder order = orderService.findById(userId, orderId);
        if (null == order) {
            return ResponseUtil.fail(ORDER_UNKNOWN, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.fail(ORDER_INVALID, "不是当前用户的订单");
        }
        Map<String, Object> orderVo = new HashMap<String, Object>();
        orderVo.put("id", order.getId());
        orderVo.put("orderSn", order.getOrderSn());
        orderVo.put("message", order.getMessage());
        orderVo.put("addTime", order.getAddTime());
        orderVo.put("consignee", order.getConsignee());
        orderVo.put("mobile", order.getMobile());
        orderVo.put("address", order.getAddress());
        orderVo.put("goodsPrice", order.getGoodsPrice());
        orderVo.put("couponPrice", order.getCouponPrice());
        orderVo.put("freightPrice", order.getFreightPrice());
        orderVo.put("actualPrice", order.getActualPrice());
        orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
        orderVo.put("handleOption", OrderUtil.build(order));
        orderVo.put("aftersaleStatus", order.getAftersaleStatus());
        orderVo.put("expCode", order.getShipChannel());
        orderVo.put("expName", expressService.getVendorName(order.getShipChannel()));
        orderVo.put("expNo", order.getShipSn());


        List<EyeOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
        List<EyeOrderGoodsVo> orderGoodsVos = new ArrayList<>();
        if(order.getOrderStatus().equals(OrderUtil.STATUS_PAY) || order.getOrderStatus().equals(OrderUtil.STATUS_RETUND_REFUSE)){
            for(EyeOrderGoods orderGoods :orderGoodsList){
                EyeOrderGoodsVo orderGoodsVo = new EyeOrderGoodsVo();
                orderGoodsVo.setId(orderGoods.getId());
                orderGoodsVo.setOrderId(orderGoods.getOrderId());
                orderGoodsVo.setPicUrl(orderGoods.getPicUrl());
                orderGoodsVo.setComment(orderGoods.getComment());
                orderGoodsVo.setGoodsId(orderGoods.getGoodsId());
                orderGoodsVo.setGoodsSn(orderGoods.getGoodsSn());
                orderGoodsVo.setPrice(orderGoods.getPrice());
                orderGoodsVo.setGoodsName(orderGoods.getGoodsName());
                orderGoodsVo.setProductId(orderGoods.getProductId());
                orderGoodsVo.setAddTime(orderGoods.getAddTime());
                orderGoodsVo.setNumber(orderGoods.getNumber());
                orderGoodsVo.setSpecifications(orderGoods.getSpecifications());
                orderGoodsVo.setUpdateTime(orderGoods.getUpdateTime());
                orderGoodsVo.setDeleted(orderGoods.getDeleted());
                EyeAccessory accessory = uloveAccessoryService.queryByGid(orderGoods.getGoodsId(),0);
                orderGoodsVo.setEyeAccessory(accessory);
                orderGoodsVos.add(orderGoodsVo);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orderInfo", orderVo);
        result.put("orderGoods", order.getOrderStatus().equals(OrderUtil.STATUS_PAY) || order.getOrderStatus().equals(OrderUtil.STATUS_RETUND_REFUSE) ? orderGoodsVos:orderGoodsList);

        // 订单状态为已发货且物流信息不为空
        //"YTO", "800669400640887922"
        if (order.getOrderStatus().equals(OrderUtil.STATUS_SHIP)) {
            ExpressInfo ei = expressService.getExpressInfo(order.getShipChannel(), order.getShipSn());
            if(ei == null){
                result.put("expressInfo", new ArrayList<>());
            }
            else {
                result.put("expressInfo", ei);
            }
        }
        else{
            result.put("expressInfo", new ArrayList<>());
        }

        return ResponseUtil.ok(result);

    }

    /**
     * 提交订单
     * 现在用户积分是一次性用完
     *
     * @param userId 用户ID
     * @param body   订单信息，{ cartId：xxx, addressId: xxx, storeName:xxx, storeAddress:xxx, couponId: xxx, message: xxx, grouponRulesId: xxx,  grouponLinkId: xxx, integralId: xxx, memberGoodsId: xxx}
     * @return 提交订单操作结果
     */

    @Transactional
    public Object submit(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        Integer cartId = JacksonUtil.parseInteger(body, "cartId");
        Integer addressId = JacksonUtil.parseInteger(body, "addressId");
        Integer couponId = JacksonUtil.parseInteger(body, "couponId");
        Integer userCouponId = JacksonUtil.parseInteger(body, "userCouponId");
        String message = JacksonUtil.parseString(body, "message");
        Integer grouponRulesId = JacksonUtil.parseInteger(body, "grouponRulesId");
        Integer grouponLinkId = JacksonUtil.parseInteger(body, "grouponLinkId");
        Integer integralId = JacksonUtil.parseInteger(body, "integralId");
        Integer memberGoodsId = JacksonUtil.parseInteger(body, "memberGoodsId");

        if (memberGoodsId != null && memberGoodsId > 0) {
            EyeMemberGoods memberGoods = memberGoodsService.findById(memberGoodsId);
            if (memberGoods == null) {
                return ResponseUtil.badArgument();
            }

            if (memberGoods.getStatus().equals(MemberConstant.GOODS_STATUS_DOWN_EXPIRE)) {
                return ResponseUtil.fail(MemberConstant.MEMBER_GOODS_EXPIRED, "会员商品已过期");
            }

            if (memberGoods.getStatus().equals(MemberConstant.GOODS_STATUS_DOWN_ADMIN)) {
                return ResponseUtil.fail(MemberConstant.MEMBER_GOODS_OFFLINE, "会员商品已下线");
            }
        }

        //如果是团购项目,验证活动是否有效
        if (grouponRulesId != null && grouponRulesId > 0) {
            EyeGrouponRules rules = grouponRulesService.findById(grouponRulesId);
            //找不到记录
            if (rules == null) {
                return ResponseUtil.badArgument();
            }
            //团购规则已经过期
            if (rules.getStatus().equals(GrouponConstant.RULE_STATUS_DOWN_EXPIRE)) {
                return ResponseUtil.fail(GROUPON_EXPIRED, "团购已过期!");
            }
            //团购规则已经下线
            if (rules.getStatus().equals(GrouponConstant.RULE_STATUS_DOWN_ADMIN)) {
                return ResponseUtil.fail(GROUPON_OFFLINE, "团购已下线!");
            }

            if (grouponLinkId != null && grouponLinkId > 0) {
                //团购人数已满
                if (grouponService.countGroupon(grouponLinkId) >= (rules.getDiscountMember() - 1)) {
                    return ResponseUtil.fail(GROUPON_FULL, "团购活动人数已满!");
                }
                // NOTE
                // 这里业务方面允许用户多次开团，以及多次参团，
                // 但是会限制以下两点：
                // （1）不允许参加已经加入的团购
                if (grouponService.hasJoin(userId, grouponLinkId)) {
                    return ResponseUtil.fail(GROUPON_JOIN, "团购活动已经参加!");
                }
                // （2）不允许参加自己开团的团购
                EyeGroupon groupon = grouponService.queryById(userId, grouponLinkId);
                if(groupon!=null) {
                    if(groupon.getCreatorUserId().equals(userId)){
                        return ResponseUtil.fail(GROUPON_JOIN, "团购活动已经参加!");
                    }
                }
            }
        }

        if (cartId == null /*|| addressId == null*/ || couponId == null) {
            return ResponseUtil.badArgument();
        }

        // 收货地址
        EyeAddress checkedAddress = addressService.query(userId, addressId);
//        if (checkedAddress == null) {
//            return ResponseUtil.badArgument();
//        }

        // 团购优惠
        BigDecimal grouponPrice = new BigDecimal(0);
        EyeGrouponRules grouponRules = grouponRulesService.findById(grouponRulesId);
        if (grouponRules != null) {
            grouponPrice = grouponRules.getDiscount();
        }

        BigDecimal memberPirce = new BigDecimal(0);
        EyeMemberGoods uloveMemberGoods = memberGoodsService.findById(memberGoodsId);
        if (uloveMemberGoods != null) {
            memberPirce = uloveMemberGoods.getRetailPrice().subtract(uloveMemberGoods.getDiscountPrice());
        }

        // 货品价格
        List<EyeGrouponCart> grouponCheckedGoodsList = null;
        List<EyeCart> checkedGoodsList = null;
        if (cartId.equals(0)) {
            if(grouponRulesId != null && grouponRulesId > 0){
                grouponCheckedGoodsList = grouponCartService.queryByUidAndChecked(userId);
            }
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            if(grouponRulesId != null && grouponRulesId > 0){
                EyeGrouponCart cart =  grouponCartService.findById(cartId);
                grouponCheckedGoodsList = new ArrayList<>();
                grouponCheckedGoodsList.add(cart);
            }
            EyeCart cart = cartService.findById(cartId);
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
        if(grouponRulesId != null && grouponRulesId > 0 && grouponCheckedGoodsList.size() == 0){
            return ResponseUtil.badArgumentValue();
        }
        if (checkedGoodsList.size() == 0) {
            return ResponseUtil.badArgumentValue();
        }
        BigDecimal checkedGoodsPrice = new BigDecimal(0);
        if(grouponRulesId != null && grouponRulesId > 0){
            for(EyeGrouponCart checkGoods : grouponCheckedGoodsList){
                //  只有当团购规格商品ID符合才进行团购优惠
                if (grouponRules != null && grouponRules.getGoodsId().equals(checkGoods.getGoodsId())) {
                    checkedGoodsPrice = checkedGoodsPrice.add(checkGoods.getPrice().subtract(grouponPrice).multiply(new BigDecimal(checkGoods.getNumber())));
                } else {
                    checkedGoodsPrice = checkedGoodsPrice.add(checkGoods.getPrice().multiply(new BigDecimal(checkGoods.getNumber())));
                }
                if (uloveMemberGoods != null && uloveMemberGoods.getGoodsId().equals(checkGoods.getGoodsId())) {
                    checkedGoodsPrice = checkedGoodsPrice.subtract(memberPirce.multiply(new BigDecimal(checkGoods.getNumber())));
                }
            }
        }else{
            for (EyeCart checkGoods : checkedGoodsList) {
                //  只有当团购规格商品ID符合才进行团购优惠
                if (grouponRules != null && grouponRules.getGoodsId().equals(checkGoods.getGoodsId())) {
                    checkedGoodsPrice = checkedGoodsPrice.add(checkGoods.getPrice().subtract(grouponPrice).multiply(new BigDecimal(checkGoods.getNumber())));
                } else {
                    checkedGoodsPrice = checkedGoodsPrice.add(checkGoods.getPrice().multiply(new BigDecimal(checkGoods.getNumber())));
                }
                if (uloveMemberGoods != null && uloveMemberGoods.getGoodsId().equals(checkGoods.getGoodsId())) {
                    checkedGoodsPrice = checkedGoodsPrice.subtract(memberPirce.multiply(new BigDecimal(checkGoods.getNumber())));
                }
            }
        }


        // 获取可用的优惠券信息
        // 使用优惠券减免的金额
        BigDecimal couponPrice = new BigDecimal(0);
        // 如果couponId=0则没有优惠券，couponId=-1则不使用优惠券
        if (couponId != 0 && couponId != -1) {
            EyeCoupon coupon = couponVerifyService.checkCoupon(userId, couponId, userCouponId, checkedGoodsPrice, checkedGoodsList);
            if (coupon == null) {
                return ResponseUtil.badArgumentValue();
            }
            couponPrice = coupon.getDiscount();
        }

        //根据附件表判断是否需要运费
        /*List<EyeAccessory> accessoryList = new ArrayList<>();

        for (EyeCart cart : checkedGoodsList) {
            EyeAccessory accessory = accessoryService.findByGoodsId(cart.getGoodsId());
            accessoryList.add(accessory);
        }*/

        // 根据订单商品总价计算运费，满足条件（例如88元）则免运费，否则需要支付运费（例如8元）；
        BigDecimal freightPrice = new BigDecimal(0);
        //根据附件表判断是否需要运费,现都为虚拟商品都不需要运费
       /* if (accessoryList.size() != checkedGoodsList.size()){
            if (checkedGoodsPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                freightPrice = SystemConfig.getFreight();
            }
        }*/

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);
        if (integralId != null && integralId != 0) {
            EyeIntegral uloveIntegral = integralService.findByUserId(userId);
            if (uloveIntegral == null) {
                return ResponseUtil.badArgumentValue();
            }
            Integer integration = uloveIntegral.getIntegration();
            integralPrice = BigDecimal.valueOf(integration).divide(MemberConstant.INTEGRAL_PERCENT);
            integralPrice = integralPrice.setScale(2,BigDecimal.ROUND_DOWN);
        }

        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0));
        BigDecimal subtract = orderTotalPrice.subtract(integralPrice);
        if (subtract.compareTo(BigDecimal.valueOf(0))==-1 || subtract.compareTo(BigDecimal.valueOf(0))==0){
            BigDecimal decimal = BigDecimal.valueOf(0.01);
            subtract=decimal;
            integralPrice= integralPrice.subtract(decimal);
        }
        // 最终支付费用
        BigDecimal actualPrice = subtract;

        Integer orderId = null;
        EyeOrder order = null;
        // 订单
        order = new EyeOrder();
        order.setUserId(userId);
        order.setOrderSn(orderService.generateOrderSn(userId));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setConsignee(checkedAddress.getName());
        order.setMobile(checkedAddress.getTel());
        order.setMessage(message);
        String detailedAddress = checkedAddress.getProvince() + checkedAddress.getCity() + checkedAddress.getCounty() + " " + checkedAddress.getAddressDetail();
        order.setAddress(detailedAddress);
        order.setGoodsPrice(checkedGoodsPrice);
        order.setFreightPrice(freightPrice);
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);
        order.setActualPrice(actualPrice);

        // 有团购
        if (grouponRules != null) {
            order.setGrouponPrice(grouponPrice);    //  团购价格
        } else {
            order.setGrouponPrice(new BigDecimal(0));    //  团购价格
        }

        // 添加订单表项
        orderService.add(order);
        orderId = order.getId();

        // 添加订单商品表项
        if(grouponRulesId != null && grouponRulesId > 0){
            for (EyeGrouponCart cartGoods : grouponCheckedGoodsList) {
                // 订单商品
                EyeOrderGoods orderGoods = new EyeOrderGoods();
                orderGoods.setOrderId(order.getId());
                orderGoods.setGoodsId(cartGoods.getGoodsId());
                orderGoods.setGoodsSn(cartGoods.getGoodsSn());
                orderGoods.setProductId(cartGoods.getProductId());
                orderGoods.setGoodsName(cartGoods.getGoodsName());
                orderGoods.setPicUrl(cartGoods.getPicUrl());
                orderGoods.setPrice(cartGoods.getPrice());
                orderGoods.setNumber(cartGoods.getNumber());
                orderGoods.setSpecifications(cartGoods.getSpecifications());
                orderGoods.setAddTime(LocalDateTime.now());

                orderGoodsService.add(orderGoods);
            }
        }else{
            for (EyeCart cartGoods : checkedGoodsList) {
                // 订单商品
                EyeOrderGoods orderGoods = new EyeOrderGoods();
                orderGoods.setOrderId(order.getId());
                orderGoods.setGoodsId(cartGoods.getGoodsId());
                orderGoods.setGoodsSn(cartGoods.getGoodsSn());
                orderGoods.setProductId(cartGoods.getProductId());
                orderGoods.setGoodsName(cartGoods.getGoodsName());
                orderGoods.setPicUrl(cartGoods.getPicUrl());
                orderGoods.setPrice(cartGoods.getPrice());
                orderGoods.setNumber(cartGoods.getNumber());
                orderGoods.setSpecifications(cartGoods.getSpecifications());
                orderGoods.setAddTime(LocalDateTime.now());

                orderGoodsService.add(orderGoods);
            }
        }

        // 删除购物车里面的商品信息
        if(grouponRulesId != null && grouponRulesId > 0){
            if (cartId.equals(0)) {
                grouponCartService.clearGoods(userId);
            } else {
                grouponCartService.deleteById(cartId);
            }
        }else{
            if (cartId.equals(0)) {
                cartService.clearGoods(userId);
            } else {
                cartService.deleteById(cartId);
            }

        }

        // 商品货品数量减少
        if(grouponRulesId != null && grouponRulesId > 0){
            for (EyeGrouponCart checkGoods : grouponCheckedGoodsList) {
                Integer productId = checkGoods.getProductId();
                EyeGoodsProduct product = productService.findById(productId);

                int remainNumber = product.getNumber() - checkGoods.getNumber();
                if (remainNumber < 0) {
                    throw new RuntimeException("下单的商品货品数量大于库存量");
                }
                if (productService.reduceStock(productId, checkGoods.getNumber()) == 0) {
                    throw new RuntimeException("商品货品库存减少失败");
                }
            }
        }else{
            for (EyeCart checkGoods : checkedGoodsList) {
                Integer productId = checkGoods.getProductId();
                EyeGoodsProduct product = productService.findById(productId);

                int remainNumber = product.getNumber() - checkGoods.getNumber();
                if (remainNumber < 0) {
                    throw new RuntimeException("下单的商品货品数量大于库存量");
                }
                if (productService.reduceStock(productId, checkGoods.getNumber()) == 0) {
                    throw new RuntimeException("商品货品库存减少失败");
                }
            }
        }


        // 如果使用了优惠券，设置优惠券使用状态
        if (couponId != 0 && couponId != -1) {
            EyeCouponUser couponUser = couponUserService.findById(userCouponId);
            couponUser.setStatus(CouponUserConstant.STATUS_USED);
            couponUser.setUsedTime(LocalDateTime.now());
            couponUser.setOrderId(orderId);
            couponUserService.update(couponUser);
        }

        //如果使用了会员积分,则将会员积分减去
        if (integralId != null && integralId > 0) {
            EyeOrder byId = orderService.findById(orderId);

            BigDecimal integarl = byId.getIntegralPrice().multiply(MemberConstant.INTEGRAL_PERCENT);
            EyeIntegral uloveIntegral = integralService.findById(integralId);

            uloveIntegral.setIntegration(uloveIntegral.getIntegration() - integarl.intValue());
            integralService.update(uloveIntegral);
        }

        //如果是团购项目，添加团购信息
        if (grouponRulesId != null && grouponRulesId > 0) {
            EyeGroupon groupon = new EyeGroupon();
            groupon.setOrderId(orderId);
            groupon.setStatus(GrouponConstant.STATUS_NONE);
            groupon.setUserId(userId);
            groupon.setRulesId(grouponRulesId);

            //参与者
            if (grouponLinkId != null && grouponLinkId > 0) {
                //参与的团购记录
                EyeGroupon baseGroupon = grouponService.queryById(grouponLinkId);
                groupon.setCreatorUserId(baseGroupon.getCreatorUserId());
                groupon.setGrouponId(grouponLinkId);
                groupon.setShareUrl(baseGroupon.getShareUrl());
                grouponService.createGroupon(groupon);
            } else {
                groupon.setCreatorUserId(userId);
                groupon.setCreatorUserTime(LocalDateTime.now());
                groupon.setGrouponId(0);
                grouponService.createGroupon(groupon);
                grouponLinkId = groupon.getId();
            }
        }

        // 订单支付超期任务
        taskService.addTask(new OrderUnpaidTask(orderId));

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);
        if (grouponRulesId != null && grouponRulesId > 0) {
            data.put("grouponLinkId", grouponLinkId);
        } else {
            data.put("grouponLinkId", 0);
        }
        return ResponseUtil.ok(data);
    }

    /**
     * 取消订单
     * <p>
     * 1. 检测当前订单是否能够取消；
     * 2. 设置订单取消状态；
     * 3. 商品货品库存恢复；
     * 4. 返还优惠券；
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 取消订单操作结果
     */
    @Transactional
    public Object cancel(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        LocalDateTime preUpdateTime = order.getUpdateTime();

        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isCancel()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能取消");
        }

        // 设置订单已取消状态
        order.setOrderStatus(OrderUtil.STATUS_CANCEL);
        order.setEndTime(LocalDateTime.now());
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            throw new RuntimeException("更新数据已失效");
        }

        // 商品货品数量增加
        List<EyeOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
        for (EyeOrderGoods orderGoods : orderGoodsList) {
            Integer productId = orderGoods.getProductId();
            Short number = orderGoods.getNumber();
            if (productService.addStock(productId, number) == 0) {
                throw new RuntimeException("商品货品库存增加失败");
            }
        }

        // 返还优惠券
        releaseCoupon(orderId);

        return ResponseUtil.ok();
    }

    /**
     * 付款订单的预支付会话标识
     * <p>
     * 1. 检测当前订单是否能够付款
     * 2. 微信商户平台返回支付订单ID
     * 3. 设置订单付款状态
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 支付订单ID
     */
    @Transactional
    public Object prepay(Integer userId, String body, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);
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
            orderRequest.setOutTradeNo(order.getOrderSn());
            orderRequest.setOpenid(openid);
            orderRequest.setBody("订单：" + order.getOrderSn());
            // 元转成分
            int fee = 0;
            BigDecimal actualPrice = order.getActualPrice();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            orderRequest.setNotifyUrl("https://wwwapidev.6eye9.com/common/order/pay-notify");
            orderRequest.setTradeType("JSAPI");
            result = wxPayService.createOrder(orderRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(ORDER_PAY_FAIL, "订单不能支付");
        }

        if (orderService.updateWithOptimisticLocker(order) == 0) {
            return ResponseUtil.updatedDateExpired();
        }
        return ResponseUtil.ok(result);
    }
    /**
     * APP支付预支付
     * @param userId
     * @param body
     * @param request
     * @return
     */
    @Transactional
    public Object apppay(Integer userId, String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }
        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }
        EyeUser user = userService.findById(userId);
        WxPayAppOrderResult result = null;
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(order.getOrderSn());
            orderRequest.setBody("浪博科技-订单号："+order.getOrderSn());
            // 元转成分
            int fee = 0;
            BigDecimal actualPrice = order.getActualPrice();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            orderRequest.setNotifyUrl("https://wwwapidev.6eye9.com/common/order/pay-notify");
            orderRequest.setTradeType("APP");
            orderRequest.setAppid("wxd2c3ff0d5a17f689");
            result = wxPayService.createOrder(orderRequest);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseUtil.ok(result);
    }
    /**
     * 微信H5支付
     *
     * @param userId
     * @param body
     * @param request
     * @return
     */
    @Transactional
    public Object h5pay(Integer userId, String body, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);
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
            BigDecimal actualPrice = order.getActualPrice();
            fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));
            orderRequest.setNotifyUrl("https://indexapi.ilovelearning.cn/common/order/pay-notify");
            result = wxPayService.createOrder(orderRequest);
            String mwebUrl = result.getMwebUrl();
            result.setMwebUrl(mwebUrl+"&redirect_url=https%3A%2F%2Findex.ilovelearning.cn%2F%23%2Fpages%2Forder%2Forder%3Ftype%3D0");
            System.out.println(result.getMwebUrl());
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

            if(!WxPayConstants.ResultCode.SUCCESS.equals(result.getResultCode())){
                logger.error(xmlResult);
                throw new WxPayException("微信通知支付失败！");
            }
            if(!WxPayConstants.ResultCode.SUCCESS.equals(result.getReturnCode())){
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
        EyeOrder order = orderService.findBySn(orderSn);
        if (order == null) {
            return WxPayNotifyResponse.fail("订单不存在 sn=" + orderSn);
        }

        // 检查这个订单是否已经处理过
        if (OrderUtil.hasPayed(order)) {
            return WxPayNotifyResponse.success("订单已经处理成功!");
        }

        // 检查支付订单金额
        if (!totalFee.equals(order.getActualPrice().toString())) {
            return WxPayNotifyResponse.fail(order.getOrderSn() + " : 支付金额不符合 totalFee=" + totalFee);
        }

        order.setPayId(payId);
        order.setPayTime(LocalDateTime.now());
        order.setOrderStatus(OrderUtil.STATUS_PAY);
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            return WxPayNotifyResponse.fail("更新数据已失效");
        }

        //  支付成功，有团购信息，更新团购信息
        EyeGroupon groupon = grouponService.queryByOrderId(order.getId());
        if (groupon != null) {
            EyeGrouponRules grouponRules = grouponRulesService.findById(groupon.getRulesId());

            //仅当发起者才创建分享图片
            if (groupon.getGrouponId() == 0) {
                String url = qCodeService.createGrouponShareImage(grouponRules.getGoodsName(), grouponRules.getPicUrl(), groupon);
                groupon.setShareUrl(url);
            }
            groupon.setStatus(GrouponConstant.STATUS_ON);
            if (grouponService.updateById(groupon) == 0) {
                return WxPayNotifyResponse.fail("更新数据已失效");
            }


            List<EyeGroupon> grouponList = grouponService.queryJoinRecord(groupon.getGrouponId());
            if (groupon.getGrouponId() != 0 && (grouponList.size() >= grouponRules.getDiscountMember() - 1)) {
                for (EyeGroupon grouponActivity : grouponList) {
                    grouponActivity.setStatus(GrouponConstant.STATUS_SUCCEED);
                    grouponService.updateById(grouponActivity);
                }

                EyeGroupon grouponSource = grouponService.queryById(groupon.getGrouponId());
                grouponSource.setStatus(GrouponConstant.STATUS_SUCCEED);
                grouponService.updateById(grouponSource);
            }
        }

        //TODO 发送邮件和短信通知，这里采用异步发送
        // 订单支付成功以后，会发送短信给用户，以及发送邮件给管理员
        //smsNotifyService.notifyMail("新订单通知", order.toString());
        // 这里微信的短信平台对参数长度有限制，所以将订单号只截取后6位
        smsNotifyService.notifySmsTemplateSync(order.getMobile(), SmsNotifyType.PAY_SUCCEED, new String[]{orderSn.substring(8, 14)});

        // 请依据自己的模版消息配置更改参数
        String[] parms = new String[]{
                order.getOrderSn(),
                order.getOrderPrice().toString(),
                DateTimeUtil.getDateTimeDisplayString(order.getAddTime()),
                order.getConsignee(),
                order.getMobile(),
                order.getAddress()
        };

        // 取消订单超时未支付任务
        taskService.removeTask(new OrderUnpaidTask(order.getId()));

        return WxPayNotifyResponse.success("处理成功!");
    }

    /**
     * 订单申请退款
     * <p>
     * 1. 检测当前订单是否能够退款；
     * 2. 设置订单申请退款状态。
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    public Object refund(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isRefund()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能退款");
        }

        // 设置订单申请退款状态
        order.setOrderStatus(OrderUtil.STATUS_REFUND);
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            return ResponseUtil.updatedDateExpired();
        }

        //TODO 发送邮件和短信通知，这里采用异步发送
        // 有用户申请退款，邮件通知运营人员
        mailNotifyService.notifyMail("退款申请", order.toString());

        return ResponseUtil.ok();
    }

    /**
     * 确认收货
     * <p>
     * 1. 检测当前订单是否能够确认收货；
     * 2. 设置订单确认收货状态。
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    public Object confirm(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isConfirm()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能确认收货");
        }

        Short comments = orderGoodsService.getComments(orderId);
        order.setComments(comments);

        order.setOrderStatus(OrderUtil.STATUS_CONFIRM);
        order.setConfirmTime(LocalDateTime.now());
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            return ResponseUtil.updatedDateExpired();
        }

        //确认收货之后增加用户积分
        EyeUser litemallUser = uloveUserService.find(userId);
        if (litemallUser.getUserLevel() == MemberConstant.USER_LEVEL_VIP || litemallUser.getUserLevel() == MemberConstant.USER_LEVEL_HIGH_VIP){

            //BigDecimal.ROUND_DOWN 舍弃整数后面的分数
            int actualPrice = order.getActualPrice().setScale(0, BigDecimal.ROUND_DOWN).intValue();
            integralService.updateIntegration(userId,actualPrice);
        }
        return ResponseUtil.ok();
    }

    /**
     * 删除订单
     * <p>
     * 1. 检测当前订单是否可以删除；
     * 2. 删除订单。
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @Transactional
    public Object delete(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isDelete()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能删除");
        }

        // 订单order_status没有字段用于标识删除
        // 而是存在专门的delete字段表示是否删除
        orderService.deleteById(orderId);
        // 售后也同时删除
        aftersaleService.deleteByOrderId(userId, orderId);

        return ResponseUtil.ok();
    }

    /**
     * 待评价订单商品信息
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @return 待评价订单商品信息
     */
    public Object goods(Integer userId, Integer orderId, Integer goodsId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }

        List<EyeOrderGoods> orderGoodsList = orderGoodsService.findByOidAndGid(orderId, goodsId);
        int size = orderGoodsList.size();

        Assert.state(size < 2, "存在多个符合条件的订单商品");

        if (size == 0) {
            return ResponseUtil.badArgumentValue();
        }

        EyeOrderGoods orderGoods = orderGoodsList.get(0);
        return ResponseUtil.ok(orderGoods);
    }

    /**
     * 评价订单商品
     * <p>
     * 确认商品收货或者系统自动确认商品收货后7天内可以评价，过期不能评价。
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    public Object comment(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Integer orderGoodsId = JacksonUtil.parseInteger(body, "orderGoodsId");
        if (orderGoodsId == null) {
            return ResponseUtil.badArgument();
        }
        EyeOrderGoods orderGoods = orderGoodsService.findById(orderGoodsId);
        if (orderGoods == null) {
            return ResponseUtil.badArgumentValue();
        }
        Integer orderId = orderGoods.getOrderId();
        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        Short orderStatus = order.getOrderStatus();
        if (!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "当前商品不能评价");
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.fail(ORDER_INVALID, "当前商品不属于用户");
        }
        Integer commentId = orderGoods.getComment();
        if (commentId == -1) {
            return ResponseUtil.fail(ORDER_COMMENT_EXPIRED, "当前商品评价时间已经过期");
        }
        if (commentId != 0) {
            return ResponseUtil.fail(ORDER_COMMENTED, "订单商品已评价");
        }

        String content = JacksonUtil.parseString(body, "content");
        Integer star = JacksonUtil.parseInteger(body, "star");
        if (star == null || star < 0 || star > 5) {
            return ResponseUtil.badArgumentValue();
        }
        Boolean hasPicture = JacksonUtil.parseBoolean(body, "hasPicture");
        List<String> picUrls = JacksonUtil.parseStringList(body, "picUrls");
        if (hasPicture == null || !hasPicture) {
            picUrls = new ArrayList<>(0);
        }

        // 1. 创建评价
        EyeComment comment = new EyeComment();
        comment.setUserId(userId);
        comment.setType((byte) 0);
        comment.setValueId(orderGoods.getGoodsId());
        comment.setStar(star.shortValue());
        comment.setContent(content);
        comment.setHasPicture(hasPicture);
        comment.setPicUrls(picUrls.toArray(new String[]{}));
        commentService.save(comment);

        // 2. 更新订单商品的评价列表
        orderGoods.setComment(comment.getId());
        orderGoodsService.updateById(orderGoods);

        // 3. 更新订单中未评价的订单商品可评价数量
        Short commentCount = order.getComments();
        if (commentCount > 0) {
            commentCount--;
        }
        order.setComments(commentCount);
        orderService.updateWithOptimisticLocker(order);

        return ResponseUtil.ok();
    }

    /**
     * 取消订单/退款返还优惠券
     * <br/>
     * @param orderId
     * @return void
     * @author Tyson
     * @date 2020/6/8/0008 1:41
     */
    public void releaseCoupon(Integer orderId) {
        List<EyeCouponUser> couponUsers = couponUserService.findByOid(orderId);
        for (EyeCouponUser couponUser: couponUsers) {
            // 优惠券状态设置为可使用
            couponUser.setStatus(CouponUserConstant.STATUS_USABLE);
            couponUser.setUpdateTime(LocalDateTime.now());
            couponUserService.update(couponUser);
        }
    }

    public Object showLink(Integer userId, Integer orderId) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        EyeOrder order = orderService.findById(userId, orderId);
        if(order == null ){
            return ResponseUtil.badArgument();
        }
        order.setOrderStatus(OrderUtil.STATUS_RETUND_REFUSE);
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        EyeUser eyeUser = userService.find(order.getUserId());
        //判断用户是否是会员
        if (eyeUser.getUserLevel() == MemberConstant.USER_LEVEL_VIP || eyeUser.getUserLevel() == MemberConstant.USER_LEVEL_HIGH_VIP){

            //BigDecimal.ROUND_DOWN 舍弃整数后面的分数
            int actualPrice = order.getActualPrice().setScale(0, BigDecimal.ROUND_DOWN).intValue();
            integralService.updateIntegration(order.getUserId(),actualPrice);
        }

        return ResponseUtil.ok();
    }

    // 取消订单/退款返还用户积分
    public void releaseIntegral(Integer orderId) {
        EyeOrder litemallOrder = orderService.findById(orderId);
        BigDecimal integralPrice = litemallOrder.getIntegralPrice();

        EyeIntegral byUserId = integralService.findByUserId(litemallOrder.getUserId());

        if (byUserId == null) {
            return;
        }

        EyeIntegral uloveIntegral = new EyeIntegral();
        uloveIntegral.setUserId(litemallOrder.getUserId());
        //返还用户积分
        uloveIntegral.setId(byUserId.getId());
        uloveIntegral.setIntegration(byUserId.getIntegration() + integralPrice.multiply(MemberConstant.INTEGRAL_PERCENT).intValue());
        integralService.update(uloveIntegral);
    }

    /**
     * 提交订单
     * 用户购买的是积分商城里的商品,不添加到购物车,直接添加到订单表和订单商品表中
     *
     * @param userId 用户ID
     * @param body   订单信息，{ addressId: xxx,  message: xxx, integralGoodsId: xxx, productId: xxx, number:xxx}
     * @return 提交订单操作结果
     */
    @Transactional
    public Object submitIntegral(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        Integer addressId = JacksonUtil.parseInteger(body, "addressId");
        String message = JacksonUtil.parseString(body, "message");
        Integer integralGoodsId = JacksonUtil.parseInteger(body, "integralGoodsId");
        Integer productId = JacksonUtil.parseInteger(body, "productId");
        Short number = JacksonUtil.parseShort(body, "number");

        if (integralGoodsId == null || addressId == null || productId == null || number == null) {
            return ResponseUtil.badArgument();
        }

        // 收货地址
        EyeAddress checkedAddress = addressService.query(userId, addressId);
        if (checkedAddress == null) {
            return ResponseUtil.badArgument();
        }

        // 团购优惠
        BigDecimal grouponPrice = new BigDecimal(0);

        // 使用优惠券减免的金额
        BigDecimal couponPrice = new BigDecimal(0);
        //
        BigDecimal goodsPrice = new BigDecimal(0);

        //积分商品
        EyeIntegralGoods integralGoods = integralGoodsService.findById(integralGoodsId);
        if (integralGoods == null) {
            return ResponseUtil.fail(MemberConstant.INTEGRAL_GOODS_OFFLINE, "积分商品不存在");
        }
        Integer integration = integralGoods.getIntegration();

        //判断用户积分是否充足
        EyeIntegral uloveIntegral = integralService.findByUserId(userId);
        Integer integer = uloveIntegral.getIntegration();
        if (integer < integration * number) {
            return ResponseUtil.fail(MemberConstant.INTEGRAL_OF_LACK, "您的积分不足");
        }

        BigDecimal integralPrice = BigDecimal.valueOf(integration * number).divide(MemberConstant.INTEGRAL_PERCENT);

        //邮费
        BigDecimal freightPrice = new BigDecimal(0);

        // 根据附件表判断是否需要运费
        /*EyeAccessory accessory = accessoryService.findByGoodsId(integralGoods.getGoodsId());
        if (accessory == null){
            freightPrice = SystemConfig.getFreight();
        }*/

        // 订单费用
        BigDecimal orderTotalPrice = freightPrice.subtract(grouponPrice).subtract(couponPrice).max(new BigDecimal(0));
        // 最终支付费用
        BigDecimal actualPrice = orderTotalPrice;

        Integer orderId = null;
        EyeOrder order = null;
        // 订单
        order = new EyeOrder();
        order.setUserId(userId);
        order.setOrderSn(orderService.generateOrderSn(userId));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setConsignee(checkedAddress.getName());
        order.setMobile(checkedAddress.getTel());
        order.setMessage(message);
        String detailedAddress = checkedAddress.getProvince() + checkedAddress.getCity() + checkedAddress.getCounty() + " " + checkedAddress.getAddressDetail();
        order.setAddress(detailedAddress);
        order.setGoodsPrice(goodsPrice);
        order.setFreightPrice(freightPrice);//配送费用
        order.setGrouponPrice(grouponPrice);
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(freightPrice);//订单费用
        order.setActualPrice(freightPrice);//实付费用

        if (order.getActualPrice().compareTo(goodsPrice)==0){
            order.setOrderStatus(OrderUtil.STATUS_RETUND_REFUSE);
        }

        orderService.add(order);
        orderId = order.getId();

        Integer goodsId = integralGoods.getGoodsId();
        EyeGoods goods = goodsService.findById(goodsId);
        EyeGoodsProduct product = goodsProductService.findById(productId);

        // 订单商品
        EyeOrderGoods orderGoods = new EyeOrderGoods();
        orderGoods.setOrderId(order.getId());//订单id
        orderGoods.setGoodsId(goods.getId());//商品id
        orderGoods.setGoodsSn(goods.getGoodsSn());//商品编号
        orderGoods.setProductId(productId);//商品货品表的货品id
        orderGoods.setGoodsName(goods.getName());//商品名称
        if (StringUtils.isEmpty(product.getUrl())) {
            orderGoods.setPicUrl(goods.getPicUrl());
        } else {
            orderGoods.setPicUrl(product.getUrl());
        }
        orderGoods.setPrice(product.getPrice());//商品货品的售价
        orderGoods.setNumber(number);//商品购买数量
        orderGoods.setSpecifications(product.getSpecifications());//商品货品的规格列表
        orderGoods.setAddTime(LocalDateTime.now());

        orderGoodsService.add(orderGoods);

        //商品货品数量减少
        int remainNumber = product.getNumber() - number;
        if (remainNumber < 0) {
            throw new RuntimeException("下单的商品货品数量大于库存量");
        }
        if (productService.reduceStock(productId, number) == 0) {
            throw new RuntimeException("商品货品库存减少失败");
        }

        //将用户积分减去
        uloveIntegral.setIntegration(uloveIntegral.getIntegration() - integration * number);
        integralService.update(uloveIntegral);

        // 订单支付超期任务
        taskService.addTask(new OrderUnpaidTask(orderId));

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);
        return ResponseUtil.ok(data);
    }
}