package com.eye.common.web;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeIssue;
import com.eye.db.service.EyeIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/common/issue")
@Validated
@Api(description = "帮助中心服务")
public class CommonIssueController {
    private final Log logger = LogFactory.getLog(CommonIssueController.class);

    @Autowired
    private EyeIssueService issueService;

    /**
     * 帮助中心
     */
    @GetMapping("/list")
    @ApiOperation("帮助中心列表")
    @ApiImplicitParam(name="question",value = "问题标题",required=true,paramType="path",dataType="string")
    public Object list(String question,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeIssue> issueList = issueService.querySelective(question, page, size, sort, order);
        return ResponseUtil.okList(issueList);
    }

}
