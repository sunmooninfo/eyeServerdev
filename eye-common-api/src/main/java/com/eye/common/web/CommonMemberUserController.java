package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeMemberUser;
import com.eye.db.service.EyeMemberPackageService;
import com.eye.db.service.EyeMemberUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/common/member/user")
@Validated
@Api(description = "用户会员时间服务")
public class CommonMemberUserController {
    private final Log logger = LogFactory.getLog(CommonMemberUserController.class);

    @Autowired
    private EyeMemberUserService memberUserService;

    @Autowired
    private EyeMemberPackageService packageService;

    /**
     * 个人会员列表
     *
     * @param userId
     * @param status
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/mylist")
    @ApiOperation("个人会员时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="status",value = "会员时间状态",required=true,paramType="path",dataType="short")

    })
    public Object mylist(@LoginUser Integer userId,
                         Short status,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit,
                         @Sort @RequestParam(defaultValue = "add_time") String sort,
                         @Order @RequestParam(defaultValue = "desc") String order) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<EyeMemberUser> memberUsers = memberUserService.queryList(userId, status, page, limit, sort, order);

        return ResponseUtil.okList(memberUsers);
    }
}
