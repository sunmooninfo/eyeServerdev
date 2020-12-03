package com.eye.admin.job;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.service.EyeGoodsKillService;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.util.KillConstant;

/**
 * 检测秒杀活动状态
 */
public class GoodsKillJob {
	private final Log logger = LogFactory.getLog(CouponJob.class);
	@Autowired
	 private EyeGoodsKillService goodsKillService;

	@Autowired
	 private EyeGoodsService goodsService;
	/**
	 * 每隔一秒检查
	 */
  @Scheduled(fixedRate = 1000*60*60*2)//两个小时
	public void CheckStatusKill() {

		logger.info("系统开启任务检查秒杀活动状态");

		List<EyeGoodsKill> goodskillList = goodsKillService.queryTo();
		for (EyeGoodsKill goodsKill : goodskillList) {
			goodsKill.setKillStatus(KillConstant.KILL_STATUS_ONGOING);
			goodsKillService.updateById(goodsKill);
		}

		List<EyeGoodsKill> goodskillEndList = goodsKillService.queryExpired();
		for (EyeGoodsKill goodsKill : goodskillEndList) {
			goodsKill.setKillStatus(KillConstant.KILL_STATUS_DUE_TO);
			goodsKill.setIsKill(false);
			goodsKillService.updateById(goodsKill);

			Integer goodsId=goodsKill.getGoodsId();
			EyeGoods goods=goodsService.findById(goodsId);
			goods.setIsKill(false);
			goodsService.updateById(goods);

		}



		logger.info("系统结束任务检查秒杀活动状态");
	}


}
