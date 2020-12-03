package com.eye.db.dao;

import com.eye.db.domain.EyeSystem;
import com.eye.db.domain.EyeSystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeSystemMapper {
    long countByExample(EyeSystemExample example);

    int deleteByExample(EyeSystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeSystem record);

    int insertSelective(EyeSystem record);

    EyeSystem selectOneByExample(EyeSystemExample example);

    EyeSystem selectOneByExampleSelective(@Param("example") EyeSystemExample example, @Param("selective") EyeSystem.Column ... selective);

    EyeSystem selectOneByExampleWithBLOBs(EyeSystemExample example);

    List<EyeSystem> selectByExampleSelective(@Param("example") EyeSystemExample example, @Param("selective") EyeSystem.Column ... selective);

    List<EyeSystem> selectByExampleWithBLOBs(EyeSystemExample example);

    List<EyeSystem> selectByExample(EyeSystemExample example);

    EyeSystem selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeSystem.Column ... selective);

    EyeSystem selectByPrimaryKey(Integer id);

    EyeSystem selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeSystem record, @Param("example") EyeSystemExample example);

    int updateByExampleWithBLOBs(@Param("record") EyeSystem record, @Param("example") EyeSystemExample example);

    int updateByExample(@Param("record") EyeSystem record, @Param("example") EyeSystemExample example);

    int updateByPrimaryKeySelective(EyeSystem record);

    int updateByPrimaryKeyWithBLOBs(EyeSystem record);

    int updateByPrimaryKey(EyeSystem record);

    int logicalDeleteByExample(@Param("example") EyeSystemExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}