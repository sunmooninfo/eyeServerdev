package com.eye.db.service;

import com.eye.db.dao.EyeMemberOrderMapper;
import com.eye.db.dao.MemberOrderMapper;
import com.eye.db.domain.EyeMemberOrder;
import com.eye.db.domain.EyeMemberOrderExample;
import com.eye.db.util.MemberOrderUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class EyeMemberOrderService {
    @Resource
    private EyeMemberOrderMapper mapper;
    @Resource
    private MemberOrderMapper memberOrderMapper;

    public int add(EyeMemberOrder memberOrder) {
        memberOrder.setAddTime(LocalDateTime.now());
        memberOrder.setUpdateTime(LocalDateTime.now());
        return mapper.insertSelective(memberOrder);
    }

    public EyeMemberOrder findById(Integer userId, Integer memberOrderId) {
        EyeMemberOrderExample example = new EyeMemberOrderExample();
        example.or().andIdEqualTo(memberOrderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return mapper.selectOneByExample(example);
    }

    public int updateWithOptimisticLocker(EyeMemberOrder memberOrder) {
        LocalDateTime preUpdateTime = memberOrder.getUpdateTime();
        memberOrder.setUpdateTime(LocalDateTime.now());
        return memberOrderMapper.updateWithOptimisticLocker(preUpdateTime, memberOrder);
    }

    public EyeMemberOrder findBySn(String orderSn) {
        EyeMemberOrderExample example = new EyeMemberOrderExample();
        example.or().andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return mapper.selectOneByExample(example);
    }

    public EyeMemberOrder findById(int memberOrderId) {
        return mapper.selectByPrimaryKey(memberOrderId);
    }

    public List<EyeMemberOrder> queryUnpaid(Integer orderUnpaid) {
        EyeMemberOrderExample example = new EyeMemberOrderExample();
        example.or().andStatusEqualTo(MemberOrderUtil.STATUS_CREATE).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }


    public int countByOrderSn(Integer userId, String orderSn) {
        EyeMemberOrderExample example = new EyeMemberOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int) mapper.countByExample(example);
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    // TODO 这里应该产生一个唯一的订单，通过当前时间戳加上用户id加上三个随机数
    public String generateOrderSn(Integer userId) {
        long df = System.currentTimeMillis();
        String now = df + "";
        String orderSn = now + userId + getRandomNum(3);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = now + userId + getRandomNum(3);
        }
        return orderSn;
    }

    public List<EyeMemberOrder> queryByOrderStatus(Integer userId, List<Short> shorts, Integer page, Integer limit, String sort, String order) {
        EyeMemberOrderExample example = new EyeMemberOrderExample();
        example.setOrderByClause(EyeMemberOrder.Column.addTime.desc());
        EyeMemberOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if (shorts != null) {
            criteria.andStatusIn(shorts);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    public void deleteById(Integer memberOrderId) {
        mapper.logicalDeleteByPrimaryKey(memberOrderId);
    }

    public List<EyeMemberOrder> querySelective(Integer userId, String orderSn, LocalDateTime start, LocalDateTime end, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
        EyeMemberOrderExample example = new EyeMemberOrderExample();
        EyeMemberOrderExample.Criteria criteria = example.or();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        if(start != null){
            criteria.andAddTimeGreaterThanOrEqualTo(start);
        }
        if(end != null){
            criteria.andAddTimeLessThanOrEqualTo(end);
        }
        if (orderStatusArray != null && orderStatusArray.size() != 0) {
            criteria.andStatusIn(orderStatusArray);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);

        return mapper.selectByExample(example);
    }

    public List<EyeMemberOrder> queryList(Integer userId, Integer memberPackageId, Short status, Integer page, Integer limit, String sort, String order) {
        EyeMemberOrderExample example = new EyeMemberOrderExample();
        EyeMemberOrderExample.Criteria criteria = example.or();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (memberPackageId != null){
            criteria.andPackageIdEqualTo(memberPackageId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(limit)) {
            PageHelper.startPage(page, limit);
        }

        return mapper.selectByExample(example);
    }
}
