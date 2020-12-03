package com.eye.db.dao;

import com.eye.db.domain.EyeGrouponRules;
import com.eye.db.domain.EyeGrouponRulesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGrouponRulesMapper {
    long countByExample(EyeGrouponRulesExample example);

    int deleteByExample(EyeGrouponRulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGrouponRules record);

    int insertSelective(EyeGrouponRules record);

    EyeGrouponRules selectOneByExample(EyeGrouponRulesExample example);

    EyeGrouponRules selectOneByExampleSelective(@Param("example") EyeGrouponRulesExample example, @Param("selective") EyeGrouponRules.Column ... selective);

    List<EyeGrouponRules> selectByExampleSelective(@Param("example") EyeGrouponRulesExample example, @Param("selective") EyeGrouponRules.Column ... selective);

    List<EyeGrouponRules> selectByExample(EyeGrouponRulesExample example);

    EyeGrouponRules selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGrouponRules.Column ... selective);

    EyeGrouponRules selectByPrimaryKey(Integer id);

    EyeGrouponRules selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGrouponRules record, @Param("example") EyeGrouponRulesExample example);

    int updateByExample(@Param("record") EyeGrouponRules record, @Param("example") EyeGrouponRulesExample example);

    int updateByPrimaryKeySelective(EyeGrouponRules record);

    int updateByPrimaryKey(EyeGrouponRules record);

    int logicalDeleteByExample(@Param("example") EyeGrouponRulesExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}