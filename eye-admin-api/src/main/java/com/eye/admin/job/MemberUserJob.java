package com.eye.admin.job;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eye.db.domain.EyeIntegral;
import com.eye.db.domain.EyeMemberUser;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeIntegralService;
import com.eye.db.service.EyeMemberUserService;
import com.eye.db.service.EyeUserService;
import com.eye.db.util.MemberConstant;

import java.util.List;

//会员套餐有效期检测
@Component
public class MemberUserJob {
    private final Log logger = LogFactory.getLog(MemberUserJob.class);

    @Autowired
    private EyeMemberUserService memberUserService;

    @Autowired
    private EyeUserService userService;

    @Autowired
    private EyeIntegralService integralService;

    /**
     * 每隔一个小时检查
     * TODO
     * 注意，因为是相隔一个小时检查，因此导致会员套餐真正超时时间可能比设定时间延迟1个小时
     * fixedDelay上一次执行完成之后间隔多少时间再次执行
     */
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void checkMemberUserExpird(){
        logger.info("系统开启任务检查会员时间是否已经过期");
        List<EyeMemberUser> memberUsers = memberUserService.queryExpired();
        for (EyeMemberUser memberUser : memberUsers) {
            //修改会员套餐用户使用表中的状态
            memberUser.setStatus(MemberConstant.USER_STATUS_DOWN);
            memberUserService.updateById(memberUser);

            //修改用户的会员信息
            Integer userId = memberUser.getUserId();
            EyeUser EyeUser = userService.findById(userId);
            EyeUser.setUserLevel(MemberConstant.USER_LEVEL_ON);
            userService.updateById(EyeUser);

            //修改用户的会员积分状态
            EyeIntegral integral = integralService.findByUserId(userId);
            integral.setStatus(MemberConstant.INTEGRAL_STATUS_DOWN_EXPIRE);
            integralService.update(integral);
        }
        logger.info("系统结束任务检查会员时间是否已经到期");
    }
}
