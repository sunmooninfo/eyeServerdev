package com.eye.db.service;

import com.eye.db.dao.EyePermissionMapper;
import com.eye.db.domain.EyePermission;
import com.eye.db.domain.EyePermissionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EyePermissionService {
    @Resource
    private EyePermissionMapper permissionMapper;

    public Set<String> queryByRoleIds(Integer[] roleIds) {
        Set<String> permissions = new HashSet<String>();
        if(roleIds.length == 0){
            return permissions;
        }

        EyePermissionExample example = new EyePermissionExample();
        example.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false);
        List<EyePermission> permissionList = permissionMapper.selectByExample(example);

        for(EyePermission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }


    public Set<String> queryByRoleId(Integer roleId) {
        Set<String> permissions = new HashSet<String>();
        if(roleId == null){
            return permissions;
        }

        EyePermissionExample example = new EyePermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<EyePermission> permissionList = permissionMapper.selectByExample(example);

        for(EyePermission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }

    public boolean checkSuperPermission(Integer roleId) {
        if(roleId == null){
            return false;
        }

        EyePermissionExample example = new EyePermissionExample();
        example.or().andRoleIdEqualTo(roleId).andPermissionEqualTo("*").andDeletedEqualTo(false);
        return permissionMapper.countByExample(example) != 0;
    }

    public void deleteByRoleId(Integer roleId) {
        EyePermissionExample example = new EyePermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        permissionMapper.logicalDeleteByExample(example);
    }

    public void add(EyePermission eyePermission) {
        eyePermission.setAddTime(LocalDateTime.now());
        eyePermission.setUpdateTime(LocalDateTime.now());
        permissionMapper.insertSelective(eyePermission);
    }
}
