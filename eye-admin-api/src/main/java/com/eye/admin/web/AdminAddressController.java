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
import com.eye.db.domain.EyeAddress;
import com.eye.db.service.EyeAddressService;
import com.eye.db.service.EyeRegionService;

import java.util.List;

@Api(description = "收货地址管理")
@RestController
@RequestMapping("/admin/address")
@Validated
public class AdminAddressController {
    private final Log logger = LogFactory.getLog(AdminAddressController.class);

    @Autowired
    private EyeAddressService addressService;
    @Autowired
    private EyeRegionService regionService;

    @ApiOperation(value = "收货地址查询")
    @RequiresPermissions("admin:address:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "收货地址"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "收货人名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(Integer userId, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        String info  = "查询用户收获地址列表";
        logger.error(getClass()+"----------------------------"+info);
        List<EyeAddress> addressList = addressService.querySelective(userId, name, page, limit, sort, order);
        return ResponseUtil.okList(addressList);
    }
}


