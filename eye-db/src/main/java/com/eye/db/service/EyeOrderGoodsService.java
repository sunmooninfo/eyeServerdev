package com.eye.db.service;

import org.springframework.stereotype.Service;

import com.eye.db.dao.EyeOrderGoodsMapper;
import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.domain.EyeOrderGoodsExample;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeOrderGoodsService {
    @Resource
    private EyeOrderGoodsMapper orderGoodsMapper;

    public int add(EyeOrderGoods orderGoods) {
        orderGoods.setAddTime(LocalDateTime.now());
        orderGoods.setUpdateTime(LocalDateTime.now());
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<EyeOrderGoods> queryByOid(Integer orderId) {
        EyeOrderGoodsExample example = new EyeOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public List<EyeOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
        EyeOrderGoodsExample example = new EyeOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public EyeOrderGoods findById(Integer id) {
        return orderGoodsMapper.selectByPrimaryKey(id);
    }

    public void updateById(EyeOrderGoods orderGoods) {
        orderGoods.setUpdateTime(LocalDateTime.now());
        orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
    }

    public Short getComments(Integer orderId) {
        EyeOrderGoodsExample example = new EyeOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        long count = orderGoodsMapper.countByExample(example);
        return (short) count;
    }

    public boolean checkExist(Integer goodsId) {
        EyeOrderGoodsExample example = new EyeOrderGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.countByExample(example) != 0;
    }

    public void deleteByOrderId(Integer orderId) {
        EyeOrderGoodsExample example = new EyeOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        orderGoodsMapper.logicalDeleteByExample(example);
    }

    public List<EyeOrderGoods> findByOrderId(Integer orderId) {
        EyeOrderGoodsExample example = new EyeOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public List<EyeOrderGoods> findByGoodsId(Integer goodsId) {
        EyeOrderGoodsExample example = new EyeOrderGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId);
        return orderGoodsMapper.selectByExample(example);
    }
    
}
