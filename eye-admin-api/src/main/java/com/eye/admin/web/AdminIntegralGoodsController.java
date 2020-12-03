package com.eye.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.task.IntegralGoodsExpiredTask;
import com.eye.core.task.TaskService;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeIntegralGoods;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeIntegralGoodsService;
import com.eye.db.util.MemberConstant;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Api(description = "积分商品管理")
@RestController
@RequestMapping("/admin/integral/goods")
@Validated
public class AdminIntegralGoodsController {
    private final Log logger = LogFactory.getLog(AdminIntegralGoodsController.class);

    @Autowired
    private EyeIntegralGoodsService integralGoodsService;

    @Autowired
    private EyeGoodsService goodsService;

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "积分商品查询")
    @RequiresPermissions("admin:integralgoods:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "积分商品"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodsId",value = "商品表的商品id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="goodsName",value = "商品名称",required=false,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(Integer goodsId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        List<EyeIntegralGoods> EyeIntegralGoodsList = integralGoodsService.querySelective(goodsId,page,limit,sort,order);
        return ResponseUtil.okList(EyeIntegralGoodsList);
    }

    private Object validate(EyeIntegralGoods EyeIntegralGoods){
        Integer goodsId = EyeIntegralGoods.getGoodsId();
        if (goodsId == null) {
            return ResponseUtil.badArgument();
        }

        Integer integration = EyeIntegralGoods.getIntegration();
        if (integration == null){
            return ResponseUtil.badArgument();
        }

        LocalDateTime expireTime = EyeIntegralGoods.getExpireTime();
        if (expireTime == null) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @ApiOperation(value = "积分商品添加")
    @RequiresPermissions("admin:integralgoods:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "积分商品"}, button = "添加")
    @PostMapping("/create")
    @Transactional
    public Object create(@RequestBody EyeIntegralGoods EyeIntegralGoods){

        Object error = validate(EyeIntegralGoods);
        if (error != null) {
            return error;
        }
        Integer goodsId = EyeIntegralGoods.getGoodsId();

        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null) {
            return ResponseUtil.fail(MemberConstant.INTEGRAL_GOODS_UNKNOWN, "商品不存在");
        }

        if (integralGoodsService.countByGoodsId(goodsId) > 0){
            return ResponseUtil.fail(MemberConstant.INTEGRAL_GOODS_EXISTED, "积分商品已经存在");
        }

        EyeIntegralGoods.setGoodsName(goods.getName());
        EyeIntegralGoods.setPicUrl(goods.getPicUrl());
        EyeIntegralGoods.setIsShown(goods.getIsShown());
        EyeIntegralGoods.setBrief(goods.getBrief());
        EyeIntegralGoods.setStatus(MemberConstant.INTEGRAL_GOODS_STATUS_ON);
        integralGoodsService.create(EyeIntegralGoods);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = EyeIntegralGoods.getExpireTime();
        long delay = ChronoUnit.MILLIS.between(now, expire);
        taskService.addTask(new IntegralGoodsExpiredTask(EyeIntegralGoods.getId(),delay));
        return ResponseUtil.ok(EyeIntegralGoods);
    }

    @ApiOperation(value = "积分商品编辑")
    @RequiresPermissions("admin:integralgoods:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "积分商品"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeIntegralGoods EyeIntegralGoods){
        Object error = validate(EyeIntegralGoods);
        if (error != null) {
            return error;
        }

        //根据积分商品id获取积分商品信息
        EyeIntegralGoods integral = integralGoodsService.findById(EyeIntegralGoods.getId());
        if (integral == null){
            return ResponseUtil.badArgumentValue();
        }
        if (!integral.getStatus().equals(MemberConstant.INTEGRAL_GOODS_STATUS_ON)){
            return ResponseUtil.fail(MemberConstant.INTEGRAL_GOODS_OFFLINE,"积分商品已下线");
        }

        //根据商品id获取商品信息
        Integer goodsId = EyeIntegralGoods.getGoodsId();
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null) {
            return ResponseUtil.badArgumentValue();
        }
        EyeIntegralGoods.setGoodsName(goods.getName());
        EyeIntegralGoods.setPicUrl(goods.getPicUrl());
        EyeIntegralGoods.setIsShown(goods.getIsShown());
        EyeIntegralGoods integralGoods = integralGoodsService.findById(EyeIntegralGoods.getId());

        //修改
        if (integralGoodsService.updateById(EyeIntegralGoods) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        //取消延迟队列
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = integralGoods.getExpireTime();
        long delay = ChronoUnit.MILLIS.between(now, expire);
        taskService.removeTask(new IntegralGoodsExpiredTask(EyeIntegralGoods.getId(),delay));


        long between = ChronoUnit.MILLIS.between(now, EyeIntegralGoods.getExpireTime());
        taskService.addTask(new IntegralGoodsExpiredTask(EyeIntegralGoods.getId(),between));



        return ResponseUtil.ok();
    }

    @ApiOperation(value = "积分商品删除")
    @RequiresPermissions("admin:integralgoods:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "积分商品"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeIntegralGoods EyeIntegralGoods){
        Integer id = EyeIntegralGoods.getId();
        if (id == null){
            return ResponseUtil.badArgument();
        }

        integralGoodsService.delete(id);
        return ResponseUtil.ok();
    }

}
