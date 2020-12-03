package com.eye.admin.task;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eye.core.task.Task;
import com.eye.core.utils.BeanUtil;
import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.service.EyeGoodsKillService;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.util.KillConstant;


public class KillGoodsStartTask extends Task {
    private final Log logger = LogFactory.getLog(KillGoodsStartTask.class);
    private Integer killGoodsId = -1;

    public KillGoodsStartTask(Integer killGoodsId, long delayInMilliseconds) {
        super("KillGoodsStartTask"+killGoodsId, delayInMilliseconds);
        this.killGoodsId = killGoodsId;
    }

    @Override
    public void run() {
        logger.info("系统开始处理延时任务---秒杀商品开始---"+this.killGoodsId);
        EyeGoodsKillService goodsKillService = BeanUtil.getBean(EyeGoodsKillService.class);
        EyeGoodsService goodsService = BeanUtil.getBean(EyeGoodsService.class);
        EyeGoodsKill goodsKill = goodsKillService.findByGoodsId(killGoodsId);
        if(goodsKill == null){
            return ;
        }
        goodsKill.setKillStatus(KillConstant.KILL_STATUS_ONGOING);
        goodsKillService.updateById(goodsKill);
        logger.info("系统结束处理延时任务---秒杀商品开始---"+this.killGoodsId);
    }
}
