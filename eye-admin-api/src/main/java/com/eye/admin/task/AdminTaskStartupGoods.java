package com.eye.admin.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.eye.core.task.TaskService;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.domain.EyeIntegralGoods;
import com.eye.db.domain.EyeMemberGoods;
import com.eye.db.service.EyeGoodsKillService;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeIntegralGoodsService;
import com.eye.db.service.EyeMemberGoodsService;
import com.eye.db.util.KillConstant;
import com.eye.db.util.MemberConstant;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class AdminTaskStartupGoods implements ApplicationRunner {

    @Autowired
    private EyeMemberGoodsService memberGoodsService;

    @Autowired
    private EyeIntegralGoodsService integralService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EyeGoodsKillService goodsKillService;

    @Autowired
    private EyeGoodsService goodsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //会员商品
        List<EyeMemberGoods> memberGoodsList = memberGoodsService.queryByStatus(MemberConstant.GOODS_STATUS_ON);
        for (EyeMemberGoods memberGoods : memberGoodsList) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expire = memberGoods.getExpireTime();

            if (expire.isBefore(now)){
                //已过期则加入延迟队列
                taskService.addTask(new MemberGoodsExpiredTask(memberGoods.getId(),0));
            }else {
                //还没过期,则加入延迟队列
                long delay = ChronoUnit.MILLIS.between(now, expire);
                taskService.addTask(new MemberGoodsExpiredTask(memberGoods.getId(),delay));
            }
        }

        //积分商品
        List<EyeIntegralGoods> integralGoodsList = integralService.queryByStatus(MemberConstant.INTEGRAL_GOODS_STATUS_ON);
        for (EyeIntegralGoods integral : integralGoodsList) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireTime = integral.getExpireTime();

            if (expireTime.isBefore(now)){
                //已过期则加入延迟队列
                taskService.addTask(new IntegralGoodsExpiredTask(integral.getId(),0));
            }else {
                //还没过期,则加入延迟队列
                long delay = ChronoUnit.MILLIS.between(now, expireTime);
                taskService.addTask(new IntegralGoodsExpiredTask(integral.getId(),delay));
            }
        }

        //秒杀商品未开始状态
        System.out.println("系统启动秒杀商品加入延时对列");
        List<EyeGoodsKill> unStartGoodsKills = goodsKillService.queryByStatus(KillConstant.KILL_STATUS_NOT_AT_THE);
        for (EyeGoodsKill unStartGoodsKill : unStartGoodsKills) {
            LocalDateTime startDate = unStartGoodsKill.getStartDate();
            LocalDateTime endDate = unStartGoodsKill.getEndDate();
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(startDate)){
                //商品秒杀未开始
                //加入商品秒杀开始延时
                long startDelay = ChronoUnit.MILLIS.between(now, startDate);
                taskService.addTask(new KillGoodsStartTask(unStartGoodsKill.getGoodsId(),startDelay));
                //加入商品秒杀过期延时
                long endDelay = ChronoUnit.MILLIS.between(now, endDate);
                taskService.addTask(new KillGoodsExpiredTask(unStartGoodsKill.getGoodsId(),endDelay));
            }else if(now.isAfter(startDate) && now.isBefore(endDate)){
                //商品秒杀正在进行，未结束
                unStartGoodsKill.setKillStatus(KillConstant.KILL_STATUS_ONGOING);
                goodsKillService.updateById(unStartGoodsKill);
                //加入商品秒杀过期延时
                long endDelay = ChronoUnit.MILLIS.between(now, endDate);
                taskService.addTask(new KillGoodsExpiredTask(unStartGoodsKill.getGoodsId(),endDelay));
            }else{
                //商品秒杀已经过期,加入过期延时
                unStartGoodsKill.setKillStatus(KillConstant.KILL_STATUS_DUE_TO);
                unStartGoodsKill.setIsKill(false);
                goodsKillService.updateById(unStartGoodsKill);
                EyeGoods goods = goodsService.findById(unStartGoodsKill.getGoodsId());
                goods.setIsKill(false);
                goodsService.updateById(goods);
                taskService.addTask(new KillGoodsExpiredTask(unStartGoodsKill.getGoodsId(),0));
            }
        }

        //秒杀商品已开始状态
        List<EyeGoodsKill> killGoods = goodsKillService.queryByStatus(KillConstant.KILL_STATUS_ONGOING);
        for (EyeGoodsKill killGood : killGoods) {
            LocalDateTime endDate = killGood.getEndDate();
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(endDate)){
                //商品秒杀正在进行,加入过期延时
                long endDelay = ChronoUnit.MILLIS.between(now, endDate);
                taskService.addTask(new KillGoodsExpiredTask(killGood.getGoodsId(),endDelay));
            }else{
                //商品秒杀已经过期，加入过期延时
                killGood.setKillStatus(KillConstant.KILL_STATUS_DUE_TO);
                killGood.setIsKill(false);
                goodsKillService.updateById(killGood);
                EyeGoods goods = goodsService.findById(killGood.getGoodsId());
                goods.setIsKill(false);
                goodsService.updateById(goods);
                taskService.addTask(new KillGoodsExpiredTask(killGood.getGoodsId(),0));
            }
        }
        System.out.println("系统启动秒杀商品加入延时对列");
    }
}
