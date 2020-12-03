package com.eye.common.task;

import com.eye.core.system.SystemConfig;
import com.eye.core.task.TaskService;
import com.eye.db.domain.EyeMemberOrder;
import com.eye.db.domain.EyeOrder;
import com.eye.db.service.EyeMemberOrderService;
import com.eye.db.service.EyeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class TaskStartupRunner implements ApplicationRunner {
    @Autowired
    private EyeOrderService orderService;
    @Autowired
    private EyeMemberOrderService memberOrderService;
    @Autowired
    private TaskService taskService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //商品订单定时任务
        List<EyeOrder> orderList = orderService.queryUnpaid(SystemConfig.getOrderUnpaid());
        for(EyeOrder order : orderList){
            LocalDateTime add = order.getAddTime();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expire =  add.plusMinutes(SystemConfig.getOrderUnpaid());
            if(expire.isBefore(now)) {
                // 已经过期，则加入延迟队列
                taskService.addTask(new OrderUnpaidTask(order.getId(), 0));
            }
            else{
                // 还没过期，则加入延迟队列
                long delay = ChronoUnit.MILLIS.between(now, expire);
                taskService.addTask(new OrderUnpaidTask(order.getId(), delay));
            }
        }
        //会员套餐订单定时任务
        List<EyeMemberOrder> memberOrderList = memberOrderService.queryUnpaid(SystemConfig.getOrderUnpaid());
        for (EyeMemberOrder memberOrder : memberOrderList) {
            LocalDateTime add = memberOrder.getAddTime();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expire = add.plusMinutes(SystemConfig.getOrderUnpaid());
            if (expire.isBefore(now)) {
                taskService.addTask(new MemberOrderUnpaidTask(memberOrder.getId(),0));
            } else {
                long delay = ChronoUnit.MILLIS.between(now, expire);
                taskService.addTask(new MemberOrderUnpaidTask(memberOrder.getId(), delay));
            }
        }
    }
}
