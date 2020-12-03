package com.eye.admin.web;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyeToolAccount;
import com.eye.db.service.EyeToolAccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "附带工具")
@RestController
@RequestMapping("/admin/tool")
@Validated
public class AdminToolAccountController {

    private final Log logger = LogFactory.getLog(AdminToolAccountController.class);

    @Autowired
    private EyeToolAccountService accountService;

    @ApiOperation(value = "附带工具")
    @RequiresPermissions("admin:tool:list")
    @RequiresPermissionsDesc(menu = {"附带工具", "高仿外站"}, button = "查询")
    @GetMapping("/list")
    public Object list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "add_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order){

        List<EyeToolAccount> list = accountService.querySelective(page,limit,sort,order);
        int total = accountService.count();
        EyeAdmin admin = (EyeAdmin) SecurityUtils.getSubject().getPrincipal();

        Map<String,Object> data = new HashMap<>();
        data.put("name",admin.getUsername());
        data.put("mobile",admin.getManagerMobile());
        data.put("list",list);
        if (total != 0 && limit != 0) {
            double ceil = (double) total / (double)limit;
            data.put("pages", (int)Math.ceil(ceil));
        } else {
            data.put("pages", 0);
        }

        data.put("total", total);
        data.put("page", page);
        data.put("limit", limit);
        return ResponseUtil.ok(data);
    }
}
