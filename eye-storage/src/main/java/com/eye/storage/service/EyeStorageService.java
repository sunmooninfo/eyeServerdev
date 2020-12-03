package com.eye.storage.service;

import com.eye.db.dao.EyeStorageMapper;
import com.eye.db.domain.EyeStorage;
import com.eye.db.domain.EyeStorageExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeStorageService {
    @Autowired
    private EyeStorageMapper storageMapper;

    public void deleteByKey(String key) {
        EyeStorageExample example = new EyeStorageExample();
        example.or().andKeyEqualTo(key);
        storageMapper.logicalDeleteByExample(example);
    }

    public void add(EyeStorage storageInfo) {
        storageInfo.setAddTime(LocalDateTime.now());
        storageInfo.setUpdateTime(LocalDateTime.now());
        storageMapper.insertSelective(storageInfo);
    }

    public EyeStorage findByKey(String key) {
        EyeStorageExample example = new EyeStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public int update(EyeStorage storageInfo) {
        storageInfo.setUpdateTime(LocalDateTime.now());
        return storageMapper.updateByPrimaryKeySelective(storageInfo);
    }

    public EyeStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<EyeStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        EyeStorageExample example = new EyeStorageExample();
        EyeStorageExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }
}
