package com.eye.db.dao;

import com.eye.db.domain.EyeNoticeAdmin;
import com.eye.db.domain.EyeNoticeAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeNoticeAdminMapper {
    long countByExample(EyeNoticeAdminExample example);

    int deleteByExample(EyeNoticeAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeNoticeAdmin record);

    int insertSelective(EyeNoticeAdmin record);

    EyeNoticeAdmin selectOneByExample(EyeNoticeAdminExample example);

    EyeNoticeAdmin selectOneByExampleSelective(@Param("example") EyeNoticeAdminExample example, @Param("selective") EyeNoticeAdmin.Column ... selective);

    List<EyeNoticeAdmin> selectByExampleSelective(@Param("example") EyeNoticeAdminExample example, @Param("selective") EyeNoticeAdmin.Column ... selective);

    List<EyeNoticeAdmin> selectByExample(EyeNoticeAdminExample example);

    EyeNoticeAdmin selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeNoticeAdmin.Column ... selective);

    EyeNoticeAdmin selectByPrimaryKey(Integer id);

    EyeNoticeAdmin selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeNoticeAdmin record, @Param("example") EyeNoticeAdminExample example);

    int updateByExample(@Param("record") EyeNoticeAdmin record, @Param("example") EyeNoticeAdminExample example);

    int updateByPrimaryKeySelective(EyeNoticeAdmin record);

    int updateByPrimaryKey(EyeNoticeAdmin record);

    int logicalDeleteByExample(@Param("example") EyeNoticeAdminExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}