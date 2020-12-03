package com.eye.admin.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eye.core.system.SystemConfig;
import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeGoodsProductService;
import com.eye.db.service.EyeGrouponRulesService;
import com.eye.db.service.EyeGrouponService;
import com.eye.db.service.EyeIntegralService;
import com.eye.db.service.EyeOrderGoodsService;
import com.eye.db.service.EyeOrderService;
import com.eye.db.service.EyeUserService;
import com.eye.db.util.MemberConstant;
import com.eye.db.util.OrderUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 检测订单状态
 */
@Component
public class OrderJob {
	private final Log logger = LogFactory.getLog(OrderJob.class);

    @Autowired
    private EyeOrderGoodsService orderGoodsService;
    @Autowired
    private EyeOrderService orderService;
    @Autowired
    private EyeGoodsProductService productService;
    @Autowired
    private EyeGrouponService grouponService;
    @Autowired
    private EyeGrouponRulesService rulesService;
    @Autowired
    private EyeUserService userService;
    @Autowired
    private EyeIntegralService integralService;

    /**
     * 自动确认订单
     * <p>
     * 定时检查订单未确认情况，如果超时 Eye_ORDER_UNCONFIRM 天则自动确认订单
     * 定时时间是每天凌晨3点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致订单真正超时时间是 [Eye_ORDER_UNCONFIRM, 1 + Eye_ORDER_UNCONFIRM]
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void checkOrderUnconfirm() {
        logger.info("系统开启定时任务检查订单是否已经超期自动确认收货");

        List<EyeOrder> orderList = orderService.queryUnconfirm(SystemConfig.getOrderUnconfirm());
        for (EyeOrder order : orderList) {

            // 设置订单已取消状态
            order.setOrderStatus(OrderUtil.STATUS_AUTO_CONFIRM);
            order.setConfirmTime(LocalDateTime.now());
            if (orderService.updateWithOptimisticLocker(order) == 0) {
                logger.info("订单 ID=" + order.getId() + " 数据已经更新，放弃自动确认收货");
            } else {
                logger.info("订单 ID=" + order.getId() + " 已经超期自动确认收货");

                //确认收货之后增加用户积分
                Integer userId = order.getUserId();
                EyeUser EyeUser = userService.findById(userId);
                if (EyeUser.getUserLevel() == MemberConstant.USER_LEVEL_VIP || EyeUser.getUserLevel() == MemberConstant.USER_LEVEL_HIGH_VIP){

                    //BigDecimal.ROUND_DOWN 舍弃整数后面的分数
                    int actualPrice = order.getActualPrice().setScale(0, BigDecimal.ROUND_DOWN).intValue();
                    integralService.updateIntegration(userId,actualPrice);
                }
            }
        }

        logger.info("系统结束定时任务检查订单是否已经超期自动确认收货");
    }

    /**
     * 可评价订单商品超期
     * <p>
     * 定时检查订单商品评价情况，如果确认商品超时 Eye_ORDER_COMMENT 天则取消可评价状态
     * 定时时间是每天凌晨4点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致订单真正超时时间是 [Eye_ORDER_COMMENT, 1 + Eye_ORDER_COMMENT]
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void checkOrderComment() {
        logger.info("系统开启任务检查订单是否已经超期未评价");

        List<EyeOrder> orderList = orderService.queryComment(SystemConfig.getOrderComment());
        for (EyeOrder order : orderList) {
            order.setComments((short) 0);
            orderService.updateWithOptimisticLocker(order);

            List<EyeOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
            for (EyeOrderGoods orderGoods : orderGoodsList) {
                orderGoods.setComment(-1);
                orderGoodsService.updateById(orderGoods);
            }
        }

        logger.info("系统结束任务检查订单是否已经超期未评价");
    }
}
