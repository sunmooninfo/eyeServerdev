package com.eye.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eye.db.dao.EyeAccessoryMapper;
import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeAccessoryExample;

import java.time.LocalDateTime;


@Service
public class EyeAccessoryService {


    @Autowired
    private EyeAccessoryMapper accessoryMapper;

    public void add(EyeAccessory accessory, Integer status){
        accessory.setStatus(status);
        accessory.setAddtime(LocalDateTime.now());
        accessory.setUpdatetime(LocalDateTime.now());
        accessoryMapper.insertSelective(accessory);
    }

    public EyeAccessory queryByGid(Integer id,Integer status) {
        EyeAccessoryExample example = new EyeAccessoryExample();
        example.or().andGoodsIdEqualTo(id).andStatusEqualTo(status).andDeletedEqualTo(false);
        return accessoryMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {

        accessoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(EyeAccessory accessory) {
        accessory.setUpdatetime(LocalDateTime.now());
        accessoryMapper.updateByPrimaryKeySelective(accessory);

    }

    public void deleteByGid(Integer goodId, Integer status) {
        EyeAccessoryExample example = new EyeAccessoryExample();
        example.or().andGoodsIdEqualTo(goodId).andStatusEqualTo(status).andDeletedEqualTo(false);
        accessoryMapper.logicalDeleteByExample(example);

    }

    public EyeAccessory findByGoodsId(Integer goodsId,Integer status) {
        EyeAccessoryExample example = new EyeAccessoryExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(status).andDeletedEqualTo(false);
        return accessoryMapper.selectOneByExample(example);
    }
}
