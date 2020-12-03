package com.eye.db.dao;

import com.eye.db.domain.EyeToolAdmin;
import com.eye.db.domain.EyeToolAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeToolAdminMapper {
    long countByExample(EyeToolAdminExample example);

    int deleteByExample(EyeToolAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeToolAdmin record);

    int insertSelective(EyeToolAdmin record);

    EyeToolAdmin selectOneByExample(EyeToolAdminExample example);

    EyeToolAdmin selectOneByExampleSelective(@Param("example") EyeToolAdminExample example, @Param("selective") EyeToolAdmin.Column ... selective);

    List<EyeToolAdmin> selectByExampleSelective(@Param("example") EyeToolAdminExample example, @Param("selective") EyeToolAdmin.Column ... selective);

    List<EyeToolAdmin> selectByExample(EyeToolAdminExample example);

    EyeToolAdmin selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeToolAdmin.Column ... selective);

    EyeToolAdmin selectByPrimaryKey(Integer id);

    EyeToolAdmin selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeToolAdmin record, @Param("example") EyeToolAdminExample example);

    int updateByExample(@Param("record") EyeToolAdmin record, @Param("example") EyeToolAdminExample example);

    int updateByPrimaryKeySelective(EyeToolAdmin record);

    int updateByPrimaryKey(EyeToolAdmin record);

    int logicalDeleteByExample(@Param("example") EyeToolAdminExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}