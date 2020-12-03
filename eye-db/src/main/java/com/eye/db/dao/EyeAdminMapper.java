package com.eye.db.dao;

import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyeAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeAdminMapper {
    long countByExample(EyeAdminExample example);

    int deleteByExample(EyeAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeAdmin record);

    int insertSelective(EyeAdmin record);

    EyeAdmin selectOneByExample(EyeAdminExample example);

    EyeAdmin selectOneByExampleSelective(@Param("example") EyeAdminExample example, @Param("selective") EyeAdmin.Column ... selective);

    List<EyeAdmin> selectByExampleSelective(@Param("example") EyeAdminExample example, @Param("selective") EyeAdmin.Column ... selective);

    List<EyeAdmin> selectByExample(EyeAdminExample example);

    EyeAdmin selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeAdmin.Column ... selective);

    EyeAdmin selectByPrimaryKey(Integer id);

    EyeAdmin selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeAdmin record, @Param("example") EyeAdminExample example);

    int updateByExample(@Param("record") EyeAdmin record, @Param("example") EyeAdminExample example);

    int updateByPrimaryKeySelective(EyeAdmin record);

    int updateByPrimaryKey(EyeAdmin record);

    int logicalDeleteByExample(@Param("example") EyeAdminExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}