package com.eye.brand.web;

import com.eye.brand.annotation.LoginUser;
import com.eye.brand.util.BrandResponseCode;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeBrandWeb;
import com.eye.db.domain.EyeBrandWebMer;
import com.eye.db.service.EyeBrandWebMerService;
import com.eye.db.service.EyeBrandWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/langbo/brand/merchants")
@Validated
@Api(description = "品牌招商管理")
public class EyeBrandWebMerController {
    private final Log logger = LogFactory.getLog(EyeBrandWebMerController.class);

    @Autowired
    private EyeBrandWebService brandService;
    @Autowired
    private EyeBrandWebMerService merchantsService;

    @GetMapping("/list")
    @ApiOperation("品牌招商列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "招商名",required=false,paramType="path",dataType="string"),
            @ApiImplicitParam(name="company",value = "所属公司",required=false,paramType="path",dataType="string")
    })
    public Object list(@LoginUser Integer userId,
                       String name,String company,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){

        List<EyeBrandWebMer> list = merchantsService.querySelective(name,company,page,limit,sort,order);
        return ResponseUtil.okList(list);
    }

    @PostMapping("/add")
    @ApiOperation("添加品牌招商")
    public Object add(@LoginUser Integer userId, @RequestBody EyeBrandWebMer merchants) {

        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        EyeBrandWeb brand = brandService.findById(merchants.getBrandId());
        if (brand == null) {
            return ResponseUtil.fail(BrandResponseCode.BRAND_INEXISTENCE, "品牌不存在");
        }

        if (!brand.getUserId().equals(userId)) {
            return ResponseUtil.fail(BrandResponseCode.BRAND_NOT_PERSON, "您没有添加权限");
        }

        merchants.setCompany(brand.getCompany());
        merchants.setTelephone(brand.getTelephone());
        merchantsService.add(merchants);

        return ResponseUtil.ok();

    }

    @PostMapping("/update")
    @ApiOperation("修改品牌招商")
    public Object update(@LoginUser Integer userId, @RequestBody EyeBrandWebMer merchants) {

        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        EyeBrandWeb brand = brandService.findById(merchants.getBrandId());
        if (!brand.getUserId().equals(userId)) {
            return ResponseUtil.fail(BrandResponseCode.BRAND_NOT_PERSON, "您没有修改权限");
        }
        merchantsService.update(merchants);

        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除品牌招商")
    public Object delete(@LoginUser Integer userId, @RequestBody EyeBrandWebMer merchants) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Integer id = merchants.getId();
        EyeBrandWebMer brandMerchants = merchantsService.findById(id);
        EyeBrandWeb brand = brandService.findById(brandMerchants.getBrandId());

        if (!brand.getUserId().equals(userId)) {
            return ResponseUtil.fail(BrandResponseCode.BRAND_NOT_PERSON, "您没有删除权限");
        }

        merchantsService.delete(id);

        return ResponseUtil.ok();
    }

    @GetMapping("/detail")
    @ApiOperation("品牌招商详情")
    @ApiImplicitParam(name="id",value = "品牌招商id",required=false,paramType="path",dataType="int")
    public Object detail(@NotNull Integer id){
        EyeBrandWebMer merchants = merchantsService.findById(id);
        if (merchants == null){
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(merchants);
    }

    @PostMapping("/save")
    @ApiOperation("添加或修改品牌招商")
    public Object save(@LoginUser Integer userId, @RequestBody EyeBrandWebMer merchants){

        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        EyeBrandWeb brand = brandService.findById(merchants.getBrandId());
        if (brand == null) {
            return ResponseUtil.fail(BrandResponseCode.BRAND_INEXISTENCE, "品牌不存在");
        }

        EyeBrandWebMer brandMerchants = merchantsService.findByBrandId(merchants.getBrandId());
        if (brandMerchants == null){
            if (!brand.getUserId().equals(userId)) {
                return ResponseUtil.fail(BrandResponseCode.BRAND_NOT_PERSON, "您没有添加权限");
            }
            merchants.setCompany(brand.getCompany());
            merchants.setTelephone(brand.getTelephone());
            merchantsService.add(merchants);
        }else{
            if (!brand.getUserId().equals(userId)) {
                return ResponseUtil.fail(BrandResponseCode.BRAND_NOT_PERSON, "您没有修改权限");
            }
            merchantsService.update(merchants);
        }

        return ResponseUtil.ok();
    }

}
