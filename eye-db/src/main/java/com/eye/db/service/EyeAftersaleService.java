package com.eye.db.service;

import com.eye.db.dao.EyeAftersaleMapper;
import com.eye.db.domain.EyeAftersale;
import com.eye.db.domain.EyeAftersaleExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class EyeAftersaleService {
    @Resource
    private EyeAftersaleMapper aftersaleMapper;

    public EyeAftersale findById(Integer id) {
        return aftersaleMapper.selectByPrimaryKey(id);
    }

    public EyeAftersale findById(Integer userId, Integer id) {
        EyeAftersaleExample example = new EyeAftersaleExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return aftersaleMapper.selectOneByExample(example);
    }

    public List<EyeAftersale> queryList(Integer userId, Short status, Integer page, Integer limit, String sort, String order) {
        EyeAftersaleExample example = new EyeAftersaleExample();
        EyeAftersaleExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        else{
            example.setOrderByClause(EyeAftersale.Column.addTime.desc());
        }

        PageHelper.startPage(page, limit);
        return aftersaleMapper.selectByExample(example);
    }

    public List<EyeAftersale> querySelective(Integer orderId, String aftersaleSn, Short status, Integer page, Integer limit, String sort, String order) {
        EyeAftersaleExample example = new EyeAftersaleExample();
        EyeAftersaleExample.Criteria criteria = example.or();
        if (orderId != null) {
            criteria.andOrderIdEqualTo(orderId);
        }
        if (!StringUtils.isEmpty(aftersaleSn)) {
            criteria.andAftersaleSnEqualTo(aftersaleSn);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        else{
            example.setOrderByClause(EyeAftersale.Column.addTime.desc());
        }

        PageHelper.startPage(page, limit);
        return aftersaleMapper.selectByExample(example);
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

    public int countByAftersaleSn(Integer userId, String aftersaleSn) {
        EyeAftersaleExample example = new EyeAftersaleExample();
        example.or().andUserIdEqualTo(userId).andAftersaleSnEqualTo(aftersaleSn).andDeletedEqualTo(false);
        return (int) aftersaleMapper.countByExample(example);
    }

    // TODO 这里应该产生一个唯一的编号，但是实际上这里仍然存在两个售后编号相同的可能性
    public String generateAftersaleSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String aftersaleSn = now + getRandomNum(6);
        while (countByAftersaleSn(userId, aftersaleSn) != 0) {
            aftersaleSn = now + getRandomNum(6);
        }
        return aftersaleSn;
    }

    public void add(EyeAftersale aftersale) {
        aftersale.setAddTime(LocalDateTime.now());
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersaleMapper.insertSelective(aftersale);
    }

    public void deleteByIds(List<Integer> ids) {
        EyeAftersaleExample example = new EyeAftersaleExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        EyeAftersale aftersale = new EyeAftersale();
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersale.setDeleted(true);
        aftersaleMapper.updateByExampleSelective(aftersale, example);
    }

    public void deleteById(Integer id) {
        aftersaleMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByOrderId(Integer userId, Integer orderId) {
        EyeAftersaleExample example = new EyeAftersaleExample();
        example.or().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        EyeAftersale aftersale = new EyeAftersale();
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersale.setDeleted(true);
        aftersaleMapper.updateByExampleSelective(aftersale, example);
    }

    public void updateById(EyeAftersale aftersale) {
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersaleMapper.updateByPrimaryKeySelective(aftersale);
    }

    public EyeAftersale findByOrderId(Integer userId, Integer orderId) {
        EyeAftersaleExample example = new EyeAftersaleExample();
        example.or().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return aftersaleMapper.selectOneByExample(example);
    }
}
