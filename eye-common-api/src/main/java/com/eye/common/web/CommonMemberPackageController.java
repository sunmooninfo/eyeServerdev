package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeIntegral;
import com.eye.db.domain.EyeMemberPackage;
import com.eye.db.service.EyeIntegralService;
import com.eye.db.service.EyeMemberPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/common/member/package")
@Validated
@Api(description = "会员套餐服务")
public class CommonMemberPackageController {
    private final Log logger = LogFactory.getLog(CommonMemberPackageController.class);

    @Autowired
    private EyeMemberPackageService packageService;
    @Autowired
    private EyeIntegralService integralService;

    /**
     * 会员套餐列表
     *
     * @param userId
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("会员套餐列表")
    @ApiImplicitParam(name="userId",value = "用户id",required=true,dataType="int")
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        List<EyeMemberPackage> list = packageService.list(page,limit,sort,order);

        Map<String,Object> map = new HashMap<>();
        if (userId != null){
            EyeIntegral integral = integralService.findByUserId(userId);
            map.put("integral",integral);

        }
        map.put("list",list);
        return ResponseUtil.ok(map);
    }

    /**
     * 会员套餐详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation("会员套餐详情")
    @ApiImplicitParam(name="id",value = "会员套餐id",required=true,paramType="path",dataType="int")
    public Object detail(@NotNull Integer id){
        EyeMemberPackage memberPackage = packageService.findById(id);
        return ResponseUtil.ok(memberPackage);
    }

}
