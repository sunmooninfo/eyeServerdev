package com.eye.common.task;

import com.eye.core.system.SystemConfig;
import com.eye.core.task.Task;
import com.eye.core.utils.BeanUtil;
import com.eye.db.domain.EyeMemberOrder;
import com.eye.db.service.EyeMemberOrderService;
import com.eye.db.util.MemberOrderUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.time.LocalDateTime;

public class MemberOrderUnpaidTask extends Task {
    private final Log logger = LogFactory.getLog(MemberOrderUnpaidTask.class);
    private int memberOrderId = -1;

    public MemberOrderUnpaidTask(Integer memberOrderId, long delayInMilliseconds) {
        super("MemberOrderUnpaidTask-" + memberOrderId, delayInMilliseconds);
        this.memberOrderId = memberOrderId;
    }

    public MemberOrderUnpaidTask(Integer memberOrderId){
        super("MemberOrderUnpaidTask-" + memberOrderId, SystemConfig.getOrderUnpaid() * 60 * 1000);
        this.memberOrderId = memberOrderId;
    }

    @Override
    public void run() {
        logger.info("系统开始处理延时任务---会员套餐订单超时未付款---" + this.memberOrderId);

        EyeMemberOrderService memberOrderService = BeanUtil.getBean(EyeMemberOrderService.class);

        EyeMemberOrder memberOrder = memberOrderService.findById(this.memberOrderId);
        if (memberOrder == null){
            return;
        }
        if (!MemberOrderUtil.isCreateStatus(memberOrder)){
            return;
        }

        memberOrder.setStatus(MemberOrderUtil.STATUS_AUTO_CANCEL);
        memberOrder.setEndTime(LocalDateTime.now());
        if (memberOrderService.updateWithOptimisticLocker(memberOrder) == 0) {
            throw new RuntimeException("更新数据已失效");
        }

        logger.info("系统结束处理延时任务---会员套餐订单超时未付款---" + this.memberOrderId);
    }
}
