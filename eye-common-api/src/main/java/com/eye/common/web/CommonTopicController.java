package com.eye.common.web;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeTopic;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeTopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专题服务
 */
@RestController
@RequestMapping("/common/topic")
@Validated
@Api(description = "专题服务")
public class CommonTopicController {
    private final Log logger = LogFactory.getLog(CommonTopicController.class);

    @Autowired
    private EyeTopicService topicService;
    @Autowired
    private EyeGoodsService goodsService;

    /**
     * 专题列表
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return 专题列表
     */
    @GetMapping("list")
    @ApiOperation("专题列表")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeTopic> topicList = topicService.queryList(page, limit, sort, order);
        return ResponseUtil.okList(topicList);
    }

    /**
     * 专题详情
     *
     * @param id 专题ID
     * @return 专题详情
     */
    @GetMapping("detail")
    @ApiOperation("专题详情")
    @ApiImplicitParam(name="id",value = "专题ID",required=true,paramType="path",dataType="int")
    public Object detail(@NotNull Integer id) {
        EyeTopic topic = topicService.findById(id);
        List<EyeGoods> goods = new ArrayList<>();
        for (Integer i : topic.getGoods()) {
            EyeGoods good = goodsService.findByIdVO(i);
            if (null != good)
                goods.add(good);
        }

        Map<String, Object> entity = new HashMap<String, Object>();
        entity.put("topic", topic);
        entity.put("goods", goods);
        return ResponseUtil.ok(entity);
    }

    /**
     * 相关专题
     *
     * @param id 专题ID
     * @return 相关专题
     */
    @GetMapping("related")
    @ApiOperation("相关专题")
    @ApiImplicitParam(name="id",value = "专题ID",required=true,paramType="path",dataType="int")
    public Object related(@NotNull Integer id) {
        List<EyeTopic> topicRelatedList = topicService.queryRelatedList(id, 0, 4);
        return ResponseUtil.okList(topicRelatedList);
    }
}