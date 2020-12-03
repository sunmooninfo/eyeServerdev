package com.eye.brand.web;

import com.eye.brand.annotation.LoginUser;
import com.eye.brand.dto.BrandAllinone;
import com.eye.brand.util.BrandResponseCode;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/langbo/brand")
@Validated
@Api(description = "品牌管理")
public class EyeBrandWebController {

    private final Log logger = LogFactory.getLog(EyeBrandWebController.class);

    @Autowired
    private EyeBrandWebService brandService;
    @Autowired
    private EyeBrandWebMerService merchantsService;

    @GetMapping("/list")
    @ApiOperation("品牌列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=false,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "品牌名",required=false,paramType="path",dataType="string"),
            @ApiImplicitParam(name="company",value = "所属公司",required=false,paramType="path",dataType="string")
    })
    public Object list(@LoginUser Integer userId,
                        String name,String company,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer limit,
                        @Order @RequestParam(defaultValue = "desc") String order) {

        List<EyeBrandWeb> list = brandService.querySelective(name, company, page, limit, order);
        return ResponseUtil.okList(list);
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
    @PostMapping("/add")
    @ApiOperation("添加品牌和品牌招商")
    @Transactional
    public Object add(@LoginUser Integer userId, @RequestBody BrandAllinone allinone) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        if (allinone == null){
            return ResponseUtil.badArgumentValue();
        }

        EyeBrandWeb brand = allinone.getBrand();
        EyeBrandWebMer merchants = allinone.getMerchants();

        Object error = validate(brand);
        if (error != null) {
            return error;
        }

        String name = brand.getName();
        if (brandService.checkExistByName(name)){
            return ResponseUtil.fail(BrandResponseCode.BRAND_NAME_EXIST,"品牌名已存在");
        }

        brand.setUserId(userId);
        brandService.add(brand);

        if (merchants != null) {
            merchants.setBrandId(brand.getId());
            merchants.setCompany(brand.getCompany());
            merchants.setTelephone(brand.getTelephone());
            merchantsService.add(merchants);
        }
        return ResponseUtil.ok();
    }

    //修改
    @PostMapping("/update")
    @ApiOperation("修改品牌")
    @Transactional
    public Object update(@LoginUser Integer userId, @RequestBody EyeBrandWeb brand){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Object error = validate(brand);
        if (error != null) {
            return error;
        }

        brandService.update(brand);

        return ResponseUtil.ok();
    }

    //删除
    @PostMapping("/delete")
    @ApiOperation("删除品牌")
    public Object delete(@LoginUser Integer userId, @RequestBody EyeBrandWeb brand){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Integer id = brand.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        brandService.delete(id);

        return ResponseUtil.ok();
    }

    @GetMapping("/detail")
    @ApiOperation("品牌详情")
    @ApiImplicitParam(name="id",value = "品牌id",required=false,paramType="path",dataType="int")
    public Object detail(@NotNull Integer id){
        EyeBrandWeb brand = brandService.findById(id);

        if(brand == null){
            return ResponseUtil.badArgument();
        }

        EyeBrandWebMer merchants = merchantsService.findByBrandId(id);

        Map<String,Object> data = new HashMap<>();
        data.put("brand",brand);
        data.put("merchants",merchants);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/mylist")
    @ApiOperation("查看自己的品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="name",value = "品牌名",required=false,paramType="path",dataType="string"),
            @ApiImplicitParam(name="company",value = "所属公司",required=false,paramType="path",dataType="string")
    })
    public Object mylist(@LoginUser Integer userId,
                       String name,String company,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<EyeBrandWeb> list = brandService.querylist(userId,name, company, page, limit, order);
        return ResponseUtil.okList(list);
    }

}
