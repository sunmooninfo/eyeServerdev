package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonAccessoryService;
import com.eye.core.system.SystemConfig;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.*;
import com.eye.db.service.*;
import com.eye.db.util.MemberConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/common/integral/goods")
@Validated
@Api(description = "积分商品服务")
public class CommonIntegralGoodsController {
    private final Log logger = LogFactory.getLog(CommonIntegralGoodsController.class);

    @Autowired
    private EyeIntegralGoodsService integralGoodsService;

    @Autowired
    private EyeAddressService addressService;

    @Autowired
    private EyeGoodsService goodsService;

    @Autowired
    private EyeGoodsProductService productService;

    @Autowired
    private EyeIssueService goodsIssueService;

    @Autowired
    private EyeBrandService brandService;

    @Autowired
    private EyeFootprintService footprintService;

    @Autowired
    private EyeGoodsSpecificationService goodsSpecificationService;

    @Autowired
    private EyeUserService userService;

    @Autowired
    private EyeCollectService collectService;

    @Autowired
    private CommonAccessoryService accessoryService;

    @Autowired
    private EyeIntegralService integralService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();


    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);


    //查看积分商品
    @GetMapping("/list")
    @ApiOperation("积分商品列表")
    public Object list(Boolean isShown,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        List<EyeIntegralGoods> integralGoodsList = integralGoodsService.querySelective(page, limit, sort, order,isShown);
        return ResponseUtil.okList(integralGoodsList);
    }


    //商品详情
    @GetMapping("/detail")
    @ApiOperation("积分商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="id",value = "积分商品id",required=true,paramType="path",dataType="int")
    })
    public Object detail(@LoginUser Integer userId, @NotNull Integer id, Boolean isShown){

        EyeMemberGoodsExample example = new EyeMemberGoodsExample();
        EyeMemberGoodsExample.Criteria criteria = example.or();
        if (!StringUtils.isEmpty(isShown)){
            criteria.andIsShownEqualTo(isShown);
        }

        //积分商品信息
        EyeIntegralGoods uloveIntegralGoods = integralGoodsService.findById(id);
        Integer goodsId = uloveIntegralGoods.getGoodsId();

        //商品信息
        EyeGoods litemallGoods = goodsService.findById(goodsId);

        // 商品规格 返回的是定制的GoodsSpecificationVo  lambda表达式
        Callable<Object> objectCallable = () -> goodsSpecificationService.getSpecificationVoList(goodsId);

        //商品规格 对应的商品数量和价格
        Callable<List> productListCallable = () -> productService.queryByGid(goodsId);

        //商品问题 一些通用的问题
        Callable<List> issueCallable = () -> goodsIssueService.querySelective("", 1, 4, "", "");

        // 商品品牌商
        Callable<EyeBrand> brandCallable = ()->{
            Integer brandId = litemallGoods.getBrandId();
            EyeBrand brand;
            if (brandId == 0) {
                brand = new EyeBrand();
            } else {
                brand = brandService.findById(litemallGoods.getBrandId());
            }
            return brand;
        };

        // 用户收藏
        int userHasCollect = 0;
        if (userId != null) {
            userHasCollect = collectService.count(userId, goodsId);
        }

        // 记录用户的足迹 异步处理
        if (userId != null) {
            executorService.execute(()->{
                EyeFootprint footprint = new EyeFootprint();
                footprint.setUserId(userId);
                footprint.setGoodsId(goodsId);
                footprintService.add(footprint);
            });
        }

        //判断用户是否是VIP用户
        Boolean level = false;
        //判断用户积分是否充足
        Boolean adequate = false;
        if (userId != null){
            EyeUser user = userService.findById(userId);
            if (user.getUserLevel().equals(MemberConstant.USER_LEVEL_VIP)){
                level = true;

                //判断用户积分是否充足
                EyeIntegral integral = integralService.findByUser(userId);
                if (integral.getIntegration() >= uloveIntegralGoods.getIntegration()){
                    adequate = true;
                }
            }
        }



        FutureTask<Object> objectCallableTask = new FutureTask<>(objectCallable);
        FutureTask<List> productListCallableTask = new FutureTask<>(productListCallable);
        FutureTask<List> issueCallableTask = new FutureTask<>(issueCallable);
        FutureTask<EyeBrand> brandCallableTask = new FutureTask<>(brandCallable);

        executorService.submit(objectCallableTask);
        executorService.submit(productListCallableTask);
        executorService.submit(issueCallableTask);
        executorService.submit(brandCallableTask);

        Map<String, Object> data = new HashMap<>();
        EyeAccessory accessory = null;
        if (userId != null){
            accessory = accessoryService.findByGoodsId(userId, id);
        }

        try {
            data.put("uloveIntegralGoods", uloveIntegralGoods);
            data.put("litemallGoods", litemallGoods);
            data.put("userHasCollect", userHasCollect);
            data.put("issue", issueCallableTask.get());
            data.put("specificationList", objectCallableTask.get());
            data.put("productList", productListCallableTask.get());
            data.put("brand", brandCallableTask.get());
            data.put("share", SystemConfig.isAutoCreateShareImage());
            data.put("level",level);
            data.put("accessory",accessory);
            data.put("adequate",adequate);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //商品分享图片地址
        data.put("shareImage", litemallGoods.getShareUrl());

        return ResponseUtil.ok(data);
    }
}
