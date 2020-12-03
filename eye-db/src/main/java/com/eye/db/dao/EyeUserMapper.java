package com.eye.db.dao;

import com.eye.db.domain.EyeUser;
import com.eye.db.domain.EyeUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeUserMapper {
    long countByExample(EyeUserExample example);

    int deleteByExample(EyeUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeUser record);

    int insertSelective(EyeUser record);

    EyeUser selectOneByExample(EyeUserExample example);

    EyeUser selectOneByExampleSelective(@Param("example") EyeUserExample example, @Param("selective") EyeUser.Column ... selective);

    List<EyeUser> selectByExampleSelective(@Param("example") EyeUserExample example, @Param("selective") EyeUser.Column ... selective);

    List<EyeUser> selectByExample(EyeUserExample example);

    EyeUser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeUser.Column ... selective);

    EyeUser selectByPrimaryKey(Integer id);

    EyeUser selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeUser record, @Param("example") EyeUserExample example);

    int updateByExample(@Param("record") EyeUser record, @Param("example") EyeUserExample example);

    int updateByPrimaryKeySelective(EyeUser record);

    int updateByPrimaryKey(EyeUser record);

    int logicalDeleteByExample(@Param("example") EyeUserExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}