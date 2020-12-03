package com.eye.db.service;

import com.eye.db.dao.EyeIntegralMapper;
import com.eye.db.domain.EyeIntegral;
import com.eye.db.domain.EyeIntegralExample;
import com.eye.db.util.MemberConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeIntegralService {

    @Resource
    private EyeIntegralMapper integralMapper;

    //添加会员id到会员积分
    public void create(EyeIntegral integral) {
        integral.setStatus(MemberConstant.INTEGRAL_STATUS_ON);
        integral.setAddTime(LocalDateTime.now());
        integral.setUpdateTime(LocalDateTime.now());
        integralMapper.insertSelective(integral);

    }

    public EyeIntegral findByUserId(Integer userId) {
        EyeIntegralExample example = new EyeIntegralExample();
        example.or().andUserIdEqualTo(userId).andStatusEqualTo(MemberConstant.INTEGRAL_STATUS_ON).andDeletedEqualTo(false);
        return integralMapper.selectOneByExample(example);
    }

    public EyeIntegral findByUser(Integer userId){
        EyeIntegralExample example = new EyeIntegralExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return integralMapper.selectOneByExample(example);
    }

    public void update(EyeIntegral uloveIntegral) {
        uloveIntegral.setUpdateTime(LocalDateTime.now());
        integralMapper.updateByPrimaryKeySelective(uloveIntegral);
    }

    public EyeIntegral findById(Integer integralId) {
        EyeIntegralExample example = new EyeIntegralExample();
        example.or().andIdEqualTo(integralId).andStatusEqualTo(MemberConstant.INTEGRAL_STATUS_ON).andDeletedEqualTo(false);
        return integralMapper.selectOneByExample(example);
    }

    public List<EyeIntegral> querySelective(Integer page, Integer limit, String sort, String order) {
        EyeIntegralExample example = new EyeIntegralExample();
        example.setOrderByClause(sort + " " + order);
        example.or().andDeletedEqualTo(false);
        PageHelper.startPage(page, limit);
        return integralMapper.selectByExample(example);
    }

    //确认收货之后增加积分
    public void updateIntegration(Integer userId, int actualPrice) {
        EyeIntegral uloveIntegral = findByUserId(userId);
        Integer integration = uloveIntegral.getIntegration();
        uloveIntegral.setIntegration(integration + actualPrice);
        uloveIntegral.setUpdateTime(LocalDateTime.now());
        integralMapper.updateByPrimaryKeySelective(uloveIntegral);
    }
}
