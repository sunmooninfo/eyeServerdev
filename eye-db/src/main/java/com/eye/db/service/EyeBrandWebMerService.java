package com.eye.db.service;

import com.eye.db.dao.EyeBrandWebMerMapper;
import com.eye.db.domain.EyeBrandWebMer;
import com.eye.db.domain.EyeBrandWebMerExample;
import com.github.pagehelper.PageHelper;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeBrandWebMerService {

    @Resource
    private EyeBrandWebMerMapper merchantsMapper;

    public void add(EyeBrandWebMer merchants) {

        merchants.setAddTime(LocalDateTime.now());
        merchants.setUpdateTime(LocalDateTime.now());
        merchantsMapper.insertSelective(merchants);

    }

    public void update(EyeBrandWebMer merchants) {
        merchants.setUpdateTime(LocalDateTime.now());
        merchantsMapper.updateByPrimaryKeySelective(merchants);
    }

    public EyeBrandWebMer findById(Integer id) {
        return merchantsMapper.selectByPrimaryKey(id);
    }

    public void delete(Integer id) {
        merchantsMapper.logicalDeleteByPrimaryKey(id);
    }

    public EyeBrandWebMer findByBrandId(Integer bradnId) {
        EyeBrandWebMerExample example = new EyeBrandWebMerExample();
        example.or().andBrandIdEqualTo(bradnId).andDeletedEqualTo(false);

        return merchantsMapper.selectOneByExample(example);
    }

    public List<EyeBrandWebMer> querylist(Integer brandId, String name, String company, Integer page, Integer limit, String sort, String order) {

        EyeBrandWebMerExample example = new EyeBrandWebMerExample();
        EyeBrandWebMerExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(brandId)){
            criteria.andBrandIdEqualTo(brandId);
        }

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (!StringUtils.isEmpty(company)) {
            criteria.andCompanyLike("%" + company + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);

        return merchantsMapper.selectByExample(example);

    }

    public List<EyeBrandWebMer> querySelective(String name, String company, Integer page, Integer limit, String sort, String order) {

        EyeBrandWebMerExample example = new EyeBrandWebMerExample();
        EyeBrandWebMerExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (!StringUtils.isEmpty(company)) {
            criteria.andCompanyLike("%" + company + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);

        return merchantsMapper.selectByExample(example);

    }

    public void deleteByBrandId(Integer brandId) {
        EyeBrandWebMerExample example = new EyeBrandWebMerExample();
        example.or().andBrandIdEqualTo(brandId);
        merchantsMapper.logicalDeleteByExample(example);
    }
}
