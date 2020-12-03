package com.eye.db.dao;

import com.eye.db.domain.EyeLog;
import com.eye.db.domain.EyeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeLogMapper {
    long countByExample(EyeLogExample example);

    int deleteByExample(EyeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeLog record);

    int insertSelective(EyeLog record);

    EyeLog selectOneByExample(EyeLogExample example);

    EyeLog selectOneByExampleSelective(@Param("example") EyeLogExample example, @Param("selective") EyeLog.Column ... selective);

    List<EyeLog> selectByExampleSelective(@Param("example") EyeLogExample example, @Param("selective") EyeLog.Column ... selective);

    List<EyeLog> selectByExample(EyeLogExample example);

    EyeLog selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeLog.Column ... selective);

    EyeLog selectByPrimaryKey(Integer id);

    EyeLog selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeLog record, @Param("example") EyeLogExample example);

    int updateByExample(@Param("record") EyeLog record, @Param("example") EyeLogExample example);

    int updateByPrimaryKeySelective(EyeLog record);

    int updateByPrimaryKey(EyeLog record);

    int logicalDeleteByExample(@Param("example") EyeLogExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}