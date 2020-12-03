package com.eye.db.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.eye.db.dao.EyeCommissionMapper;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyeCommission;
import com.eye.db.domain.EyeCommissionExample;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.domain.EyeSignStore;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EyeCommissionService {

	@Resource
	private EyeCommissionMapper commissionMapper;
	@Autowired
	private EyeSignStoreService signStoreService;
	@Autowired
	private EyeOrderGoodsService orderGoodsService;
	@Autowired
	private EyeGoodsService goodsService;
	/**
	 * 计算门店佣金并保存
	 * @param order
	 */
	public void add(EyeOrder order) {
		BigDecimal orderCommission = new BigDecimal(0.00);//订单佣金
		List<EyeOrderGoods> orderGoods = orderGoodsService.queryByOid(order.getId());
		for (EyeOrderGoods orderGood : orderGoods) {
			EyeGoods goods = goodsService.findById(orderGood.getGoodsId());
			orderCommission = orderCommission.add(orderGood.getPrice().multiply(new BigDecimal(orderGood.getNumber()).multiply(goods.getCommissionRate())));
		}

		BigDecimal orderPrice = order.getOrderPrice();
		Integer storeSn = order.getStoreSn();
		if(storeSn == null && storeSn ==0){
			return ;
		}
		EyeSignStore EyeSignStore  = signStoreService.queryByStoreSn(storeSn);
		BigDecimal commisionRate = EyeSignStore.getCommisionRate();
		EyeCommission EyeCommission = new EyeCommission();
		EyeCommission.setOrderSn(order.getOrderSn());
		EyeCommission.setManagerMobile(EyeSignStore.getManagerMobile());
		EyeCommission.setOrderCommision(orderPrice.multiply(orderCommission));
		EyeCommission.setOrderTotalPrice(orderPrice);
		EyeCommission.setAddTime(LocalDateTime.now());
		EyeCommission.setUpdateTime(LocalDateTime.now());
		this.commissionMapper.insertSelective(EyeCommission);


	}


	/**
	 * 查询佣金信息
	 * @param orderSn
	 * @param storeName
	 * @param start
	 * @param end
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<EyeCommission> querySelective(String orderSn,String storeName,LocalDateTime start,LocalDateTime end,Integer page, Integer limit) {
		EyeAdmin admin = (EyeAdmin) SecurityUtils.getSubject().getPrincipal();
		List<EyeCommission> EyeCommissions = null;
		if(admin.getId() == 6){
			String managerMobile = admin.getManagerMobile();
			EyeCommissions = this.commissionMapper.selectCommisionInfo(managerMobile,orderSn, storeName, start, end,false);
		}else{
			EyeCommissions = this.commissionMapper.selectCommisionInfo(null,orderSn, storeName, start, end,false);
		}
		PageHelper.startPage(page,limit);

		return EyeCommissions;
	}

	public int deliteById(Integer id) {
		return commissionMapper.logicalDeleteByPrimaryKey(id);
	}

	public EyeCommission queryByStoreSn(EyeOrder order) {
		EyeCommissionExample example = new EyeCommissionExample();
		EyeCommissionExample.Criteria criteria = example.createCriteria();
		criteria.andOrderSnEqualTo(order.getOrderSn()).andDeletedEqualTo(false);
		return commissionMapper.selectOneByExample(example);
	}

	public EyeCommission queryByOrderSn(String orderSn) {
		EyeCommissionExample example = new EyeCommissionExample();
		EyeCommissionExample.Criteria criteria = example.createCriteria();
		criteria.andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
		return this.commissionMapper.selectOneByExample(example);
	}
}
