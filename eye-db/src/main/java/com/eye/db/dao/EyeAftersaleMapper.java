package com.eye.db.dao;

import com.eye.db.domain.EyeAftersale;
import com.eye.db.domain.EyeAftersaleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeAftersaleMapper {
    long countByExample(EyeAftersaleExample example);

    int deleteByExample(EyeAftersaleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeAftersale record);

    int insertSelective(EyeAftersale record);

    EyeAftersale selectOneByExample(EyeAftersaleExample example);

    EyeAftersale selectOneByExampleSelective(@Param("example") EyeAftersaleExample example, @Param("selective") EyeAftersale.Column ... selective);

    List<EyeAftersale> selectByExampleSelective(@Param("example") EyeAftersaleExample example, @Param("selective") EyeAftersale.Column ... selective);

    List<EyeAftersale> selectByExample(EyeAftersaleExample example);

    EyeAftersale selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeAftersale.Column ... selective);

    EyeAftersale selectByPrimaryKey(Integer id);

    EyeAftersale selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeAftersale record, @Param("example") EyeAftersaleExample example);

    int updateByExample(@Param("record") EyeAftersale record, @Param("example") EyeAftersaleExample example);

    int updateByPrimaryKeySelective(EyeAftersale record);

    int updateByPrimaryKey(EyeAftersale record);

    int logicalDeleteByExample(@Param("example") EyeAftersaleExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}