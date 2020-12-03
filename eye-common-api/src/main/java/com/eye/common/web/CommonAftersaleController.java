package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.util.CommonResponseCode;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeAftersale;
import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.service.EyeAftersaleService;
import com.eye.db.service.EyeOrderGoodsService;
import com.eye.db.service.EyeOrderService;
import com.eye.db.util.AftersaleConstant;
import com.eye.db.util.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 售后服务
 *
 * 目前只支持订单整体售后，不支持订单商品单个售后
 *
 * 一个订单只能有一个售后记录
 */
@RestController
@RequestMapping("/common/aftersale")
@Validated
@Api(description = "售后服务")
public class CommonAftersaleController {
    private final Log logger = LogFactory.getLog(CommonAftersaleController.class);

    @Autowired
    private EyeAftersaleService aftersaleService;
    @Autowired
    private EyeOrderService orderService;
    @Autowired
    private EyeOrderGoodsService orderGoodsService;

    /**
     * 售后列表
     *
     * @param userId   用户ID
     * @param status   状态类型，如果是空则是全部
     * @param page     分页页数
     * @param limit    分页大小
     * @param sort     排序字段
     * @param order    排序方式
     * @return 售后列表
     */
    @GetMapping("list")
    @ApiOperation("用户售后列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="status",value = "售后状态",required=true,paramType="path",dataType="short")
    })
    public Object list(@LoginUser Integer userId,
                       @RequestParam Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<EyeAftersale> aftersaleList = aftersaleService.queryList(userId, status, page, limit, sort, order);

        List<Map<String, Object>> aftersaleVoList = new ArrayList<>(aftersaleList.size());
        for (EyeAftersale aftersale : aftersaleList) {
            List<EyeOrderGoods> orderGoodsList = orderGoodsService.queryByOid(aftersale.getOrderId());

            Map<String, Object> aftersaleVo = new HashMap<>();
            aftersaleVo.put("aftersale", aftersale);
            aftersaleVo.put("goodsList", orderGoodsList);

            aftersaleVoList.add(aftersaleVo);
        }

        return ResponseUtil.okList(aftersaleVoList, aftersaleList);
    }

    /**
     * 售后详情
     *
     * @param orderId 订单ID
     * @return 售后详情
     */
    @GetMapping("detail")
    @ApiOperation("用户售后详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="orderId",value = "订单id",required=true,paramType="path",dataType="int")
    })
    public Object detail(@LoginUser Integer userId, @NotNull Integer orderId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        EyeOrder order = orderService.findById(userId, orderId);
        if (order == null){
            return ResponseUtil.badArgumentValue();
        }
        List<EyeOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
        EyeAftersale aftersale = aftersaleService.findByOrderId(userId, orderId);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("aftersale", aftersale);
        data.put("order", order);
        data.put("orderGoods", orderGoodsList);
        return ResponseUtil.ok(data);
    }

    /**
     * 申请售后
     *
     * @param userId   用户ID
     * @param aftersale 用户售后信息
     * @return 操作结果
     */
    @PostMapping("submit")
    @ApiOperation("用户申请售后")
    @Transactional
    public Object submit(@LoginUser Integer userId, @RequestBody EyeAftersale aftersale) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(aftersale);
        if (error != null) {
            return error;
        }
        // 进一步验证
        Integer orderId = aftersale.getOrderId();
        if(orderId == null){
            return ResponseUtil.badArgument();
        }
        EyeOrder order = orderService.findById(userId, orderId);
        if(order == null){
            return ResponseUtil.badArgumentValue();
        }

        // 订单必须完成才能进入售后流程。
        if(!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)){
            return ResponseUtil.fail(CommonResponseCode.AFTERSALE_UNALLOWED, "不能申请售后");
        }
        BigDecimal amount = order.getActualPrice().subtract(order.getFreightPrice());
        if(aftersale.getAmount().compareTo(amount) > 0){
            return ResponseUtil.fail(CommonResponseCode.AFTERSALE_INVALID_AMOUNT, "退款金额不正确");
        }
        Short afterStatus = order.getAftersaleStatus();
        if(afterStatus.equals(AftersaleConstant.STATUS_RECEPT) || afterStatus.equals(AftersaleConstant.STATUS_REFUND)){
            return ResponseUtil.fail(CommonResponseCode.AFTERSALE_INVALID_AMOUNT, "已申请售后");
        }

        // 如果有旧的售后记录则删除（例如用户已取消，管理员拒绝）
        aftersaleService.deleteByOrderId(userId, orderId);

        aftersale.setStatus(AftersaleConstant.STATUS_REQUEST);
        aftersale.setAftersaleSn(aftersaleService.generateAftersaleSn(userId));
        aftersale.setUserId(userId);
        aftersaleService.add(aftersale);

        // 订单的aftersale_status和售后记录的status是一致的。
        orderService.updateAftersaleStatus(orderId, AftersaleConstant.STATUS_REQUEST);
        return ResponseUtil.ok();
    }

    /**
     * 取消售后
     *
     * 如果管理员还没有审核，用户可以取消自己的售后申请
     *
     * @param userId   用户ID
     * @param aftersale 用户售后信息
     * @return 操作结果
     */
    @PostMapping("cancel")
    @ApiOperation("用户取消售后")
    @Transactional
    public Object cancel(@LoginUser Integer userId, @RequestBody EyeAftersale aftersale) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer id = aftersale.getId();
        if(id == null){
            return ResponseUtil.badArgument();
        }
        EyeAftersale aftersaleOne = aftersaleService.findById(userId, id);
        if(aftersaleOne == null){
            return ResponseUtil.badArgument();
        }

        Integer orderId = aftersaleOne.getOrderId();
        EyeOrder order = orderService.findById(userId, orderId);
        if(!order.getUserId().equals(userId)){
            return ResponseUtil.badArgumentValue();
        }

        // 订单必须完成才能进入售后流程。
        if(!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)){
            return ResponseUtil.fail(CommonResponseCode.AFTERSALE_UNALLOWED, "不支持售后");
        }
        Short afterStatus = order.getAftersaleStatus();
        if(!afterStatus.equals(AftersaleConstant.STATUS_REQUEST)){
            return ResponseUtil.fail(CommonResponseCode.AFTERSALE_INVALID_STATUS, "不能取消售后");
        }

        aftersale.setStatus(AftersaleConstant.STATUS_CANCEL);
        aftersale.setUserId(userId);
        aftersaleService.updateById(aftersale);

        // 订单的aftersale_status和售后记录的status是一致的。
        orderService.updateAftersaleStatus(orderId, AftersaleConstant.STATUS_CANCEL);
        return ResponseUtil.ok();
    }

    private Object validate(EyeAftersale aftersale) {
        Short type = aftersale.getType();
        if (type == null) {
            return ResponseUtil.badArgument();
        }
        BigDecimal amount = aftersale.getAmount();
        if (amount == null) {
            return ResponseUtil.badArgument();
        }
        String reason = aftersale.getReason();
        if (StringUtils.isEmpty(reason)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}