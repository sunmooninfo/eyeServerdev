package com.eye.db.service;

import com.eye.db.dao.EyeRegionMapper;
import com.eye.db.domain.EyeRegion;
import com.eye.db.domain.EyeRegionExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EyeRegionService {

    @Resource
    private EyeRegionMapper regionMapper;

    public List<EyeRegion> getAll(){
        EyeRegionExample example = new EyeRegionExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return regionMapper.selectByExample(example);
    }

    public List<EyeRegion> queryByPid(Integer parentId) {
        EyeRegionExample example = new EyeRegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public EyeRegion findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<EyeRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        EyeRegionExample example = new EyeRegionExample();
        EyeRegionExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }

}
