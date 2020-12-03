package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.CommonCompanyService;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeCompany;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author edc
 * @date 2020/7/21 14:19
 * @description
 */
@RestController
@RequestMapping("/common/company")
@Api(description = "公司服务")
public class CommonCompanyController {

    @Resource
    private CommonCompanyService companyService;


    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除")
    public Object delete(@LoginUser Integer userId, @RequestBody EyeCompany company){
        if(userId == null){
            ResponseUtil.unlogin();
        }
        Integer id = company.getId();
        if(id == null){
            return ResponseUtil.badArgumentValue();
        }
        companyService.deleteById(id);
        return ResponseUtil.ok();
    }
}
