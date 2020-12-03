package com.eye.db.service;

import com.eye.db.dao.EyeAddressMapper;
import com.eye.db.domain.EyeAddress;
import com.eye.db.domain.EyeAddressExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeAddressService {
    @Resource
    private EyeAddressMapper addressMapper;

    public List<EyeAddress> queryByUid(Integer uid) {
        EyeAddressExample example = new EyeAddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    public EyeAddress query(Integer userId, Integer id) {
        EyeAddressExample example = new EyeAddressExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public int add(EyeAddress address) {
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.insertSelective(address);
    }

    public int update(EyeAddress address) {
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    public void delete(Integer id) {
        addressMapper.logicalDeleteByPrimaryKey(id);
    }

    public EyeAddress findDefault(Integer userId) {
        EyeAddressExample example = new EyeAddressExample();
        example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public void resetDefault(Integer userId) {
        EyeAddress address = new EyeAddress();
        address.setIsDefault(false);
        address.setUpdateTime(LocalDateTime.now());
        EyeAddressExample example = new EyeAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        addressMapper.updateByExampleSelective(address, example);
    }

    public List<EyeAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        EyeAddressExample example = new EyeAddressExample();
        EyeAddressExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return addressMapper.selectByExample(example);
    }

}
