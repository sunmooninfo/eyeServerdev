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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeIntegral;
import com.eye.db.service.EyeIntegralService;

import java.util.List;

@Api(description = "会员积分管理")
@RestController
@RequestMapping("/admin/integral")
@Validated
//会员积分权限此项目暂时没有用到，先注销，后期用到再进行修改。
public class AdminIntegralController {
    private final Log logger = LogFactory.getLog(AdminIntegralController.class);

    @Autowired
    private EyeIntegralService integralService;


    @ApiOperation(value = "会员积分查询")
    @RequiresPermissions("admin:integralGoods:list")
//    @RequiresPermissionsDesc(menu = {"会员管理", "会员积分"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="integration",value = "会员积分",required=false,paramType="path",dataType="int")
    })
    @GetMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        List<EyeIntegral> EyeIntegralList = integralService.querySelective(page,limit,sort,order);
        return ResponseUtil.okList(EyeIntegralList);
    }
}
