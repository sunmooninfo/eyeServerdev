package com.eye.common.service;

import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.service.EyeOrderGoodsService;
import com.eye.db.service.EyeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonGoodsService {

    @Autowired
    private EyeOrderService eyeOrderService;
    @Autowired
    private EyeOrderGoodsService eyeOrderGoodsService;

    /**
     * 判断虚拟商品用户是否付费
     * @param userId
     * @param body { goodId: xxx }
     * @return
     */
    public Object isPay(Integer userId, String body) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Integer goodId = JacksonUtil.parseInteger(body, "goodId");
        if(goodId == null){
            return ResponseUtil.badArgument();
        }
        //查询当前用户付费的订单
        List<EyeOrder> orders = eyeOrderService.queryPayOrder(userId);
        for(EyeOrder order :orders){
            List<EyeOrderGoods> eyeOrderGoods = eyeOrderGoodsService.queryByOid(order.getId());
            for(EyeOrderGoods orderGoods : eyeOrderGoods){
                System.out.println(orderGoods.getGoodsId());
                if(orderGoods.getGoodsId().intValue() == goodId.intValue()){
                    return true;
                }
            }
        }
        return false;
    }
}
