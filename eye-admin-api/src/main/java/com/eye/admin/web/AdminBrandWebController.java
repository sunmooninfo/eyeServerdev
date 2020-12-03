package com.eye.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.db.domain.EyeBrandWeb;
import com.eye.db.service.EyeBrandWebMerService;
import com.eye.db.service.EyeBrandWebService;

import javax.validation.constraints.NotNull;
import java.util.*;
@Api(description = "品牌管理")
@RestController
@RequestMapping("/admin/brandx")
@Validated
//此权限暂时未用到，先注释，后期用到再做修改
public class AdminBrandWebController {
    private final Log logger = LogFactory.getLog(AdminBrandWebController.class);

    @Autowired
    private EyeBrandWebService brandService;
    @Autowired
    private EyeBrandWebMerService brandWebMerService;


    @ApiOperation(value = "品牌管理查询")
    @RequiresPermissions("admin:brand:list")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "品牌名",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="company",value = "公司名",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(Integer userId, String name, String company,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeBrandWeb> brandList = brandService.querylist(userId, name, company, page, limit, order);
        return ResponseUtil.okList(brandList);
    }

    private Object validate(EyeBrandWeb brand) {
        String name = brand.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        String company = brand.getCompany();
        if (company == null) {
            return ResponseUtil.badArgument();
        }

        String representative = brand.getRepresentative();
        if (representative == null) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    //添加
    @ApiOperation(value = "品牌管理添加")
    @RequiresPermissions("admin:brandx:add")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "添加")
    @PostMapping("/add")
    public Object add(@RequestBody EyeBrandWeb brand) {

        Object error = validate(brand);
        if (error != null) {
            return error;
        }

        String name = brand.getName();
        if (brandService.checkExistByName(name)){
            return ResponseUtil.fail(811,"品牌名已存在");
        }

        brandService.add(brand);
        return ResponseUtil.ok();
    }

    //修改
    @ApiOperation(value = "品牌管理修改")
    @RequiresPermissions("admin:brandx:update")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "修改")
    @PostMapping("/update")
    public Object update(@RequestBody EyeBrandWeb brand){
        Object error = validate(brand);
        if (error != null) {
            return error;
        }

        brandService.update(brand);

        return ResponseUtil.ok();
    }

    //删除
    @ApiOperation(value = "品牌管理删除")
    @RequiresPermissions("admin:brandx:delete")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "删除")
    @PostMapping("/delete")
    @Transactional
    public Object delete(@RequestBody EyeBrandWeb brand){
        Integer id = brand.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        brandService.delete(id);
        brandWebMerService.deleteByBrandId(brand.getId());

        return ResponseUtil.ok();
    }

    @ApiOperation(value = "品牌管理详情")
    @RequiresPermissions("admin:brandx:detail")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id){
        EyeBrandWeb brand = brandService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @ApiOperation(value = "品牌管理查询名字")
    @RequiresPermissions("admin:brandx:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "品牌名",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/name")
    public Object name(){
        List<EyeBrandWeb> list = brandService.queryName();
        List<Map<String, Object>> data = new ArrayList<>(list.size());
        for (EyeBrandWeb brand : list) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("value",brand.getId());
            map.put("name",brand.getName());
            data.add(map);
        }
        return ResponseUtil.okList(data);
    }

}
