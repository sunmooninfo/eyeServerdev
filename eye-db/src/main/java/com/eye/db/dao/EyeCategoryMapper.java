package com.eye.db.dao;

import com.eye.db.domain.EyeCategory;
import com.eye.db.domain.EyeCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCategoryMapper {
    long countByExample(EyeCategoryExample example);

    int deleteByExample(EyeCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeCategory record);

    int insertSelective(EyeCategory record);

    EyeCategory selectOneByExample(EyeCategoryExample example);

    EyeCategory selectOneByExampleSelective(@Param("example") EyeCategoryExample example, @Param("selective") EyeCategory.Column ... selective);

    List<EyeCategory> selectByExampleSelective(@Param("example") EyeCategoryExample example, @Param("selective") EyeCategory.Column ... selective);

    List<EyeCategory> selectByExample(EyeCategoryExample example);

    EyeCategory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeCategory.Column ... selective);

    EyeCategory selectByPrimaryKey(Integer id);

    EyeCategory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeCategory record, @Param("example") EyeCategoryExample example);

    int updateByExample(@Param("record") EyeCategory record, @Param("example") EyeCategoryExample example);

    int updateByPrimaryKeySelective(EyeCategory record);

    int updateByPrimaryKey(EyeCategory record);

    int logicalDeleteByExample(@Param("example") EyeCategoryExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}