package com.eye.admin.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eye.core.task.Task;
import com.eye.core.utils.BeanUtil;
import com.eye.db.domain.EyeIntegralGoods;
import com.eye.db.service.EyeIntegralGoodsService;
import com.eye.db.util.MemberConstant;

public class IntegralGoodsExpiredTask extends Task {

    private final Log logger = LogFactory.getLog(IntegralGoodsExpiredTask.class);

    private int integralGoodsId = -1;

    public IntegralGoodsExpiredTask(Integer integralGoodsId, long delayInMilliseconds) {
        super("IntegralGoodsExpiredTask-" + integralGoodsId, delayInMilliseconds);
        this.integralGoodsId = integralGoodsId;
    }

    @Override
    public void run() {
        logger.info("系统开始处理延时任务---积分商品过期---" + this.integralGoodsId);
        EyeIntegralGoodsService integralService = BeanUtil.getBean(EyeIntegralGoodsService.class);
        EyeIntegralGoods integralGoods = integralService.findById(integralGoodsId);

        if (integralGoods == null){
            return;
        }
        //判断积分商品的状态
        if (!integralGoods.getStatus().equals(MemberConstant.INTEGRAL_GOODS_STATUS_ON)){
            return;
        }
        //积分商品下线
        integralGoods.setStatus(MemberConstant.INTEGRAL_GOODS_STATUS_DOWN_EXPIRE);
        integralService.updateById(integralGoods);

        logger.info("系统开始处理延时任务---积分商品过期---" + this.integralGoodsId);

    }
}
