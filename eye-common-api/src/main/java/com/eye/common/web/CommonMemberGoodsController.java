package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonAccessoryService;
import com.eye.common.service.CommonMemberGoodsService;
import com.eye.common.vo.MemberGoodsVo;
import com.eye.core.system.SystemConfig;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.*;
import com.eye.db.service.*;
import com.eye.db.util.MemberConstant;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/common/member/goods")
@Validated
@Api(description = "会员商品详情")
public class CommonMemberGoodsController {
    private final Log logger = LogFactory.getLog(CommonMemberGoodsController.class);

    @Resource
    private CommonMemberGoodsService memberGoodsService;

    @Autowired
    private EyeGoodsService goodsService;

    @Autowired
    private EyeGoodsProductService productService;

    @Autowired
    private EyeIssueService goodsIssueService;

    @Autowired
    private EyeGoodsAttributeService goodsAttributeService;

    @Autowired
    private EyeBrandService brandService;

    @Autowired
    private EyeCommentService commentService;

    @Autowired
    private EyeUserService userService;

    @Autowired
    private EyeCollectService collectService;

    @Autowired
    private EyeFootprintService footprintService;

    @Autowired
    private CommonAccessoryService accessoryService;

    @Autowired
    private EyeGoodsSpecificationService goodsSpecificationService;

    @Autowired
    private EyeGrouponRulesService rulesService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);


    /**
     * 会员商品列表
     *
     * @param page  分页页数
     * @param limit 分页大小
     * @return 会员商品列表
     */
    @GetMapping("/list")
    @ApiOperation("会员商品列表")
    public Object list(Boolean isShown,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<MemberGoodsVo> memberGoodsVoList = memberGoodsService.list(page, limit, sort, order,isShown);
        return ResponseUtil.okList(memberGoodsVoList);
    }

    /**
     * 商品详情
     * <p>
     * 用户可以不登录。
     * 如果用户登录，则记录用户足迹以及返回用户收藏信息。
     *
     * @param userId 用户ID
     * @param id     商品ID
     * @return 商品详情
     */
    @GetMapping("detail")
    @ApiOperation("会员商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="id",value = "会员商品id",required=true,paramType="path",dataType="int")
    })
    public Object detail(@LoginUser Integer userId, @NotNull Integer id) {

//        UloveMemberGoodsExample example = new UloveMemberGoodsExample();
//        UloveMemberGoodsExample.Criteria criteria = example.or();
//        if (!StringUtils.isEmpty(isShown)){
//            criteria.andIsShownEqualTo(isShown);
//        }

        EyeMemberGoods memberGoods = memberGoodsService.findById(id);

        Integer goodsId = memberGoods.getGoodsId();

        // 商品信息
        EyeGoods info = goodsService.findById(goodsId);

        // 商品属性
        Callable<List> goodsAttributeListCallable = () -> goodsAttributeService.queryByGid(goodsId);

        // 商品规格 返回的是定制的GoodsSpecificationVo
        Callable<Object> objectCallable = () -> goodsSpecificationService.getSpecificationVoList(goodsId);

        // 商品规格对应的数量和价格
        //Callable<List> productListCallable = () -> productService.queryByGid(goodsId);
        Callable<List> productListCallable = () ->{
            List<EyeGoodsProduct> litemallGoodsProducts = productService.queryByGid(goodsId);
            for (EyeGoodsProduct product : litemallGoodsProducts) {
                BigDecimal subtract = (new BigDecimal((double)memberGoods.getDiscount()/100));
                product.setPrice(product.getPrice().multiply(subtract));
            }
            return litemallGoodsProducts;
        };

        // 商品问题，这里是一些通用问题
        Callable<List> issueCallable = () -> goodsIssueService.querySelective("", 1, 4, "", "");

        // 商品品牌商
        Callable<EyeBrand> brandCallable = ()->{
            Integer brandId = info.getBrandId();
            EyeBrand brand;
            if (brandId == 0) {
                brand = new EyeBrand();
            } else {
                brand = brandService.findById(info.getBrandId());
            }
            return brand;
        };

        // 评论
        Callable<Map> commentsCallable = () -> {
            List<EyeComment> comments = commentService.queryGoodsByGid(goodsId, 0, 2);
            List<Map<String, Object>> commentsVo = new ArrayList<>(comments.size());
            long commentCount = PageInfo.of(comments).getTotal();
            for (EyeComment comment : comments) {
                Map<String, Object> c = new HashMap<>();
                c.put("id", comment.getId());
                c.put("addTime", comment.getAddTime());
                c.put("content", comment.getContent());
                c.put("adminContent", comment.getAdminContent());
                EyeUser user = userService.findById(comment.getUserId());
                c.put("nickname", user == null ? "" : user.getNickname());
                c.put("avatar", user == null ? "" : user.getAvatar());
                c.put("picList", comment.getPicUrls());
                commentsVo.add(c);
            }
            Map<String, Object> commentList = new HashMap<>();
            commentList.put("count", commentCount);
            commentList.put("data", commentsVo);
            return commentList;
        };

        /*//团购信息
        Callable<List> grouponRulesCallable = () ->rulesService.queryByGoodsId(id);*/

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
        if (userId != null){
            EyeUser user = userService.findById(userId);
            if (user.getUserLevel().equals(MemberConstant.USER_LEVEL_VIP)){
                level = true;
            }
        }

        FutureTask<List> goodsAttributeListTask = new FutureTask<>(goodsAttributeListCallable);
        FutureTask<Object> objectCallableTask = new FutureTask<>(objectCallable);
        FutureTask<List> productListCallableTask = new FutureTask<>(productListCallable);
        FutureTask<List> issueCallableTask = new FutureTask<>(issueCallable);
        FutureTask<Map> commentsCallableTsk = new FutureTask<>(commentsCallable);
        FutureTask<EyeBrand> brandCallableTask = new FutureTask<>(brandCallable);
        /*FutureTask<List> grouponRulesCallableTask = new FutureTask<>(grouponRulesCallable);*/

        executorService.submit(goodsAttributeListTask);
        executorService.submit(objectCallableTask);
        executorService.submit(productListCallableTask);
        executorService.submit(issueCallableTask);
        executorService.submit(commentsCallableTsk);
        executorService.submit(brandCallableTask);
        /*executorService.submit(grouponRulesCallableTask);*/

        Map<String, Object> data = new HashMap<>();

        EyeAccessory accessory = null;
        if (userId != null){
            accessory = accessoryService.findByGoodsId(userId, id);
        }

        try {
            data.put("memberGoods",memberGoods);
            data.put("info", info);
            data.put("userHasCollect", userHasCollect);
            data.put("issue", issueCallableTask.get());
            data.put("comment", commentsCallableTsk.get());
            data.put("specificationList", objectCallableTask.get());
            data.put("productList", productListCallableTask.get());
            data.put("attribute", goodsAttributeListTask.get());
            data.put("brand", brandCallableTask.get());
            /*data.put("groupon", grouponRulesCallableTask.get());*/
            //SystemConfig.isAutoCreateShareImage()
            data.put("share", SystemConfig.isAutoCreateShareImage());
            data.put("accessory",accessory);
            data.put("level",level);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //商品分享图片地址
        data.put("shareImage", info.getShareUrl());
        return ResponseUtil.ok(data);
    }


}
