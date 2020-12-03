package com.eye.db.service;

import com.github.pagehelper.PageHelper;
import com.eye.db.dao.EyeCollectMapper;
import com.eye.db.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EyeCollectService {
    @Resource
    private EyeCollectMapper collectMapper;
    @Autowired
    private EyeUserService userService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeTopicService topicService;


    public int count(int uid, Integer gid) {
        EyeCollectExample example = new EyeCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public List<EyeCollect> queryByType(Integer userId, Byte type, Integer page, Integer limit, String sort, String order) {
        EyeCollectExample example = new EyeCollectExample();
        EyeCollectExample.Criteria criteria = example.createCriteria();

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return collectMapper.selectByExample(example);
    }

    public int countByType(Integer userId, Byte type) {
        EyeCollectExample example = new EyeCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public EyeCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId) {
        EyeCollectExample example = new EyeCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        collectMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(EyeCollect collect) {
        collect.setAddTime(LocalDateTime.now());
        collect.setUpdateTime(LocalDateTime.now());
        return collectMapper.insertSelective(collect);
    }

    public List<NewEyeCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        EyeCollectExample example = new EyeCollectExample();
        EyeCollectExample.Criteria criteria = example.createCriteria();

            if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(valueId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<EyeCollect> collectList = collectMapper.selectByExample(example);
        List<NewEyeCollect> newEyeCollects = new ArrayList<>();
        for (EyeCollect EyeCollect : collectList) {
            NewEyeCollect newEyeCollect = new NewEyeCollect();
            newEyeCollect.setId(EyeCollect.getId());
            newEyeCollect.setUserId(EyeCollect.getUserId());
            newEyeCollect.setValueId(EyeCollect.getValueId());
            newEyeCollect.setAddTime(EyeCollect.getAddTime());
            EyeUser user = userService.queryById(EyeCollect.getUserId());
            newEyeCollect.setUserName(user.getNickname());
            if(EyeCollect.getType() == 0){
                EyeGoods goods = goodsService.queryByiId(EyeCollect.getValueId());
                newEyeCollect.setGoodsName(goods.getName());
            }else{
                EyeTopic topic = topicService.queryById(EyeCollect.getValueId());
                newEyeCollect.setGoodsName(topic.getTitle());
            }
            newEyeCollects.add(newEyeCollect);
        }
        return newEyeCollects;
    }
}
