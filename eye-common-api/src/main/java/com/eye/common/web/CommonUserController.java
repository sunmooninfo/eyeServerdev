package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.service.EyeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 */
@RestController
@RequestMapping("/common/user")
@Validated
@Api(description = "用户服务")
public class CommonUserController {
    private final Log logger = LogFactory.getLog(CommonUserController.class);

    @Autowired
    private EyeOrderService orderService;

    /**
     * 用户个人页面数据
     * <p>
     * 目前是用户订单统计信息
     *
     * @param userId 用户ID
     * @return 用户个人页面数据
     */
    @GetMapping("index")
    @ApiOperation("用户订单统计信息")
    @ApiImplicitParam(name="userId",value = "用户ID",required=true,paramType="path",dataType="int")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("order", orderService.orderInfo(userId));
        return ResponseUtil.ok(data);
    }

}