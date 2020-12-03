package com.eye.admin.job;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eye.db.domain.EyeGroupon;
import com.eye.db.domain.EyeGrouponRules;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeGrouponRulesService;
import com.eye.db.service.EyeGrouponService;
import com.eye.db.service.EyeUserService;
import com.eye.db.util.GrouponConstant;

import java.util.List;


//@Component
public class GrouponJob {
   private final Log logger =  LogFactory.getLog(GrouponJob.class);
   @Autowired
   private EyeGrouponService grouponService;
   @Autowired
   private EyeGrouponRulesService grouponRulesService;
   @Autowired
   private EyeUserService userService;
    @Scheduled(fixedDelay = 600 * 60 * 1000)
//
    public void fillUpGroupon(){
    logger.info("系统开启定时任务自动将团购活动补齐成员");
    //查询正在开团的团购活动
    List<EyeGroupon> groupons =  grouponService.queryByStatus(GrouponConstant.STATUS_ON);

    for(EyeGroupon groupon : groupons){
     //自动补满团购活动成员
        //1.随机抽取一个假用户，作为参团成员，此用户ID不等于开团用户ID;
        List<EyeUser> users =  userService.queryAllUser();
        Integer[] integers = new Integer[users.size()];
        for(int i = 0;i <users.size(); i++){
            integers[i] = users.get(i).getId();
        }

        int ran = 0 ;
        for( int i = 0;i<5;i++){
            ran = RandomUtils.nextInt(1, users.size());
            if(integers[ran] != groupon.getCreatorUserId()){
                break;
            }
        }

        EyeUser user = userService.findById(integers[ran]);

        EyeGroupon EyeGroupon = new EyeGroupon();
        EyeGroupon.setOrderId(groupon.getOrderId());
        EyeGroupon.setShareUrl(groupon.getShareUrl());
        EyeGroupon.setGrouponId(groupon.getId());
        EyeGroupon.setUserId(user.getId());
        EyeGroupon.setStatus(GrouponConstant.STATUS_SUCCEED);
        EyeGroupon.setRulesId(groupon.getRulesId());
        EyeGroupon.setCreatorUserId(groupon.getUserId());
        grouponService.createGroupon(EyeGroupon);

        int joinerCount  = grouponService.countGroupon(groupon.getId());
        EyeGrouponRules grouponRules = grouponRulesService.findById(groupon.getRulesId());
        System.out.println("joinerCount:"+joinerCount+",totlecount:"+grouponRules.getDiscountMember());

        if(joinerCount+1 == grouponRules.getDiscountMember() ){
            groupon.setStatus(GrouponConstant.STATUS_SUCCEED);
            grouponService.updateById(groupon);
        }

        logger.info("系统结束定时任务自动将团购活动补齐成员");
    }
    }

}
