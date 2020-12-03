package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonGrouponCartService;
import com.eye.db.domain.EyeGrouponCart;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common/groupon/cart")
public class CommonGrouponCartController {

    private final Log logger =  LogFactory.getLog(CommonGrouponCartController.class);

    @Autowired
    private CommonGrouponCartService wxGrouponCartService;

    /**
     * 团购商品购买
     * @param userId
     * @param cart
     * @return
     */
    @PostMapping("buying")
    public Object fastadd(@LoginUser Integer userId, @RequestBody EyeGrouponCart cart){
        logger.info(getClass()+"----------------------------团购商品立即购买");
        return wxGrouponCartService.fastadd(userId,cart);
    }

    @GetMapping("checkout")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="cartId",value = "购物车id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="addressId",value = "地址id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="couponId",value = "优惠券id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="userCouponId",value = "用户优惠券id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="grouponRulesId",value = "团购规则id",required=true,paramType="path",dataType="int")
    })
    public Object checkOut(@LoginUser Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer userCouponId, Integer grouponRulesId){
        logger.info(getClass()+"----------------------------团购商品购物车下单");
        return wxGrouponCartService.checkout(userId,cartId, addressId,couponId,userCouponId,grouponRulesId);
    }
}
