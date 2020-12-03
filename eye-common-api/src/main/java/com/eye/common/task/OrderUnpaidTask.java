package com.eye.common.task;

import com.eye.common.service.CommonOrderService;
import com.eye.core.system.SystemConfig;
import com.eye.core.task.Task;
import com.eye.core.utils.BeanUtil;
import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.service.EyeGoodsProductService;
import com.eye.db.service.EyeOrderGoodsService;
import com.eye.db.service.EyeOrderService;
import com.eye.db.util.OrderUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUnpaidTask extends Task {
    private final Log logger = LogFactory.getLog(OrderUnpaidTask.class);
    private int orderId = -1;

    public OrderUnpaidTask(Integer orderId, long delayInMilliseconds){
        super("OrderUnpaidTask-" + orderId, delayInMilliseconds);
        this.orderId = orderId;
    }

    public OrderUnpaidTask(Integer orderId){
        super("OrderUnpaidTask-" + orderId, SystemConfig.getOrderUnpaid() * 60 * 1000);
        this.orderId = orderId;
    }
    @Override
    public void run() {
        logger.info("系统开始处理延时任务---订单超时未付款---" + this.orderId);

        EyeOrderService orderService = BeanUtil.getBean(EyeOrderService.class);
        EyeOrderGoodsService orderGoodsService = BeanUtil.getBean(EyeOrderGoodsService.class);
        EyeGoodsProductService productService = BeanUtil.getBean(EyeGoodsProductService.class);
        CommonOrderService commonOrderService = BeanUtil.getBean(CommonOrderService.class);

        EyeOrder order = orderService.findById(this.orderId);
        if(order == null){
            return;
        }
        if(!OrderUtil.isCreateStatus(order)){
            return;
        }

        // 设置订单已取消状态
        order.setOrderStatus(OrderUtil.STATUS_AUTO_CANCEL);
        order.setEndTime(LocalDateTime.now());
        if (orderService.updateWithOptimisticLocker(order) == 0) {
            throw new RuntimeException("更新数据已失效");
        }

        // 商品货品数量增加
        Integer orderId = order.getId();
        List<EyeOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
        for (EyeOrderGoods orderGoods : orderGoodsList) {
            Integer productId = orderGoods.getProductId();
            Short number = orderGoods.getNumber();
            if (productService.addStock(productId, number) == 0) {
                throw new RuntimeException("商品货品库存增加失败");
            }
        }

        //返还优惠券
        commonOrderService.releaseCoupon(orderId);

        //返还积分
        commonOrderService.releaseIntegral(orderId);
        logger.info("系统结束处理延时任务---订单超时未付款---" + this.orderId);
    }
}
