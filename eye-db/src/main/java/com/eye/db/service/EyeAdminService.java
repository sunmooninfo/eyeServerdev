package com.eye.db.service;

import com.eye.db.dao.EyeAdminMapper;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyeAdminExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EyeAdminService {
    private final EyeAdmin.Column[] result = new EyeAdmin.Column[]{EyeAdmin.Column.id, EyeAdmin.Column.username, EyeAdmin.Column.avatar, EyeAdmin.Column.roleIds};
    @Resource
    private EyeAdminMapper adminMapper;

    public List<EyeAdmin> findAdmin(String username) {
        EyeAdminExample example = new EyeAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    public EyeAdmin findAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public List<EyeAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        EyeAdminExample example = new EyeAdminExample();
        EyeAdminExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int updateById(EyeAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(EyeAdmin admin) {
        admin.setAddTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insertSelective(admin);
    }

    public EyeAdmin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }

    public List<EyeAdmin> all() {
        EyeAdminExample example = new EyeAdminExample();
        example.or().andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }
}
