package com.eye.db.service;

import com.eye.db.dao.EyeToolAccountMapper;
import com.eye.db.domain.EyeToolAccount;
import com.eye.db.domain.EyeToolAccountExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EyeToolAccountService {

    @Autowired
    private EyeToolAccountMapper accountMapper;

    public List<EyeToolAccount> querySelective(Integer page, Integer limit, String sort, String order) {
        EyeToolAccountExample example = new EyeToolAccountExample();
        example.or().andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return accountMapper.selectByExample(example);
    }

    public int count() {
        EyeToolAccountExample example = new EyeToolAccountExample();
        example.or().andDeletedEqualTo(false);
        return (int) accountMapper.countByExample(example);
    }
}
