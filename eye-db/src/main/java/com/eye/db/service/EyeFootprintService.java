package com.eye.db.service;

import com.eye.db.dao.EyeFootprintMapper;
import com.eye.db.domain.EyeFootprint;
import com.eye.db.domain.EyeFootprintExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeFootprintService {
    @Resource
    private EyeFootprintMapper footprintMapper;

    public List<EyeFootprint> queryByAddTime(Integer userId, Integer page, Integer size) {
        EyeFootprintExample example = new EyeFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause(EyeFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public EyeFootprint findById(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    public EyeFootprint findById(Integer userId, Integer id) {
        EyeFootprintExample example = new EyeFootprintExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return footprintMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        footprintMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeFootprint footprint) {
        footprint.setAddTime(LocalDateTime.now());
        footprint.setUpdateTime(LocalDateTime.now());
        footprintMapper.insertSelective(footprint);
    }

    public List<EyeFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        EyeFootprintExample example = new EyeFootprintExample();
        EyeFootprintExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }
}
