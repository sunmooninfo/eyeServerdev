package com.eye.common.service;

import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.service.EyeAccessoryService;
import com.eye.db.service.EyeOrderGoodsService;
import com.eye.db.service.EyeOrderService;
import com.eye.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonAccessoryService {

    @Autowired
    private EyeOrderService orderService;

    @Autowired
    private EyeOrderGoodsService orderGoodsService;

    @Autowired
    private EyeAccessoryService accessoryService;

    public EyeAccessory findByGoodsId(Integer userId, Integer goodsId){
        //判断用户是否购买过虚拟商品,如果购买过则将商品信息展示出来
        EyeAccessory accessory = accessoryService.findByGoodsId(goodsId,0);
        List<EyeOrderGoods> orderGoodsList = null;
        if (accessory != null) {
            orderGoodsList = orderGoodsService.findByGoodsId(goodsId);
        }
        if (orderGoodsList != null) {
            for (EyeOrderGoods litemallOrderGoods : orderGoodsList) {
                EyeOrder order = orderService.findById(userId, litemallOrderGoods.getOrderId());
                if (order != null){
                    Short status = order.getOrderStatus();
                    if (status.equals(OrderUtil.STATUS_PAY) || status.equals(OrderUtil.STATUS_SHIP) || status.equals(OrderUtil.STATUS_CONFIRM) || status.equals(OrderUtil.STATUS_AUTO_CONFIRM)) {
                        return accessory;
                    }
                }
            }
        }
        return null;
    }

}
