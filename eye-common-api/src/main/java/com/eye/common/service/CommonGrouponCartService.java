package com.eye.common.service;

import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.*;
import com.eye.db.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eye.common.util.CommonResponseCode.GOODS_NO_STOCK;
import static com.eye.common.util.CommonResponseCode.GOODS_UNSHELVE;

@Service
public class CommonGrouponCartService {


    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeGoodsProductService productService;
    @Autowired
    private EyeGrouponCartService cartService;
    @Autowired
    private EyeAddressService addressService;
    @Autowired
    private EyeGrouponRulesService grouponRulesService;
    @Autowired
    private EyeUserService userService;
    @Autowired
    private EyeCouponUserService couponUserService;
    @Autowired
    private CouponVerifyService couponVerifyService;


    public Object fastadd(Integer userId, EyeGrouponCart cart) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }

        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        if (!ObjectUtils.allNotNull(productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0) {
            return ResponseUtil.badArgument();
        }
        //判断商品是否可以购买
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }
        EyeGoodsProduct product = productService.findById(productId);
        //判断购物车中是否存在此规格商品
        EyeGrouponCart existCart = cartService.queryExist(goodsId, productId, userId);
        if(existCart == null){
            //取得规格的信息,判断规格库存
            if(product == null || number > product.getNumber()){
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            cart.setId(null);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName((goods.getName()));
            if (StringUtils.isEmpty(product.getUrl())) {
                cart.setPicUrl(goods.getPicUrl());
            } else {
                cart.setPicUrl(product.getUrl());
            }
            cart.setPrice(product.getPrice());
            cart.setSpecifications(product.getSpecifications());
            cart.setUserId(userId);
            cart.setChecked(true);
            cartService.add(cart);
        }else{
            //取得规格的信息,判断规格库存
            int num = number;
            if (product==null || num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
           existCart.setNumber((short)num);
            if(cartService.updateById(existCart) == 0){
                return ResponseUtil.updatedDataFailed();
            }
        }

        return ResponseUtil.ok(existCart != null ? existCart.getId() : cart.getId());
    }

    public Object checkout(Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer userCouponId, Integer grouponRulesId) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        //收获地址
        EyeAddress checkedAddress = null;
        if(addressId == null || addressId.equals(0)){
            checkedAddress = addressService.findDefault(userId);
            // 如果仍然没有地址，则是没有收货地址,返回一个空的地址id=0，这样前端则会提醒添加地址
            if (checkedAddress == null) {
                checkedAddress = new EyeAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }
        }else {
            checkedAddress = addressService.query(userId, addressId);
            // 如果null, 则报错
            if (checkedAddress == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        // 团购优惠
        BigDecimal grouponPrice = new BigDecimal(0.00);
        EyeGrouponRules grouponRules = grouponRulesService.findById(grouponRulesId);
        if (grouponRules != null) {
            grouponPrice = grouponRules.getDiscount();
        }

        // 商品价格
        List<EyeGrouponCart> checkedGoodsList = null;
        if(cartId == null || cartId.equals(0)){
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            EyeGrouponCart cart = cartService.findById(userId, cartId);
            if (cart == null) {
                return ResponseUtil.badArgumentValue();
            }
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }

        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (EyeGrouponCart cart : checkedGoodsList) {
            //  只有当团购规格商品ID符合才进行团购优惠
            if (grouponRules != null && grouponRules.getGoodsId().equals(cart.getGoodsId())) {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
            } else {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }

        // 计算优惠券可用情况
        BigDecimal tmpCouponPrice = new BigDecimal(0.00);
        Integer tmpCouponId = 0;
        Integer tmpUserCouponId = 0;
        int tmpCouponLength = 0;
        List<EyeCouponUser> couponUserList = couponUserService.queryAll(userId);
        for(EyeCouponUser couponUser : couponUserList){
            EyeCoupon coupon = couponVerifyService.checkGouponGoodsCoupon(userId, couponUser.getCouponId(), couponUser.getId(), checkedGoodsPrice, checkedGoodsList);
            if(coupon == null){
                continue;
            }

            tmpCouponLength++;
            //如果用户优惠卷优惠价格大于0，则对优惠价格，优惠卷id,用户优惠卷id重新赋给其他变量
            if (tmpCouponPrice.compareTo(coupon.getDiscount()) == -1) {
                tmpCouponPrice = coupon.getDiscount();
                tmpCouponId = coupon.getId();
                tmpUserCouponId = couponUser.getId();
            }
        }
        // 获取优惠券减免金额，优惠券可用数量
        int availableCouponLength = tmpCouponLength;
        BigDecimal couponPrice = new BigDecimal(0);
        // 这里存在三种情况
        // 1. 用户不想使用优惠券，则不处理
        // 2. 用户想自动使用优惠券，则选择合适优惠券
        // 3. 用户已选择优惠券，则测试优惠券是否合适
        if (couponId == null || couponId.equals(-1)){
            couponId = -1;
            userCouponId = -1;
        }
        else if (couponId.equals(0)) {
            couponPrice = tmpCouponPrice;
            couponId = tmpCouponId;
            userCouponId = tmpUserCouponId;
        }
        else {
            EyeCoupon coupon = couponVerifyService.checkGouponGoodsCoupon(userId, couponId, userCouponId, checkedGoodsPrice, checkedGoodsList);
            // 用户选择的优惠券有问题，则选择合适优惠券，否则使用用户选择的优惠券
            if(coupon == null){
                couponPrice = tmpCouponPrice;
                couponId = tmpCouponId;
                userCouponId = tmpUserCouponId;
            }
            else {
                couponPrice = coupon.getDiscount();
            }
        }
        // 根据订单商品总价计算运费，满88则免运费，否则8元；
        BigDecimal freightPrice = new BigDecimal(0.00);
//        if (checkedGoodsPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
//            freightPrice = SystemConfig.getFreight();
//        }

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);

        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0.00));

        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        Map<String, Object> data = new HashMap<>();
        data.put("addressId", addressId);
        data.put("couponId", couponId);
        data.put("userCouponId", userCouponId);
        data.put("cartId", cartId);
        data.put("grouponRulesId", grouponRulesId);
        data.put("grouponPrice", grouponPrice);
        data.put("checkedAddress", checkedAddress);
        data.put("availableCouponLength", availableCouponLength);
        data.put("goodsTotalPrice", checkedGoodsPrice);
        data.put("freightPrice", freightPrice);
        data.put("couponPrice", couponPrice);
        data.put("orderTotalPrice", orderTotalPrice);
        data.put("actualPrice", actualPrice);
        data.put("checkedGoodsList", checkedGoodsList);

        return ResponseUtil.ok(data);
    }
}
