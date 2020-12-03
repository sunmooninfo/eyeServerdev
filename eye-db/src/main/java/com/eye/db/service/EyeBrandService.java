package com.eye.db.service;

import com.eye.db.dao.EyeBrandMapper;
import com.eye.db.domain.EyeBrand;
import com.eye.db.domain.EyeBrand.Column;
import com.eye.db.domain.EyeBrandExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeBrandService {
    @Resource
    private EyeBrandMapper brandMapper;
    private Column[] columns = new Column[]{Column.id, Column.name, Column.desc, Column.picUrl, Column.floorPrice};

    public List<EyeBrand> query(Integer page, Integer limit, String sort, String order) {
        EyeBrandExample example = new EyeBrandExample();
        example.or().andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return brandMapper.selectByExampleSelective(example, columns);
    }

    public List<EyeBrand> query(Integer page, Integer limit) {
        return query(page, limit, null, null);
    }

    public EyeBrand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<EyeBrand> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        EyeBrandExample example = new EyeBrandExample();
        EyeBrandExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return brandMapper.selectByExample(example);
    }

    public int updateById(EyeBrand brand) {
        brand.setUpdateTime(LocalDateTime.now());
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    public void deleteById(Integer id) {
        brandMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeBrand brand) {
        brand.setAddTime(LocalDateTime.now());
        brand.setUpdateTime(LocalDateTime.now());
        brandMapper.insertSelective(brand);
    }

    public List<EyeBrand> all() {
        EyeBrandExample example = new EyeBrandExample();
        example.or().andDeletedEqualTo(false);
        return brandMapper.selectByExample(example);
    }
}
