package com.eye.admin.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eye.core.task.Task;
import com.eye.core.utils.BeanUtil;
import com.eye.db.domain.EyeGroupon;
import com.eye.db.domain.EyeGrouponRules;
import com.eye.db.domain.EyeOrder;
import com.eye.db.service.EyeGrouponRulesService;
import com.eye.db.service.EyeGrouponService;
import com.eye.db.service.EyeOrderService;
import com.eye.db.util.GrouponConstant;
import com.eye.db.util.OrderUtil;

import java.util.List;

public class GrouponRuleExpiredTask extends Task {
    private final Log logger = LogFactory.getLog(GrouponRuleExpiredTask.class);
    private int grouponRuleId = -1;

    public GrouponRuleExpiredTask(Integer grouponRuleId, long delayInMilliseconds){
        super("GrouponRuleExpiredTask-" + grouponRuleId, delayInMilliseconds);
        this.grouponRuleId = grouponRuleId;
    }

    @Override
    public void run() {
        logger.info("系统开始处理延时任务---团购规则过期---" + this.grouponRuleId);

        EyeOrderService orderService = BeanUtil.getBean(EyeOrderService.class);
        EyeGrouponService grouponService = BeanUtil.getBean(EyeGrouponService.class);
        EyeGrouponRulesService grouponRulesService = BeanUtil.getBean(EyeGrouponRulesService.class);

        EyeGrouponRules grouponRules = grouponRulesService.findById(grouponRuleId);
        if(grouponRules == null){
            return;
        }
        if(!grouponRules.getStatus().equals(GrouponConstant.RULE_STATUS_ON)){
            return;
        }

        // 团购活动取消
        grouponRules.setStatus(GrouponConstant.RULE_STATUS_DOWN_EXPIRE);
        grouponRulesService.updateById(grouponRules);

        List<EyeGroupon> grouponList = grouponService.queryByRuleId(grouponRuleId);
        // 用户团购处理
        for(EyeGroupon groupon : grouponList){
            Short status = groupon.getStatus();
            EyeOrder order = orderService.findById(groupon.getOrderId());
            if(status.equals(GrouponConstant.STATUS_NONE)){
                groupon.setStatus(GrouponConstant.STATUS_FAIL);
                grouponService.updateById(groupon);
            }
            else if(status.equals(GrouponConstant.STATUS_ON)){
                // 如果团购进行中
                // (1) 团购设置团购失败等待退款状态
                groupon.setStatus(GrouponConstant.STATUS_FAIL);
                grouponService.updateById(groupon);
                // (2) 团购订单申请退款
                if(OrderUtil.isPayStatus(order)) {
                    order.setOrderStatus(OrderUtil.STATUS_REFUND);
                    orderService.updateWithOptimisticLocker(order);
                }
            }
        }
        logger.info("系统结束处理延时任务---团购规则过期---" + this.grouponRuleId);
    }
}
