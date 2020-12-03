package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.dto.UserInfo;
import com.eye.common.service.UserInfoService;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeComment;
import com.eye.db.service.EyeCommentService;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeTopicService;
import com.eye.db.service.EyeUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户评论服务
 */
@RestController
@RequestMapping("/common/comment")
@Validated
@Api(description = "用户评论服务")
public class CommonCommentController {
    private final Log logger = LogFactory.getLog(CommonCommentController.class);

    @Autowired
    private EyeCommentService commentService;
    @Autowired
    private EyeUserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeTopicService topicService;

    private Object validate(EyeComment comment) {
        String content = comment.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }

        Short star = comment.getStar();
        if (star == null) {
            return ResponseUtil.badArgument();
        }
        if (star < 0 || star > 5) {
            return ResponseUtil.badArgumentValue();
        }

        Byte type = comment.getType();
        Integer valueId = comment.getValueId();
        if (type == null || valueId == null) {
            return ResponseUtil.badArgument();
        }
        if (type == 0) {
            if (goodsService.findById(valueId) == null) {
                return ResponseUtil.badArgumentValue();
            }
        } else if (type == 1) {
            if (topicService.findById(valueId) == null) {
                return ResponseUtil.badArgumentValue();
            }
        } else {
            return ResponseUtil.badArgumentValue();
        }
        Boolean hasPicture = comment.getHasPicture();
        if (hasPicture == null || !hasPicture) {
            comment.setPicUrls(new String[0]);
        }
        return null;
    }

    /**
     * 发表评论
     *
     * @param userId  用户ID
     * @param comment 评论内容
     * @return 发表评论操作结果
     */
    @PostMapping("post")
    @ApiOperation("用户发表评论")
    public Object post(@LoginUser Integer userId, @RequestBody EyeComment comment) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(comment);
        if (error != null) {
            return error;
        }

        comment.setUserId(userId);
        commentService.save(comment);
        return ResponseUtil.ok(comment);
    }

    /**
     * 评论数量
     *
     * @param type    类型ID。 如果是0，则查询商品评论；如果是1，则查询专题评论。
     * @param valueId 商品或专题ID。如果type是0，则是商品ID；如果type是1，则是专题ID。
     * @return 评论数量
     */
    @GetMapping("count")
    @ApiOperation("评论数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value ="类型id",required=true,paramType = "path",dataType="byte"),
            @ApiImplicitParam(name="valueId",value ="商品或专题id",required=true,paramType = "path",dataType="int")
    })
    public Object count(@NotNull Byte type, @NotNull Integer valueId) {
        int allCount = commentService.count(type, valueId, 0);
        int hasPicCount = commentService.count(type, valueId, 1);
        Map<String, Object> entity = new HashMap<String, Object>();
        entity.put("allCount", allCount);
        entity.put("hasPicCount", hasPicCount);
        return ResponseUtil.ok(entity);
    }

    /**
     * 评论列表
     *
     * @param type     类型ID。 如果是0，则查询商品评论；如果是1，则查询专题评论。
     * @param valueId  商品或专题ID。如果type是0，则是商品ID；如果type是1，则是专题ID。
     * @param showType 显示类型。如果是0，则查询全部；如果是1，则查询有图片的评论。
     * @param page     分页页数
     * @param limit     分页大小
     * @return 评论列表
     */
    @GetMapping("list")
    @ApiOperation("评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value ="类型id",required=true,paramType = "path",dataType="byte"),
            @ApiImplicitParam(name="valueId",value ="商品或专题id",required=true,paramType = "path",dataType="int"),
            @ApiImplicitParam(name="showType",value ="显示类型",required=true,paramType = "path",dataType="int")
    })
    public Object list(@NotNull Byte type,
                       @NotNull Integer valueId,
                       @NotNull Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {
        List<EyeComment> commentList = commentService.query(type, valueId, showType, page, limit);

        List<Map<String, Object>> commentVoList = new ArrayList<>(commentList.size());
        for (EyeComment comment : commentList) {
            Map<String, Object> commentVo = new HashMap<>();
            commentVo.put("addTime", comment.getAddTime());
            commentVo.put("content", comment.getContent());
            commentVo.put("adminContent", comment.getAdminContent());
            commentVo.put("picList", comment.getPicUrls());

            UserInfo userInfo = userInfoService.getInfo(comment.getUserId());
            commentVo.put("userInfo", userInfo);

            commentVoList.add(commentVo);
        }
        return ResponseUtil.okList(commentVoList, commentList);
    }
}