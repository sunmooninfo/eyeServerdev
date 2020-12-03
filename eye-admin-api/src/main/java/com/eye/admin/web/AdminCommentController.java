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
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeComment;
import com.eye.db.service.EyeCommentService;

import java.util.List;

@Api(description = "商品评论")
@RestController
@RequestMapping("/admin/comment")
@Validated
public class AdminCommentController {
    private final Log logger = LogFactory.getLog(AdminCommentController.class);

    @Autowired
    private EyeCommentService commentService;

    @ApiOperation(value = "商品评论")
    @RequiresPermissions("admin:comment:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品评论"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="valueId",value = "如果type=0，则是商品ID；如果type=1，则是专题ID",
                    required=true,paramType="path",dataType="int")
    })
    @GetMapping("/list")
    public Object list(String userId, String valueId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeComment> commentList = commentService.querySelective(userId, valueId, page, limit, sort, order);
        return ResponseUtil.okList(commentList);
    }

    @ApiOperation(value = "商品评论删除")
    @RequiresPermissions("admin:comment:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品评论"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeComment comment) {
        Integer id = comment.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        commentService.deleteById(id);
        return ResponseUtil.ok();
    }

}
