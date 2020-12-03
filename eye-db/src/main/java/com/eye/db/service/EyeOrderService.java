package com.eye.db.service;

import com.eye.db.dao.EyeOrderMapper;
import com.eye.db.dao.OrderMapper;
import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderExample;
import com.eye.db.util.OrderUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class EyeOrderService {
    @Resource
    private EyeOrderMapper eyeOrderMapper;
    @Resource
    private OrderMapper orderMapper;

    public int add(EyeOrder order) {
        order.setAddTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return eyeOrderMapper.insertSelective(order);
    }

    public int count(Integer userId) {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return (int) eyeOrderMapper.countByExample(example);
    }

    public EyeOrder findById(Integer orderId) {
        return eyeOrderMapper.selectByPrimaryKey(orderId);
    }

    public EyeOrder findById(Integer userId, Integer orderId) {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return eyeOrderMapper.selectOneByExample(example);
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

    public int countByOrderSn(Integer userId, String orderSn) {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int) eyeOrderMapper.countByExample(example);
    }

    // TODO 这里应该产生一个唯一的订单，但是实际上这里仍然存在两个订单相同的可能性
    public String generateOrderSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = now + getRandomNum(6);
        }
        return orderSn;
    }

    public List<EyeOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
        EyeOrderExample example = new EyeOrderExample();
        example.setOrderByClause(EyeOrder.Column.addTime.desc());
        EyeOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return eyeOrderMapper.selectByExample(example);
    }

    public List<EyeOrder> querySelective(Integer userId, String orderSn, LocalDateTime start, LocalDateTime end, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
        EyeOrderExample example = new EyeOrderExample();
        EyeOrderExample.Criteria criteria = example.createCriteria();

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
            criteria.andOrderStatusIn(orderStatusArray);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return eyeOrderMapper.selectByExample(example);
    }

    public int updateWithOptimisticLocker(EyeOrder order) {
        LocalDateTime preUpdateTime = order.getUpdateTime();
        order.setUpdateTime(LocalDateTime.now());
        return orderMapper.updateWithOptimisticLocker(preUpdateTime, order);
    }

    public void deleteById(Integer id) {
        eyeOrderMapper.logicalDeleteByPrimaryKey(id);
    }

    public int count() {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andDeletedEqualTo(false);
        return (int) eyeOrderMapper.countByExample(example);
    }

    public List<EyeOrder> queryUnpaid(int minutes) {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_CREATE).andDeletedEqualTo(false);
        return eyeOrderMapper.selectByExample(example);
    }

    public List<EyeOrder> queryUnconfirm(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        EyeOrderExample example = new EyeOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_SHIP).andShipTimeLessThan(expired).andDeletedEqualTo(false);
        return eyeOrderMapper.selectByExample(example);
    }

    public EyeOrder findBySn(String orderSn) {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return eyeOrderMapper.selectOneByExample(example);
    }

    public Map<Object, Object> orderInfo(Integer userId) {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<EyeOrder> orders = eyeOrderMapper.selectByExampleSelective(example, EyeOrder.Column.orderStatus, EyeOrder.Column.comments);

        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;
        for (EyeOrder order : orders) {
            if (OrderUtil.isCreateStatus(order)) {
                unpaid++;
            } else if (OrderUtil.isPayStatus(order)) {
                unship++;
            } else if (OrderUtil.isShipStatus(order)) {
                unrecv++;
            } else if (OrderUtil.isConfirmStatus(order) || OrderUtil.isAutoConfirmStatus(order)) {
                uncomment += order.getComments();
            } else {
                // do nothing
            }
        }

        Map<Object, Object> orderInfo = new HashMap<Object, Object>();
        orderInfo.put("unpaid", unpaid);
        orderInfo.put("unship", unship);
        orderInfo.put("unrecv", unrecv);
        orderInfo.put("uncomment", uncomment);
        return orderInfo;

    }

    public List<EyeOrder> queryComment(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        EyeOrderExample example = new EyeOrderExample();
        example.or().andCommentsGreaterThan((short) 0).andConfirmTimeLessThan(expired).andDeletedEqualTo(false);
        return eyeOrderMapper.selectByExample(example);
    }

    public void updateAftersaleStatus(Integer orderId, Short statusReject) {
        EyeOrder order = new EyeOrder();
        order.setId(orderId);
        order.setAftersaleStatus(statusReject);
        order.setUpdateTime(LocalDateTime.now());
        eyeOrderMapper.updateByPrimaryKeySelective(order);
    }

    public List<EyeOrder> queryPayOrder(Integer userId) {
        EyeOrderExample example = new EyeOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderStatusEqualTo(OrderUtil.STATUS_PAY).andDeletedEqualTo(false);
        return eyeOrderMapper.selectByExample(example);
    }
}
