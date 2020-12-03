package com.eye.db.service;

import com.eye.db.dao.EyeCompanyMapper;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyeCompany;
import com.eye.db.domain.EyeCompanyExample;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author edc
 * @date 2020/7/21 11:07
 * @description
 */
@Service
public class EyeCompanyService {

    @Resource
    private EyeCompanyMapper eyeCompanyMapper;

//    public int add(EyeCompany company){
//        company.setAddTime(LocalDateTime.now());
//        company.setUpdateTime(LocalDateTime.now());
//        return eyeCompanyMapper.insertSelective(company);
//    }

    /**
     * 获取公司信息（包含logo，sloga与时间）
     */
    public List<EyeCompany> querymessage(Integer[] roleIds, Integer page, Integer limit, String sort, String order){
      //获取当前管理员角色
      EyeAdmin user = (EyeAdmin)SecurityUtils.getSubject().getPrincipal();
      EyeCompanyExample example = new EyeCompanyExample();
      EyeCompanyExample.Criteria criteria = example.createCriteria();
      if(roleIds !=null){
        criteria.andRoleIdsEqualTo(roleIds);
      }
      criteria.andDeletedEqualTo(false);
      example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);

        return eyeCompanyMapper.selectByExample(example);
    }

    /**
     * 添加公司信息
     * @param company
     */
    public void add(EyeCompany company) {
      //获取当前管理员角色
       EyeAdmin user = (EyeAdmin)SecurityUtils.getSubject().getPrincipal();
        company.setRoleIds(user.getRoleIds());
        company.setAddTime(LocalDateTime.now());
        company.setUpdateTime(LocalDateTime.now());
        eyeCompanyMapper.insertSelective(company);
    }

    /**
     * 删除公司信息
     * @param id
     */
    public void deleteById(Integer id) {
        eyeCompanyMapper.logicalDeleteByPrimaryKey(id);
    }


    /**
     * 编辑公司信息
     * @param company
     * @return
     */
    public int updateById(EyeCompany company) {
        company.setUpdateTime(LocalDateTime.now());
        return eyeCompanyMapper.updateByPrimaryKeySelective(company);
    }

  public List<EyeCompany> queryCompanyMessage(String name, Integer page, Integer limit, String sort, String order) {
    //获取当前管理员角色
    EyeAdmin user = (EyeAdmin)SecurityUtils.getSubject().getPrincipal();
    EyeCompanyExample example = new EyeCompanyExample();
    EyeCompanyExample.Criteria criteria = example.createCriteria();
    Integer[] roleIds = user.getRoleIds();
    if(roleIds[0] != 1){
      criteria.andRoleIdsEqualTo(roleIds);
    }
    if(!StringUtils.isEmpty(name)){
      criteria.andNameLike("%"+name+"%");
    }
    criteria.andDeletedEqualTo(false);
    PageHelper.startPage(page, limit);
    example.setOrderByClause(sort + " " + order);
    this.eyeCompanyMapper.selectByExample(example);
    return this.eyeCompanyMapper.selectByExample(example);
  }
}
