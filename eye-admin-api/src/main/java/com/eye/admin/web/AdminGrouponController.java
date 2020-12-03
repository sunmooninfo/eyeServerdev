package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.task.GrouponRuleExpiredTask;
import com.eye.admin.util.AdminResponseCode;
import com.eye.core.task.TaskService;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGroupon;
import com.eye.db.domain.EyeGrouponRules;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeGrouponRulesService;
import com.eye.db.service.EyeGrouponService;
import com.eye.db.util.GrouponConstant;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "团购管理")
@RestController
@RequestMapping("/admin/groupon")
@Validated
public class AdminGrouponController {
    private final Log logger = LogFactory.getLog(AdminGrouponController.class);

    @Autowired
    private EyeGrouponRulesService rulesService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeGrouponService grouponService;
    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "团购管理详情")
    @RequiresPermissions("admin:groupon:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "团购管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="grouponRuleId",value = "团购规则id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/listRecord")
    public Object listRecord(String grouponRuleId,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit,
                             @Sort @RequestParam(defaultValue = "add_time") String sort,
                             @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeGroupon> grouponList = grouponService.querySelective(grouponRuleId, page, limit, sort, order);

        List<Map<String, Object>> groupons = new ArrayList<>();
        for (EyeGroupon groupon : grouponList) {
            try {
                Map<String, Object> recordData = new HashMap<>();
                List<EyeGroupon> subGrouponList = grouponService.queryJoinRecord(groupon.getId());
                EyeGrouponRules rules = rulesService.findById(groupon.getRulesId());
                EyeGoods goods = goodsService.findById(rules.getGoodsId());

                recordData.put("groupon", groupon);
                recordData.put("subGroupons", subGrouponList);
                recordData.put("rules", rules);
                recordData.put("goods", goods);

                groupons.add(recordData);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return ResponseUtil.okList(groupons, grouponList);
    }

    @ApiOperation(value = "团购管理查询")
    @RequiresPermissions("admin:groupon:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "团购管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodsId",value = "商品id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/list")
    public Object list(String goodsId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeGrouponRules> rulesList = rulesService.querySelective(goodsId, page, limit, sort, order);
        return ResponseUtil.okList(rulesList);
    }

    private Object validate(EyeGrouponRules grouponRules) {
        Integer goodsId = grouponRules.getGoodsId();
        if (goodsId == null) {
            return ResponseUtil.badArgument();
        }
        BigDecimal discount = grouponRules.getDiscount();
        if (discount == null) {
            return ResponseUtil.badArgument();
        }
        Integer discountMember = grouponRules.getDiscountMember();
        if (discountMember == null) {
            return ResponseUtil.badArgument();
        }
        LocalDateTime expireTime = grouponRules.getExpireTime();
        if (expireTime == null) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @ApiOperation(value = "团购管理编辑")
    @RequiresPermissions("admin:groupon:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "团购管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeGrouponRules grouponRules) {
        Object error = validate(grouponRules);
        if (error != null) {
            return error;
        }

        EyeGrouponRules rules = rulesService.findById(grouponRules.getId());
        if(rules == null){
            return ResponseUtil.badArgumentValue();
        }
        if(!rules.getStatus().equals(GrouponConstant.RULE_STATUS_ON)){
            return ResponseUtil.fail(AdminResponseCode.GROUPON_GOODS_OFFLINE, "团购已经下线");
        }

        Integer goodsId = grouponRules.getGoodsId();
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null) {
            return ResponseUtil.badArgumentValue();
        }

        grouponRules.setGoodsName(goods.getName());
        grouponRules.setPicUrl(goods.getPicUrl());

        if (rulesService.updateById(grouponRules) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    @ApiOperation(value = "团购管理添加")
    @RequiresPermissions("admin:groupon:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "团购管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeGrouponRules grouponRules) {
        Object error = validate(grouponRules);
        if (error != null) {
            return error;
        }

        Integer goodsId = grouponRules.getGoodsId();
        EyeGoods goods = goodsService.findById(goodsId);
        if (goods == null) {
            return ResponseUtil.fail(AdminResponseCode.GROUPON_GOODS_UNKNOWN, "团购商品不存在");
        }
        if(rulesService.countByGoodsId(goodsId) > 0){
            return ResponseUtil.fail(AdminResponseCode.GROUPON_GOODS_EXISTED, "团购商品已经存在");
        }

        grouponRules.setGoodsName(goods.getName());
        grouponRules.setPicUrl(goods.getPicUrl());
        grouponRules.setStatus(GrouponConstant.RULE_STATUS_ON);
        rulesService.createRules(grouponRules);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = grouponRules.getExpireTime();
        long delay = ChronoUnit.MILLIS.between(now, expire);
        // 团购过期任务
        taskService.addTask(new GrouponRuleExpiredTask(grouponRules.getId(), delay));

        return ResponseUtil.ok(grouponRules);
    }

    @ApiOperation(value = "团购管理删除")
    @RequiresPermissions("admin:groupon:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "团购管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeGrouponRules grouponRules) {
        Integer id = grouponRules.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        rulesService.delete(id);
        return ResponseUtil.ok();
    }
}
