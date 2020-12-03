package com.eye.db.service;

import com.alibaba.druid.util.StringUtils;
import com.eye.db.dao.EyeMemberPackageMapper;
import com.eye.db.domain.EyeMemberPackage;
import com.eye.db.domain.EyeMemberPackageExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeMemberPackageService {

    @Resource
    private EyeMemberPackageMapper packageMapper;

    public void add(EyeMemberPackage memberPackage) {
        memberPackage.setAddTime(LocalDateTime.now());
        memberPackage.setUpdateTime(LocalDateTime.now());
        packageMapper.insertSelective(memberPackage);
    }

    public List<EyeMemberPackage> querySelective(String name, Integer page, Integer limit, String sort, String order) {
        EyeMemberPackageExample example = new EyeMemberPackageExample();
        EyeMemberPackageExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);

        return packageMapper.selectByExample(example);
    }

    public int updateById(EyeMemberPackage memberPackage) {
        memberPackage.setUpdateTime(LocalDateTime.now());
        return packageMapper.updateByPrimaryKeySelective(memberPackage);
    }

    public void deleteById(Integer id) {
        packageMapper.logicalDeleteByPrimaryKey(id);
    }


    public List<EyeMemberPackage> list(){
        EyeMemberPackageExample example = new EyeMemberPackageExample();
        example.or().andDeletedEqualTo(false);
        return packageMapper.selectByExample(example);
    }

    public List<EyeMemberPackage> list(Integer page, Integer limit, String sort, String order) {
        EyeMemberPackageExample example = new EyeMemberPackageExample();
        example.or().andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);

        return packageMapper.selectByExample(example);
    }

    public EyeMemberPackage findById(int menberPackageId) {
        return packageMapper.selectByPrimaryKey(menberPackageId);
    }
}
