package com.eye.db.service;

import com.alibaba.druid.util.StringUtils;
import com.eye.db.dao.EyeRoleMapper;
import com.eye.db.domain.EyeRole;
import com.eye.db.domain.EyeRoleExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EyeRoleService {
    @Resource
    private EyeRoleMapper roleMapper;


    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if(roleIds.length == 0){
            return roles;
        }

        EyeRoleExample example = new EyeRoleExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<EyeRole> roleList = roleMapper.selectByExample(example);

        for(EyeRole role : roleList){
            roles.add(role.getName());
        }

        return roles;

    }

    public List<EyeRole> querySelective(String name, Integer page, Integer limit, String sort, String order) {
        EyeRoleExample example = new EyeRoleExample();
        EyeRoleExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return roleMapper.selectByExample(example);
    }

    public EyeRole findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public void add(EyeRole role) {
        role.setAddTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insertSelective(role);
    }

    public void deleteById(Integer id) {
        roleMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(EyeRole role) {
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public boolean checkExist(String name) {
        EyeRoleExample example = new EyeRoleExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return roleMapper.countByExample(example) != 0;
    }

    public List<EyeRole> queryAll() {
        EyeRoleExample example = new EyeRoleExample();
        example.or().andDeletedEqualTo(false);
        return roleMapper.selectByExample(example);
    }
}
