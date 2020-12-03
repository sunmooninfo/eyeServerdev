package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeIntegral;
import com.eye.db.service.EyeIntegralService;
import com.eye.db.util.MemberConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common/integral")
@Validated
@Api(description = "积分服务")
public class CommonIntegralController {
    private final Log logger = LogFactory.getLog(CommonIntegralController.class);

    @Autowired
    private EyeIntegralService integralService;

    /**
     * 减少会员积分
     *
     * @param userId
     * @param eyeIntegral
     * @return
     */
    @PostMapping("/updateReduce")
    @ApiOperation("减少会员积分")
    public Object updateReduce(@LoginUser Integer userId, @RequestBody EyeIntegral eyeIntegral) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        EyeIntegral byUserId = integralService.findByUserId(userId);
        Integer integration = byUserId.getIntegration();
        eyeIntegral.setId(byUserId.getId());
        if (integration < eyeIntegral.getIntegration()){
            return ResponseUtil.fail(MemberConstant.INTEGRAL_OF_LACK,"您的会员积分不足");
        }
        eyeIntegral.setIntegration(integration - eyeIntegral.getIntegration());
        integralService.update(eyeIntegral);
        return ResponseUtil.ok();
    }


    //查看会员积分
    @GetMapping("/myintegral")
    @ApiOperation("查看会员积分")
    @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    public Object myintegral(@LoginUser Integer userId){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        EyeIntegral integral = integralService.findByUserId(userId);
        if (integral == null){
            integral.setIntegration(0);
            return ResponseUtil.ok(integral);
        }
        return ResponseUtil.ok(integral);
    }
}
