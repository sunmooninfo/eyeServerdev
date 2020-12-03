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

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeIssue;
import com.eye.db.service.EyeIssueService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "通用问题管理")
@RestController
@RequestMapping("/admin/issue")
@Validated
public class AdminIssueController {
    private final Log logger = LogFactory.getLog(AdminIssueController.class);

    @Autowired
    private EyeIssueService issueService;

    @ApiOperation(value = "通用问题查询")
    @RequiresPermissions("admin:issue:list")
    @RequiresPermissionsDesc(menu = {"商城管理", "通用问题"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="question",value = "问题标题",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String question,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeIssue> issueList = issueService.querySelective(question, page, limit, sort, order);
        return ResponseUtil.okList(issueList);
    }

    private Object validate(EyeIssue issue) {
        String question = issue.getQuestion();
        if (StringUtils.isEmpty(question)) {
            return ResponseUtil.badArgument();
        }
        String answer = issue.getAnswer();
        if (StringUtils.isEmpty(answer)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "通用问题添加")
    @RequiresPermissions("admin:issue:create")
    @RequiresPermissionsDesc(menu = {"商城管理", "通用问题"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeIssue issue) {
        Object error = validate(issue);
        if (error != null) {
            return error;
        }
        issueService.add(issue);
        return ResponseUtil.ok(issue);
    }

    @ApiOperation(value = "通用问题详情")
    @RequiresPermissions("admin:issue:read")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "常见问题id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeIssue issue = issueService.findById(id);
        return ResponseUtil.ok(issue);
    }

    @ApiOperation(value = "通用问题编辑")
    @RequiresPermissions("admin:issue:update")
    @RequiresPermissionsDesc(menu = {"商城管理", "通用问题"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeIssue issue) {
        Object error = validate(issue);
        if (error != null) {
            return error;
        }
        if (issueService.updateById(issue) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok(issue);
    }

    @ApiOperation(value = "通用问题删除")
    @RequiresPermissions("admin:issue:delete")
    @RequiresPermissionsDesc(menu = {"商城管理", "通用问题"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeIssue issue) {
        Integer id = issue.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        issueService.deleteById(id);
        return ResponseUtil.ok();
    }

}
