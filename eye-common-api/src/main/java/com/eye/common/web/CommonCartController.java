package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.*;
import com.eye.db.service.*;
import com.eye.db.util.MemberConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static com.eye.common.util.CommonResponseCode.GOODS_NO_STOCK;
import static com.eye.common.util.CommonResponseCode.GOODS_UNSHELVE;

/**
 * 用户购物车服务
 */
@RestController
@RequestMapping("/common/cart")
@Validated
@Api(description = "用户购物车服务")
public class CommonCartController {
    private final Log logger = LogFactory.getLog(CommonCartController.class);

    @Autowired
    private EyeCartService cartService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeGoodsProductService productService;
    @Autowired
    private EyeAddressService addressService;
    @Autowired
    private EyeGrouponRulesService grouponRulesService;
    @Autowired
    private EyeCouponService couponService;
    @Autowired
    private EyeCouponUserService couponUserService;
    @Autowired
    private CouponVerifyService couponVerifyService;
    @Autowired
    private EyeIntegralService integralService;
    @Autowired
    private EyeIntegralGoodsService integralGoodsService;
    @Autowired
    private EyeUserService userService;
    @Autowired
    private EyeMemberGoodsService memberGoodsService;

    /**
     * 用户购物车信息
     *
     * @param userId 用户ID
     * @return 用户购物车信息
     */
    @GetMapping("index")
    @ApiOperation("查询用户购物车信息")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object index(@LoginUser Integer userId) {
        String info = "查询用户购物车信息";
        logger.info(getClass()+"----------------------------"+info);
        //userId为空，提醒用户登录
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        //根据userId查询购物车列表
        List<EyeCart> list = cartService.queryByUid(userId);
        List<EyeCart> cartList = new ArrayList<>();
        // TODO
        // 如果系统检查商品已删除或已下架，则系统自动删除。
        // 更好的效果应该是告知用户商品失效，允许用户点击按钮来清除失效商品。
        for (EyeCart cart : list) {
            EyeGoods goods = goodsService.findById(cart.getGoodsId());
            if (goods == null || !goods.getIsOnSale()) {
                cartService.deleteById(cart.getId());
                logger.debug("系统自动删除失效购物车商品 goodsId=" + cart.getGoodsId() + " productId=" + cart.getProductId());
            } else {
                cartList.add(cart);
            }
        }
        //重新计算购物车的总价格
        Integer goodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal(0.00);
        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
        for (EyeCart cart : cartList) {
            goodsCount += cart.getNumber();
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if (cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> result = new HashMap<>();
        result.put("cartList", cartList);
        result.put("cartTotal", cartTotal);

        return ResponseUtil.ok(result);
    }

    /**
     * 加入商品到购物车
     * <p>
     * 如果已经存在购物车货品，则增加数量；
     * 否则添加新的购物车货品项。
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return 加入购物车操作结果
     */
    @PostMapping("add")
    @ApiOperation("将商品加入到购物车")
    public Object add(@LoginUser Integer userId, @RequestBody EyeCart cart) {
        String info = "加入商品到购物车";
        logger.info(getClass()+"----------------------------"+info);
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cart == null) {
            return ResponseUtil.badArgument();
        }
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        //从购物车中获取productId(货品id),number(货品数量),goodId(商品id),并判断是否为空，为空返回参数不对
        if (!ObjectUtils.allNotNull(productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }
        if (number <= 0) {
            return ResponseUtil.badArgument();
        }
        //判断商品是否可以购买
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        EyeGoodsProduct product = productService.findById(productId);
        //判断购物车中是否存在此规格商品
        EyeCart existCart = cartService.queryExist(goodsId, productId, userId);
        if (existCart == null) {
            //取得规格的信息,判断规格库存
            if (product == null || number > product.getNumber()) {
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
        } else {
            //取得规格的信息,判断规格库存
            int num = existCart.getNumber() + number;
            if (num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if (cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return goodscount(userId);
    }

    /**
     * 立即购买
     * <p>
     * 和add方法的区别在于：
     * 1. 如果购物车内已经存在购物车货品，前者的逻辑是数量添加，这里的逻辑是数量覆盖
     * 2. 添加成功以后，前者的逻辑是返回当前购物车商品数量，这里的逻辑是返回对应购物车项的ID
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return 立即购买操作结果
     */
    @PostMapping("fastadd")
    @ApiOperation("用户立即购买")
    public Object fastadd(@LoginUser Integer userId, @RequestBody EyeCart cart) {
        String info = "用户立即购买";
        logger.info(getClass()+"----------------------------"+info);
        if (userId == null) {
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
        EyeCart existCart = cartService.queryExist(goodsId, productId, userId);
        if (existCart == null) {
            //取得规格的信息,判断规格库存
            if (product == null || number > product.getNumber()) {
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
        } else {
            //取得规格的信息,判断规格库存
            int num = number;
            if (num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if (cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return ResponseUtil.ok(existCart != null ? existCart.getId() : cart.getId());
    }

    /**
     * 修改购物车商品货品数量
     *
     * @param userId 用户ID
     * @param cart   购物车商品信息， { id: xxx, goodsId: xxx, productId: xxx, number: xxx }
     * @return 修改结果
     */
    @PostMapping("update")
    @ApiOperation("修改购物车商品货品数量")
    public Object update(@LoginUser Integer userId, @RequestBody EyeCart cart) {
        String info = "修改购物车";
        logger.info(getClass()+"----------------------------"+info);
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        Integer id = cart.getId();
        if (!ObjectUtils.allNotNull(id, productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }
        if (number <= 0) {
            return ResponseUtil.badArgument();
        }

        //判断是否存在该订单
        // 如果不存在，直接返回错误
        EyeCart existCart = cartService.findById(userId, id);
        if (existCart == null) {
            return ResponseUtil.badArgumentValue();
        }

        // 判断goodsId和productId是否与当前cart里的值一致，不一致，返回参数值错误
        if (!existCart.getGoodsId().equals(goodsId)) {
            return ResponseUtil.badArgumentValue();
        }
        if (!existCart.getProductId().equals(productId)) {
            return ResponseUtil.badArgumentValue();
        }

        //判断商品是否可以购买
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        //根据productId查询商品货品信息,判断规格库存是否满足
        EyeGoodsProduct product = productService.findById(productId);
        if (product == null || product.getNumber() < number) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "库存不足");
        }

        existCart.setNumber(number.shortValue());
        if (cartService.updateById(existCart) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    /**
     * 购物车商品货品勾选状态
     * <p>
     * 如果原来没有勾选，则设置勾选状态；如果商品已经勾选，则设置非勾选状态。
     *
     * @param userId 用户ID
     * @param body   购物车商品信息， { productIds: xxx, isChecked: 1/0 }
     * @return 购物车信息
     */
    @PostMapping("checked")
    @ApiOperation("设置购物车商品货品勾选状态")
    public Object checked(@LoginUser Integer userId, @RequestBody String body) {
        String info = "购物车商品货品勾选状态";
        logger.info(getClass()+"----------------------------"+info);
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }

        List<Integer> productIds = JacksonUtil.parseIntegerList(body, "productIds");
        if (productIds == null) {
            return ResponseUtil.badArgument();
        }

        Integer checkValue = JacksonUtil.parseInteger(body, "isChecked");
        if (checkValue == null) {
            return ResponseUtil.badArgument();
        }
        Boolean isChecked = (checkValue == 1);

        cartService.updateCheck(userId, productIds, isChecked);
        return index(userId);
    }

    /**
     * 购物车商品删除
     *
     * @param userId 用户ID
     * @param body   购物车商品信息， { productIds: xxx }
     * @return 购物车信息
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data: xxx
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("delete")
    @ApiOperation("删除购物车商品")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        String info = "删除购物车商品";
        logger.info(getClass()+"----------------------------"+info);
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }

        List<Integer> productIds = JacksonUtil.parseIntegerList(body, "productIds");

        if (productIds == null || productIds.size() == 0) {
            return ResponseUtil.badArgument();
        }

        cartService.delete(productIds, userId);
        return index(userId);
    }

    /**
     * 购物车商品货品数量
     * <p>
     * 如果用户没有登录，则返回空数据。
     *
     * @param userId 用户ID
     * @return 购物车商品货品数量
     */
    @GetMapping("goodscount")
    @ApiOperation("购物车商品货品数量")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object goodscount(@LoginUser Integer userId) {
        String info = "购物车商品货品数量";
        logger.info(getClass()+"----------------------------"+info);
        if (userId == null) {
            return ResponseUtil.ok(0);
        }

        int goodsCount = 0;
        List<EyeCart> cartList = cartService.queryByUid(userId);
        for (EyeCart cart : cartList) {
            goodsCount += cart.getNumber();
        }

        return ResponseUtil.ok(goodsCount);
    }


    /**
     * 购物车下单
     *
     * @param userId    用户ID
     * @param cartId    购物车商品ID：
     *                  如果购物车商品ID是空，则下单当前用户所有购物车商品；
     *                  如果购物车商品ID非空，则只下单当前购物车商品。
     * @param addressId 收货地址ID：
     *                  如果收货地址ID是空，则查询当前用户的默认地址。
     * @param couponId  优惠券ID：
     *                  如果优惠券ID是空，则自动选择合适的优惠券。
     * @param userCouponId  用户优惠券id
     * @param grouponRulesId  团购规则id
     * @param integralId   会员积分id
     * @param memberGoodsId 会员商品id
     * @return 购物车操作结果
     */
    @GetMapping("checkout")
    @ApiOperation("购物车下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="cartId",value = "购物车商品ID",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="addressId",value = "收货地址ID",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="couponId",value = "优惠券ID",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="userCouponId",value = "用户优惠券id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="grouponRulesId",value = "团购规则id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="integralId",value = "会员积分id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="memberGoodsId",value = "会员商品id",required=false,paramType="path",dataType="int")

    })
    public Object checkout(@LoginUser Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer userCouponId, Integer grouponRulesId, Integer integralId, Integer memberGoodsId) {
        String info = "购物车下单";
        logger.info(getClass()+"----------------------------"+info);
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 收货地址
        EyeAddress checkedAddress = null;
        if (addressId == null || addressId.equals(0)) {
            checkedAddress = addressService.findDefault(userId);
            // 如果仍然没有地址，则是没有收货地址,返回一个空的地址id=0，这样前端则会提醒添加地址
            if (checkedAddress == null) {
                checkedAddress = new EyeAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }

        } else {
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

        // 会员商品优惠
        BigDecimal memberPirce = new BigDecimal(0);
        EyeMemberGoods uloveMemberGoods = memberGoodsService.findById(memberGoodsId);
        if (uloveMemberGoods != null){
            memberPirce =uloveMemberGoods.getRetailPrice().subtract(uloveMemberGoods.getDiscountPrice());

            //判断用户是否是VIP用户
            EyeUser user = userService.findById(userId);
            Byte userLevel = user.getUserLevel();
            if (!userLevel.equals(MemberConstant.USER_LEVEL_VIP)){
                return ResponseUtil.fail(MemberConstant.USER_LEVEL_NO,"您还不是会员用户");
            }
        }

        // 商品价格
        List<EyeCart> checkedGoodsList = null;
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            EyeCart cart = cartService.findById(userId, cartId);
            if (cart == null) {
                return ResponseUtil.badArgumentValue();
            }
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (EyeCart cart : checkedGoodsList) {
            //  只有当团购规格商品ID符合才进行团购优惠
            if (grouponRules != null && grouponRules.getGoodsId().equals(cart.getGoodsId())) {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
            } else {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
            //自有会员商品id复合才能进行团购优惠
            if (uloveMemberGoods != null && uloveMemberGoods.getGoodsId().equals(cart.getGoodsId())){
                checkedGoodsPrice = checkedGoodsPrice.subtract(memberPirce.multiply(new BigDecimal(cart.getNumber())));
            }
        }

        // 计算优惠券可用情况
        BigDecimal tmpCouponPrice = new BigDecimal(0.00);
        Integer tmpCouponId = 0;
        Integer tmpUserCouponId = 0;
        int tmpCouponLength = 0;
        List<EyeCouponUser> couponUserList = couponUserService.queryAll(userId);
        for (EyeCouponUser couponUser : couponUserList) {
            EyeCoupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), couponUser.getId(), checkedGoodsPrice, checkedGoodsList);
            if (coupon == null) {
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
        if (couponId == null || couponId.equals(-1)) {
            couponId = -1;
            userCouponId = -1;
        } else if (couponId.equals(0)) {
            couponPrice = tmpCouponPrice;
            couponId = tmpCouponId;
            userCouponId = tmpUserCouponId;
        } else {
            EyeCoupon coupon = couponVerifyService.checkCoupon(userId, couponId, userCouponId, checkedGoodsPrice, checkedGoodsList);

            // 用户选择的优惠券有问题，则选择合适优惠券，否则使用用户选择的优惠券
            if (coupon == null) {
                couponPrice = tmpCouponPrice;
                couponId = tmpCouponId;
                userCouponId = tmpUserCouponId;
            } else {
                couponPrice = coupon.getDiscount();
            }
        }

        //如果是VIP商品,提交订单时商品显示优惠后的价格
        if (uloveMemberGoods != null){
            for (EyeCart cart : checkedGoodsList) {
                cart.setPrice(uloveMemberGoods.getDiscountPrice());
            }
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
        /*if (accessoryList.size() != checkedGoodsList.size()){
            if (checkedGoodsPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                freightPrice = SystemConfig.getFreight();
            }
        }*/

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);

        //将用户积分除以一千,得到积分抵挡金钱的额度
        EyeIntegral uloveIntegral = integralService.findByUserId(userId);
        if (uloveIntegral != null){
            Integer integration = uloveIntegral.getIntegration();
            //用户使用积分或不使用积分 intagralId为空或0时不使用用户积分,
            if (integralId == null || integralId == 0){
                integralId = 0;
            }else {
                integralId = uloveIntegral.getId();
                integralPrice = BigDecimal.valueOf(integration).divide(MemberConstant.INTEGRAL_PERCENT);
                integralPrice = integralPrice.setScale(2,BigDecimal.ROUND_DOWN);
            }
        }

        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0.00));

        BigDecimal subtract = orderTotalPrice.subtract(integralPrice);

        integralPrice = integralPrice.multiply(MemberConstant.INTEGRAL_PERCENT);

        if (subtract.compareTo(BigDecimal.valueOf(0))==-1 || subtract.compareTo(BigDecimal.valueOf(0))==0){
            BigDecimal decimal = BigDecimal.valueOf(0.01);
            subtract=decimal;
            //未减去积分之前的价格减去0.01
            integralPrice=orderTotalPrice.multiply(MemberConstant.INTEGRAL_PERCENT).subtract(decimal.multiply(MemberConstant.INTEGRAL_PERCENT));
        }


        BigDecimal actualPrice = subtract;

        Map<String, Object> data = new HashMap<>();
        data.put("addressId", addressId);
        data.put("couponId", couponId);
        data.put("userCouponId", userCouponId);
        data.put("cartId", cartId);
        data.put("grouponRulesId", grouponRulesId);
        data.put("integralId",integralId);
        data.put("memberGoodsId",memberGoodsId);
        data.put("grouponPrice", grouponPrice);
        data.put("memberPrice",memberPirce);
        data.put("checkedAddress", checkedAddress);
        data.put("availableCouponLength", availableCouponLength);
        data.put("goodsTotalPrice", checkedGoodsPrice);
        data.put("freightPrice", freightPrice);
        data.put("couponPrice", couponPrice);
        data.put("orderTotalPrice", orderTotalPrice);
        data.put("actualPrice", actualPrice);
        data.put("checkedGoodsList", checkedGoodsList);
        data.put("integralPrice",integralPrice);
        return ResponseUtil.ok(data);
    }

    //积分商品
    @GetMapping("checkIntegral")
    @ApiOperation("积分商品下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="addressId",value = "收货地址ID",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="productId",value = "积分商品规格",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="integralGoodsId",value = "积分商品id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="number",value = "积分商品兑换数量",required=true,paramType="path",dataType="int")

    })
    public Object checkIntegral(@LoginUser Integer userId, Integer addressId,Integer productId, Integer integralGoodsId,Integer number){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        if(integralGoodsId == null || productId == null || number == null){
            return ResponseUtil.badArgument();
        }

        EyeIntegral uloveIntegral = integralService.findByUserId(userId);

        if (uloveIntegral == null){
            return ResponseUtil.fail(MemberConstant.USER_LEVEL_NO,"您还不会员用户");
        }

        // 收货地址
        EyeAddress checkedAddress = null;
        if (addressId == null || addressId.equals(0)) {
            checkedAddress = addressService.findDefault(userId);
            // 如果仍然没有地址，则是没有收货地址,返回一个空的地址id=0，这样前端则会提醒添加地址
            if (checkedAddress == null) {
                checkedAddress = new EyeAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }

        } else {
            checkedAddress = addressService.query(userId, addressId);
            // 如果null, 则报错
            if (checkedAddress == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        EyeGoodsProduct product = productService.findById(productId);
        //取得规格的信息,判断规格库存
        if (product == null || number > product.getNumber()) {
            return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
        }

        EyeIntegralGoods integralGoods = integralGoodsService.findById(integralGoodsId);
        if (integralGoods ==null){
            return ResponseUtil.badArgumentValue();
        }
        Integer integration = integralGoods.getIntegration();

        //邮费
        BigDecimal freightPrice = new BigDecimal(0);

        // 根据附件表判断是否需要运费
        /*EyeAccessory accessory = accessoryService.findByGoodsId(integralGoods.getGoodsId());
        if (accessory == null){
            freightPrice = SystemConfig.getFreight();
        }*/


        Integer integrals = integration * number;

        //判断用户积分是否充足

        Integer integral = uloveIntegral.getIntegration();
        if (integral < integrals) {
            return ResponseUtil.fail(MemberConstant.INTEGRAL_OF_LACK, "您的积分不足");
        }

        List<EyeIntegralGoods> integralGoodsList = new ArrayList<>();
        integralGoodsList.add(integralGoods);

        Map<String, Object> data = new HashMap<>();
        data.put("addressId", addressId);
        data.put("productId",productId);
        data.put("integralId",uloveIntegral.getId());
        data.put("checkedAddress", checkedAddress);
        data.put("freightPrice", freightPrice);

        data.put("actualPrice", freightPrice);
        data.put("number",number);
        data.put("list",integralGoodsList);
        data.put("integration",integrals);
        data.put("integralGoodsId",integralGoodsId);
        return ResponseUtil.ok(data);

    }


}
