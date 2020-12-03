package com.eye.db.dao;

import com.eye.db.domain.EyeIntegral;
import com.eye.db.domain.EyeIntegralExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeIntegralMapper {
    long countByExample(EyeIntegralExample example);

    int deleteByExample(EyeIntegralExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeIntegral record);

    int insertSelective(EyeIntegral record);

    EyeIntegral selectOneByExample(EyeIntegralExample example);

    EyeIntegral selectOneByExampleSelective(@Param("example") EyeIntegralExample example, @Param("selective") EyeIntegral.Column ... selective);

    List<EyeIntegral> selectByExampleSelective(@Param("example") EyeIntegralExample example, @Param("selective") EyeIntegral.Column ... selective);

    List<EyeIntegral> selectByExample(EyeIntegralExample example);

    EyeIntegral selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeIntegral.Column ... selective);

    EyeIntegral selectByPrimaryKey(Integer id);

    EyeIntegral selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeIntegral record, @Param("example") EyeIntegralExample example);

    int updateByExample(@Param("record") EyeIntegral record, @Param("example") EyeIntegralExample example);

    int updateByPrimaryKeySelective(EyeIntegral record);

    int updateByPrimaryKey(EyeIntegral record);

    int logicalDeleteByExample(@Param("example") EyeIntegralExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}