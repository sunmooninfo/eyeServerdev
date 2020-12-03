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
import com.eye.db.domain.EyeBrand;
import com.eye.db.service.EyeBrandService;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Api(description = "品牌管理")
@RestController
@RequestMapping("/admin/brand")
@Validated
public class AdminBrandController {
    private final Log logger = LogFactory.getLog(AdminBrandController.class);

    @Autowired
    private EyeBrandService brandService;

    @ApiOperation(value = "品牌管理查询")
    @RequiresPermissions("admin:brand:list")
    @RequiresPermissionsDesc(menu = {"商城管理", "品牌管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "品牌id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "品牌商名称",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String id, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeBrand> brandList = brandService.querySelective(id, name, page, limit, sort, order);
        return ResponseUtil.okList(brandList);
    }

    private Object validate(EyeBrand brand) {
        String name = brand.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        String desc = brand.getDesc();
        if (StringUtils.isEmpty(desc)) {
            return ResponseUtil.badArgument();
        }

        BigDecimal price = brand.getFloorPrice();
        if (price == null) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "品牌管理添加")
    @RequiresPermissions("admin:brand:create")
    @RequiresPermissionsDesc(menu = {"商城管理", "品牌管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeBrand brand) {
        Object error = validate(brand);
        if (error != null) {
            return error;
        }
        brandService.add(brand);
        return ResponseUtil.ok(brand);
    }

    @ApiOperation(value = "品牌管理详情")
    @RequiresPermissions("admin:brand:read")
    @RequiresPermissionsDesc(menu = {"商城管理", "品牌管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "品牌id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeBrand brand = brandService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @ApiOperation(value = "品牌管理编辑")
    @RequiresPermissions("admin:brand:update")
    @RequiresPermissionsDesc(menu = {"商城管理", "品牌管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeBrand brand) {
        Object error = validate(brand);
        if (error != null) {
            return error;
        }
        if (brandService.updateById(brand) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(brand);
    }

    @ApiOperation(value = "品牌管理删除")
    @RequiresPermissions("admin:brand:delete")
    @RequiresPermissionsDesc(menu = {"商城管理", "品牌管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeBrand brand) {
        Integer id = brand.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        brandService.deleteById(id);
        return ResponseUtil.ok();
    }

}
