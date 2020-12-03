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
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeBrandWeb;
import com.eye.db.domain.EyeBrandWebMer;
import com.eye.db.service.EyeBrandWebService;
import com.eye.db.service.EyeBrandWebMerService;

import java.util.List;

@RestController
@RequestMapping("/admin/brand/merchants")
@Validated
@Api(description = "品牌招商列表管理")
//品牌权限此项目暂时没有用到，先注销，后期用到再进行修改。
public class AdminBrandMerchantsController {
    private final Log logger = LogFactory.getLog(AdminBrandMerchantsController.class);

    @Autowired
    private EyeBrandWebService brandService;
    @Autowired
    private EyeBrandWebMerService brandWebMerService;

    @ApiOperation("品牌招商查询")
    @RequiresPermissions("admin:brandmerchants:list")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌招商"}, button = "查询")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name="brandId",value = "品牌id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "招商品牌名",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="company",value = "所属公司",required=true,paramType="path",dataType="varchar"),

    })

    public Object list(Integer brandId, String name, String company,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        List<EyeBrandWebMer> list = brandWebMerService.querylist(brandId,name,company,page,limit,sort,order);
        return ResponseUtil.okList(list);
    }


    private Object validate(EyeBrandWebMer merchants) {

        Integer brandId = merchants.getBrandId();
        if (StringUtils.isEmpty(brandId)){
            return ResponseUtil.badArgument();
        }

        String name = merchants.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @ApiOperation(value = "品牌招商添加")
    @RequiresPermissions("admin:brandmerchants:add")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌招商"}, button = "添加")
    @PostMapping("/add")
    public Object add(@RequestBody EyeBrandWebMer merchants){

        Object error = validate(merchants);
        if (error != null) {
            return error;
        }

        EyeBrandWeb brand = brandService.findById(merchants.getBrandId());
        if (brand == null){
            return ResponseUtil.fail(805,"品牌不存在");
        }

        merchants.setCompany(brand.getCompany());
        merchants.setTelephone(brand.getTelephone());

        brandWebMerService.add(merchants);

        return ResponseUtil.ok();

    }

    @ApiOperation(value = "品牌招商修改")
    @RequiresPermissions("admin:brandmerchants:update")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌招商"}, button = "修改")
    @PostMapping("/update")
    public Object update(@RequestBody EyeBrandWebMer merchants){

        Object error = validate(merchants);
        if (error != null) {
            return error;
        }

        brandWebMerService.update(merchants);

        return ResponseUtil.ok();
    }

    @ApiOperation(value = "品牌招商删除")
    @RequiresPermissions("admin:brandmerchants:delete")
//    @RequiresPermissionsDesc(menu = {"商品管理", "品牌招商"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeBrandWebMer merchants){

        Integer id = merchants.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        brandWebMerService.delete(id);

        return ResponseUtil.ok();
    }

}
