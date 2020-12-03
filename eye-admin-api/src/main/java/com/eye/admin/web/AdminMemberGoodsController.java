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
import com.eye.admin.task.MemberGoodsExpiredTask;
import com.eye.core.task.TaskService;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeMemberGoods;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeMemberGoodsService;
import com.eye.db.util.MemberConstant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Api(description = "超级会员商品管理")
@RestController
@RequestMapping("/admin/member/goods")
@Validated
public class AdminMemberGoodsController {

    private final Log logger = LogFactory.getLog(AdminMemberGoodsController.class);

    @Autowired
    private EyeMemberGoodsService memberGoodsService;

    @Autowired
    private EyeGoodsService goodsService;

    @Autowired
    private TaskService taskService;


    @ApiOperation(value = "超级会员商品查询")
    @RequiresPermissions("admin:membergoods:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "超级会员商品"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodsId",value = "商品表的商品id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="goodsName",value = "商品名称",required=false,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(Integer goodsId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){

        List<EyeMemberGoods> memberGoodsList = memberGoodsService.querySelective(goodsId, page, limit, sort, order);
        return ResponseUtil.okList(memberGoodsList);
    }

    private Object validate(EyeMemberGoods memberGoods) {
        Integer goodsId = memberGoods.getGoodsId();
        if (goodsId == null) {
            return ResponseUtil.badArgument();
        }
        Integer discount = memberGoods.getDiscount();
        if (discount == null) {
            return ResponseUtil.badArgument();
        }
        LocalDateTime expireTime = memberGoods.getExpireTime();
        if (expireTime == null) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @ApiOperation(value = "超级会员商品添加")
    @RequiresPermissions("admin:membergoods:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "超级会员商品"}, button = "添加")
    @PostMapping("/create")
    @Transactional
    public Object create(@RequestBody EyeMemberGoods memberGoods){

        Object error = validate(memberGoods);
        if (error != null) {
            return error;
        }

        Integer goodsId = memberGoods.getGoodsId();
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null) {
            return ResponseUtil.fail(MemberConstant.MEMBER_GOODS_UNKNOWN, "VIP商品不存在");
        }
        if(memberGoodsService.countByGoodsId(goodsId) > 0){
            return ResponseUtil.fail(MemberConstant.MEMBER_GOODS_EXISTED, "VIP商品已经存在");
        }

        memberGoods.setGoodsName(goods.getName());
        memberGoods.setPicUrl(goods.getPicUrl());
        memberGoods.setIsShown(goods.getIsShown());
        memberGoods.setRetailPrice(goods.getRetailPrice());
        memberGoods.setDiscountPrice(goods.getRetailPrice().multiply((new BigDecimal((double)memberGoods.getDiscount()/100))));
        memberGoods.setStatus(MemberConstant.GOODS_STATUS_ON);
        memberGoodsService.createRules(memberGoods);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = memberGoods.getExpireTime();
        long delay = ChronoUnit.MILLIS.between(now, expire);
        // 会员商品过期任务
        taskService.addTask(new MemberGoodsExpiredTask(memberGoods.getId(), delay));

        return ResponseUtil.ok(memberGoods);
    }

    @ApiOperation(value = "超级会员商品编辑")
    @RequiresPermissions("admin:membergoods:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "超级会员商品"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeMemberGoods memberGoods) {
        Object error = validate(memberGoods);
        if (error != null) {
            return error;
        }

        //根据会员商品id获取会员商品信息
        EyeMemberGoods EyeMemberGoods = memberGoodsService.findById(memberGoods.getId());
        if(EyeMemberGoods == null){
            return ResponseUtil.badArgumentValue();
        }
        if(!EyeMemberGoods.getStatus().equals(MemberConstant.GOODS_STATUS_ON)){
            return ResponseUtil.fail(MemberConstant.MEMBER_GOODS_OFFLINE, "VIP商品已经下线");
        }

        //根据商品id获取商品信息
        Integer goodsId = memberGoods.getGoodsId();
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null) {
            return ResponseUtil.badArgumentValue();
        }

        memberGoods.setGoodsName(goods.getName());
        memberGoods.setPicUrl(goods.getPicUrl());
        memberGoods.setIsShown(goods.getIsShown());
        memberGoods.setRetailPrice(goods.getRetailPrice());
        memberGoods.setDiscountPrice(goods.getRetailPrice().multiply((new BigDecimal((double)memberGoods.getDiscount()/100))));


        //修改
        if (memberGoodsService.updateById(memberGoods) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        //取消延迟任务
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = EyeMemberGoods.getExpireTime();
        long delay = ChronoUnit.MILLIS.between(now, expire);
        taskService.removeTask(new MemberGoodsExpiredTask(memberGoods.getId(), delay));

        //添加延迟任务
        long between = ChronoUnit.MILLIS.between(now, memberGoods.getExpireTime());
        taskService.addTask(new MemberGoodsExpiredTask(memberGoods.getId(), between));


        return ResponseUtil.ok();
    }

    @ApiOperation(value = "超级会员商品删除")
    @RequiresPermissions("admin:membergoods:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "超级会员商品"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeMemberGoods memberGoods) {
        Integer id = memberGoods.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        memberGoodsService.delete(id);
        return ResponseUtil.ok();
    }

}
