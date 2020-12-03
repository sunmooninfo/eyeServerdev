package com.eye.db.service;

import com.eye.db.dao.BrandProductMapper;
import com.eye.db.dao.EyeBrandWebMapper;
import com.eye.db.domain.EyeBrandWeb;
import com.eye.db.domain.EyeBrandWebExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeBrandWebService {
    @Resource
    private EyeBrandWebMapper brandMapper;
    @Resource
    private BrandProductMapper productMapper;

    public List<EyeBrandWeb> querySelective(String name, String company, Integer page, Integer limit, String order) {
        EyeBrandWebExample example = new EyeBrandWebExample();
        EyeBrandWebExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (!StringUtils.isEmpty(company)) {
            criteria.andCompanyLike("%" + company + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(order)) {
            example.setOrderByClause("clicks" + " " + order);
        }

        PageHelper.startPage(page, limit);

        return brandMapper.selectByExampleWithBLOBs(example);
    }




    public void add(EyeBrandWeb brand) {

        brand.setAddTime(LocalDateTime.now());
        brand.setUpdateTime(LocalDateTime.now());
        brandMapper.insertSelective(brand);

    }

    public void update(EyeBrandWeb brand) {
        brand.setUpdateTime(LocalDateTime.now());
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    public void delete(Integer id) {
        brandMapper.logicalDeleteByPrimaryKey(id);
    }

    public EyeBrandWeb findById(Integer id) {

        EyeBrandWebExample example = new EyeBrandWebExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return brandMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<EyeBrandWeb> querylist(Integer userId, String name, String company, Integer page, Integer limit, String order) {

        EyeBrandWebExample example = new EyeBrandWebExample();
        EyeBrandWebExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (!StringUtils.isEmpty(company)) {
            criteria.andCompanyLike("%" + company + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(order)) {
            example.setOrderByClause("clicks" + " " + order);
        }

        PageHelper.startPage(page, limit);

        return brandMapper.selectByExampleWithBLOBs(example);
    }

    public boolean checkExistByName(String name) {
        EyeBrandWebExample example = new EyeBrandWebExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return brandMapper.countByExample(example) != 0;
    }

    public List<EyeBrandWeb> queryName() {
        List<EyeBrandWeb> list = productMapper.queryName();
        return list;
    }
}
