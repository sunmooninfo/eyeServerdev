package com.eye.db.service;

import com.eye.db.dao.EyeUserMapper;
import com.eye.db.domain.EyeUser;
import com.eye.db.domain.EyeUserExample;
import com.eye.db.domain.UserVo;
import com.eye.db.util.MemberConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeUserService {
    @Resource
    private EyeUserMapper userMapper;

    public EyeUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public UserVo findUserVoById(Integer userId) {
        EyeUser user = findById(userId);
        UserVo userVo = new UserVo();
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }

    public EyeUser queryByOid(String openId) {
        EyeUserExample example = new EyeUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public void add(EyeUser user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    public int updateById(EyeUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public List<EyeUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        EyeUserExample example = new EyeUserExample();
        EyeUserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public int count() {
        EyeUserExample example = new EyeUserExample();
        example.or().andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    public List<EyeUser> queryByUsername(String username) {
        EyeUserExample example = new EyeUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public boolean checkByUsername(String username) {
        EyeUserExample example = new EyeUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.countByExample(example) != 0;
    }

    public List<EyeUser> queryByMobile(String mobile) {
        EyeUserExample example = new EyeUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<EyeUser> queryByOpenid(String openid) {
        EyeUserExample example = new EyeUserExample();
        example.or().andWeixinOpenidEqualTo(openid).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        userMapper.logicalDeleteByPrimaryKey(id);
    }

    public List<EyeUser> queryName() {
        EyeUserExample example = new EyeUserExample();
        example.or().andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<EyeUser> queryAllUser() {
        EyeUserExample example = new EyeUserExample();
        example.or().andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public EyeUser queryByUnionId(String unionId) {
        EyeUserExample example = new EyeUserExample();
        example.or().andUnionIdEqualTo(unionId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public EyeUser queryById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public Integer updatelevel(Integer userId) {
        EyeUser user = new EyeUser();
        user.setId(userId);
        user.setUserLevel(MemberConstant.USER_LEVEL_VIP);
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public List<EyeUser> querySelective(String username, String mobile, Byte userLevel, Integer page, Integer size, String sort, String order) {
        EyeUserExample example = new EyeUserExample();
        EyeUserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileLike("%" +mobile + "%");
        }
        if (!StringUtils.isEmpty(userLevel)) {
            criteria.andUserLevelEqualTo(userLevel);
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public EyeUser find(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
