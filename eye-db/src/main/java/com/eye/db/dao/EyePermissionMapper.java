package com.eye.db.dao;

import com.eye.db.domain.EyePermission;
import com.eye.db.domain.EyePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyePermissionMapper {
    long countByExample(EyePermissionExample example);

    int deleteByExample(EyePermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyePermission record);

    int insertSelective(EyePermission record);

    EyePermission selectOneByExample(EyePermissionExample example);

    EyePermission selectOneByExampleSelective(@Param("example") EyePermissionExample example, @Param("selective") EyePermission.Column ... selective);

    List<EyePermission> selectByExampleSelective(@Param("example") EyePermissionExample example, @Param("selective") EyePermission.Column ... selective);

    List<EyePermission> selectByExample(EyePermissionExample example);

    EyePermission selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyePermission.Column ... selective);

    EyePermission selectByPrimaryKey(Integer id);

    EyePermission selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyePermission record, @Param("example") EyePermissionExample example);

    int updateByExample(@Param("record") EyePermission record, @Param("example") EyePermissionExample example);

    int updateByPrimaryKeySelective(EyePermission record);

    int updateByPrimaryKey(EyePermission record);

    int logicalDeleteByExample(@Param("example") EyePermissionExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}