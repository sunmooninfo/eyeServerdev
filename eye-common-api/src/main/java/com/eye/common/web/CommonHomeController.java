package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonGrouponRuleService;
import com.eye.common.service.HomeCacheManager;
import com.eye.core.system.SystemConfig;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeCategory;
import com.eye.db.domain.EyeGoods;
import com.eye.db.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 首页服务
 */
@RestController
@RequestMapping("/common/home")
@Validated
@Api(description = "首页服务")
public class CommonHomeController {
    private final Log logger = LogFactory.getLog(CommonHomeController.class);

    @Autowired
    private EyeAdService adService;

    @Autowired
    private EyeGoodsService goodsService;

    @Autowired
    private EyeBrandService brandService;

    @Autowired
    private EyeTopicService topicService;

    @Autowired
    private EyeCategoryService categoryService;

    @Autowired
    private CommonGrouponRuleService grouponService;

    @Autowired
    private EyeCouponService couponService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);

    @GetMapping("/cache")
    @ApiOperation("清除缓存")
    @ApiImplicitParam(name="key",value = "输入:eye_cache",required=true,paramType="path",dataType="int")
    public Object cache(@NotNull String key) {
        if (!key.equals("eye_cache")) {
            return ResponseUtil.fail();
        }

        // 清除缓存
        HomeCacheManager.clearAll();
        return ResponseUtil.ok("缓存已清除");
    }

    /**
     * 首页数据
     * @param userId 当用户已经登录时，非空。为登录状态为null
     * @return 首页数据
     */
    @GetMapping("/index")
    @ApiOperation("首页数据")
    @ApiImplicitParam(name="userId",value = "用户ID",required=false,paramType="path",dataType="int")
    public Object index(@LoginUser Integer userId, Boolean isShown, Boolean isKill) {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.INDEX)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<List> bannerListCallable = () -> adService.queryIndex();

        Callable<List> channelListCallable = () -> categoryService.queryChannel();

        Callable<List> couponListCallable;
        if(userId == null){
            couponListCallable = () -> couponService.queryList(0, 3);
        } else {
            couponListCallable = () -> couponService.queryAvailableList(userId,0, 3);
        }


        Callable<List> newGoodsListCallable = () -> goodsService.queryByNew(0, SystemConfig.getNewLimit(),isShown);

        Callable<List> isshownGoodsListCallable = () -> goodsService.queryByisShown(0, SystemConfig.getIsShownLimit(),isShown);

        Callable<List> hotGoodsListCallable = () -> goodsService.queryByHot(0, SystemConfig.getHotLimit(),isShown);
        Callable<List> killGoodsListCallable = () -> goodsService.queryByKill(0, SystemConfig.getKillLimit(),isShown);
        Callable<List> brandListCallable = () -> brandService.query(0, SystemConfig.getBrandLimit());

        Callable<List> topicListCallable = () -> topicService.queryList(0, SystemConfig.getTopicLimit());

        //团购专区
        Callable<List> grouponListCallable = () -> grouponService.queryList(0, 5);

        Callable<List> floorGoodsListCallable = () -> getCategoryList(isShown);

        FutureTask<List> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List> channelTask = new FutureTask<>(channelListCallable);
        FutureTask<List> couponListTask = new FutureTask<>(couponListCallable);
        FutureTask<List> newGoodsListTask = new FutureTask<>(newGoodsListCallable);
        FutureTask<List> isshownGoodsListTask = new FutureTask<>(isshownGoodsListCallable);
        FutureTask<List> hotGoodsListTask = new FutureTask<>(hotGoodsListCallable);
        FutureTask<List> killGoodsListTask = new FutureTask<>(killGoodsListCallable);
        FutureTask<List> brandListTask = new FutureTask<>(brandListCallable);
        FutureTask<List> topicListTask = new FutureTask<>(topicListCallable);
        FutureTask<List> grouponListTask = new FutureTask<>(grouponListCallable);
        FutureTask<List> floorGoodsListTask = new FutureTask<>(floorGoodsListCallable);

        executorService.submit(bannerTask);
        executorService.submit(channelTask);
        executorService.submit(couponListTask);
        executorService.submit(newGoodsListTask);
        executorService.submit(isshownGoodsListTask);
        executorService.submit(hotGoodsListTask);
        executorService.submit(killGoodsListTask);
        executorService.submit(brandListTask);
        executorService.submit(topicListTask);
        executorService.submit(grouponListTask);
        executorService.submit(floorGoodsListTask);

        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());
            entity.put("channel", channelTask.get());
            entity.put("couponList", couponListTask.get());
            entity.put("newGoodsList", newGoodsListTask.get());
            entity.put("isshownGoodsList", isshownGoodsListTask.get());
            entity.put("hotGoodsList", hotGoodsListTask.get());
            entity.put("killGoodsList", killGoodsListTask.get());
            entity.put("brandList", brandListTask.get());
            entity.put("topicList", topicListTask.get());
            entity.put("grouponList", grouponListTask.get());
            entity.put("floorGoodsList", floorGoodsListTask.get());
            //缓存数据
            HomeCacheManager.loadData(HomeCacheManager.INDEX, entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
        return ResponseUtil.ok(entity);
    }

    private List<Map> getCategoryList(Boolean isShown) {
        List<Map> categoryList = new ArrayList<>();
        List<EyeCategory> catL1List = categoryService.queryL1WithoutRecommend(0, SystemConfig.getCatlogListLimit());
        for (EyeCategory catL1 : catL1List) {
            List<EyeCategory> catL2List = categoryService.queryByPid(catL1.getId(),isShown);
            List<Integer> l2List = new ArrayList<>();
            for (EyeCategory catL2 : catL2List) {
                l2List.add(catL2.getId());
            }

            List<EyeGoods> categoryGoods;
            if (l2List.size() == 0) {
                categoryGoods = new ArrayList<>();
            } else {
                categoryGoods = goodsService.queryByCategory(l2List, 0, SystemConfig.getCatlogMoreLimit(),isShown);
            }

            Map<String, Object> catGoods = new HashMap<>();
            catGoods.put("id", catL1.getId());
            catGoods.put("name", catL1.getName());
            catGoods.put("goodsList", categoryGoods);
            categoryList.add(catGoods);
        }
        return categoryList;
    }

    /**
     * 商城介绍信息
     * @return 商城介绍信息
     */
    @GetMapping("/about")
    @ApiOperation("商城介绍信息")
    public Object about() {
        Map<String, Object> about = new HashMap<>();
        about.put("name", SystemConfig.getMallName());
        about.put("address", SystemConfig.getMallAddress());
        about.put("phone", SystemConfig.getMallPhone());
        about.put("qq", SystemConfig.getMallQQ());
        about.put("longitude", SystemConfig.getMallLongitude());
        about.put("latitude", SystemConfig.getMallLatitude());
        return ResponseUtil.ok(about);
    }
}