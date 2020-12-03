package com.eye.admin.task;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eye.core.task.Task;
import com.eye.core.utils.BeanUtil;
import com.eye.db.domain.EyeMemberGoods;
import com.eye.db.service.EyeMemberGoodsService;
import com.eye.db.util.MemberConstant;


public class MemberGoodsExpiredTask extends Task {
    private final Log logger = LogFactory.getLog(MemberGoodsExpiredTask.class);
    private int memberGooddsId = -1;

    public MemberGoodsExpiredTask(Integer memberGooddsId, long delayInMilliseconds) {
        super("MemberGoodsExpiredTask-" + memberGooddsId, delayInMilliseconds);
        this.memberGooddsId = memberGooddsId;
    }

    @Override
    public void run() {
        logger.info("系统开始处理延时任务---会员商品过期---" + this.memberGooddsId);

        EyeMemberGoodsService memberGoodsService = BeanUtil.getBean(EyeMemberGoodsService.class);

        EyeMemberGoods memberGoods = memberGoodsService.findById(memberGooddsId);

        if (memberGoods == null){
            return;
        }
        //判断会员商品状态
        if (!memberGoods.getStatus().equals(MemberConstant.GOODS_STATUS_ON)){
            return;
        }
        //会员商品下线
        memberGoods.setStatus(MemberConstant.GOODS_STATUS_DOWN_EXPIRE);
        memberGoodsService.updateById(memberGoods);

        logger.info("系统结束处理延时任务---会员商品过期--" + this.memberGooddsId);
    }
}
