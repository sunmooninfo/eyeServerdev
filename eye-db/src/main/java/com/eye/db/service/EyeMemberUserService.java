package com.eye.db.service;

import com.eye.db.dao.EyeMemberUserMapper;
import com.eye.db.domain.EyeMemberUser;
import com.eye.db.domain.EyeMemberUserExample;
import com.eye.db.util.MemberConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeMemberUserService {

    @Resource
    private EyeMemberUserMapper userMapper;

    public List<EyeMemberUser> queryList(Integer userId, Short status, Integer page, Integer size, String sort, String order) {
        EyeMemberUserExample example = new EyeMemberUserExample();
        EyeMemberUserExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        criteria.andStatusEqualTo(MemberConstant.USER_STATUS_ON);

        criteria.andDeteledEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(size)) {
            PageHelper.startPage(page, size);
        }
        return userMapper.selectByExample(example);
    }

    public EyeMemberUser findByUserID(Integer userId) {
        EyeMemberUserExample example = new EyeMemberUserExample();
        example.or().andUserIdEqualTo(userId).andStatusEqualTo(MemberConstant.USER_STATUS_ON).andDeteledEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public int add(EyeMemberUser eyeMemberUser) {
        return userMapper.insertSelective(eyeMemberUser);
    }

    public int update(EyeMemberUser eyeMemberUser) {
        return userMapper.updateByPrimaryKeySelective(eyeMemberUser);
    }

    public List<EyeMemberUser> queryExpired() {
        EyeMemberUserExample example = new EyeMemberUserExample();
        //到期时间小于andEndTimeLessThan()现在时间
        example.or().andStatusEqualTo(MemberConstant.USER_STATUS_ON).andEndTimeLessThan(LocalDateTime.now()).andDeteledEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public int updateById(EyeMemberUser memberUser) {

        memberUser.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(memberUser);
    }

    public List<EyeMemberUser> querySelective(Integer userId, Integer page, Integer limit, String sort, String order) {
        EyeMemberUserExample example = new EyeMemberUserExample();
        EyeMemberUserExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        criteria.andDeteledEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(limit)) {
            PageHelper.startPage(page, limit);
        }
        return userMapper.selectByExample(example);
    }
}
