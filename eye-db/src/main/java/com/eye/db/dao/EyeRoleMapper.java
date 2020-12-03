package com.eye.db.dao;

import com.eye.db.domain.EyeRole;
import com.eye.db.domain.EyeRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeRoleMapper {
    long countByExample(EyeRoleExample example);

    int deleteByExample(EyeRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeRole record);

    int insertSelective(EyeRole record);

    EyeRole selectOneByExample(EyeRoleExample example);

    EyeRole selectOneByExampleSelective(@Param("example") EyeRoleExample example, @Param("selective") EyeRole.Column ... selective);

    List<EyeRole> selectByExampleSelective(@Param("example") EyeRoleExample example, @Param("selective") EyeRole.Column ... selective);

    List<EyeRole> selectByExample(EyeRoleExample example);

    EyeRole selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeRole.Column ... selective);

    EyeRole selectByPrimaryKey(Integer id);

    EyeRole selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeRole record, @Param("example") EyeRoleExample example);

    int updateByExample(@Param("record") EyeRole record, @Param("example") EyeRoleExample example);

    int updateByPrimaryKeySelective(EyeRole record);

    int updateByPrimaryKey(EyeRole record);

    int logicalDeleteByExample(@Param("example") EyeRoleExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}