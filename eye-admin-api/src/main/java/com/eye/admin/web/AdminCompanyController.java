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

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeCompany;
import com.eye.db.service.EyeCompanyService;

import java.util.List;


/**
 * @author edc
 * @date 2020/7/22 13:51
 * @description
 */
@Api(description = "公司管理")
@RestController
@RequestMapping("/admin/company")
@Validated
//公司管理权限此项目暂时没有用到，先注销，后期用到再进行修改。
public class AdminCompanyController{

    private final Log logger = LogFactory.getLog(AdminBrandController.class);
    @Autowired
    private EyeCompanyService companyService;

    private Object validate(EyeCompany company){
        String name = company.getName();
        if (name == null){
            return ResponseUtil.badArgument();
        }
        String logo = company.getLogo();
        if (logo == null){
            return ResponseUtil.badArgument();
        }
        String slogan = company.getSlogan();
        if (slogan == null){
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "公司管理添加")
    @RequiresPermissions("admin:company:create")
//    @RequiresPermissionsDesc(menu = {"公司管理", "公司信息配置"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeCompany company) {
        Object error = validate(company);
        if (error != null) {
            return error;
        }
        companyService.add(company);
        return ResponseUtil.ok(company);
    }

    @ApiOperation(value = "公司管理删除")
    @RequiresPermissions("admin:company:delete")
//    @RequiresPermissionsDesc(menu = {"公司管理", "公司信息配置"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeCompany company){

        Integer id = company.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        companyService.deleteById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "公司管理编辑")
    @RequiresPermissions("admin:company:update")
//    @RequiresPermissionsDesc(menu = {"公司管理", "公司信息配置"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeCompany company) {

        if (companyService.updateById(company) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(company);
    }

    @ApiOperation(value = "公司管理查询")
    @RequiresPermissions("admin:company:list")
//    @RequiresPermissionsDesc(menu = {"公司管理", "公司信息配置"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "公司名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(@RequestParam(required = false) String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeCompany> querymessage = companyService.queryCompanyMessage(name,page,limit,sort,order);
        return ResponseUtil.okList(querymessage);
    }
}
