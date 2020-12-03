package com.eye.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.service.AdminTopicCategoryService;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeTopic;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeTopicService;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "专题管理")
@RestController
@RequestMapping("/admin/topic")
@Validated
//专题管理已改为文章管理，此方法权限不在自动生成。
public class AdminTopicController {
    private final Log logger = LogFactory.getLog(AdminTopicController.class);

    @Autowired
    private EyeTopicService topicService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private AdminTopicCategoryService topicCategoryService;

    @ApiOperation(value = "专题管理查询")
    @RequiresPermissions("admin:topic:list")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="title",value = "专题标题",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="subtitle",value = "专题子标题",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String title, String subtitle,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort(accepts = {"id", "add_time", "price"}) @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeTopic> topicList = topicService.querySelective(title, subtitle, page, limit, sort, order);
        return ResponseUtil.okList(topicList);
    }

    private Object validate(EyeTopic topic) {
        String title = topic.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ResponseUtil.badArgument();
        }
        String content = topic.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        BigDecimal price = topic.getPrice();
        if (price == null) {
            return ResponseUtil.badArgument();
        }
        Integer[] topicCategoryIds = topic.getTopicCategoryIds();
        if(topicCategoryIds == null){
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "专题管理添加")
    @RequiresPermissions("admin:topic:create")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeTopic topic) {
        Object error = validate(topic);
        if (error != null) {
            return error;
        }
        topicService.add(topic);
        return ResponseUtil.ok(topic);
    }

    @ApiOperation(value = "专题管理详情")
    @RequiresPermissions("admin:topic:read")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "专题id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeTopic topic = topicService.findById(id);
        Integer[] goodsIds = topic.getGoods();
        List<EyeGoods> goodsList = null;
        if (goodsIds == null || goodsIds.length == 0) {
            goodsList = new ArrayList<>();
        } else {
            goodsList = goodsService.queryByIds(goodsIds);
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put("topic", topic);
        data.put("goodsList", goodsList);
        return ResponseUtil.ok(data);
    }

    @ApiOperation(value = "专题管理编辑")
    @RequiresPermissions("admin:topic:update")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeTopic topic) {
        Object error = validate(topic);
        if (error != null) {
            return error;
        }
        if (topicService.updateById(topic) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(topic);
    }

    @ApiOperation(value = "专题管理删除")
    @RequiresPermissions("admin:topic:delete")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeTopic topic) {
        topicService.deleteById(topic.getId());
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "专题管理批量删除")
    @RequiresPermissions("admin:topic:batch-delete")
//    @RequiresPermissionsDesc(menu = {"推广管理", "专题管理"}, button = "批量删除")
    @PostMapping("/batch-delete")
    public Object batchDelete(@RequestBody String body) {
        List<Integer> ids = JacksonUtil.parseIntegerList(body, "ids");
        topicService.deleteByIds(ids);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "专题管理类目")
    @GetMapping("category")
    public Object category(){

        return topicCategoryService.category();
    }
}
