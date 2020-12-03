package com.eye.db.service;


import org.springframework.stereotype.Service;

import com.eye.db.dao.EyeGoodsAttributeMapper;
import com.eye.db.domain.EyeGoodsAttribute;
import com.eye.db.domain.EyeGoodsAttributeExample;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeGoodsAttributeService {
    @Resource
    private EyeGoodsAttributeMapper goodsAttributeMapper;

    public List<EyeGoodsAttribute> queryByGid(Integer goodsId) {
        EyeGoodsAttributeExample example = new EyeGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        example.setOrderByClause("id DESC");
        return goodsAttributeMapper.selectByExample(example);
    }

    public void add(EyeGoodsAttribute goodsAttribute) {
        goodsAttribute.setAddTime(LocalDateTime.now());
        goodsAttribute.setUpdateTime(LocalDateTime.now());
        goodsAttributeMapper.insertSelective(goodsAttribute);
    }

    public EyeGoodsAttribute findById(Integer id) {
        return goodsAttributeMapper.selectByPrimaryKey(id);
    }

    public void deleteByGid(Integer gid) {
        EyeGoodsAttributeExample example = new EyeGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsAttributeMapper.logicalDeleteByExample(example);
    }

    public void deleteById(Integer id) {
        goodsAttributeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(EyeGoodsAttribute attribute) {
        attribute.setUpdateTime(LocalDateTime.now());
        goodsAttributeMapper.updateByPrimaryKeySelective(attribute);
    }
}
