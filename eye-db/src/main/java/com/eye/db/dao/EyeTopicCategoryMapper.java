package com.eye.db.dao;

import com.eye.db.domain.EyeTopicCategory;
import com.eye.db.domain.EyeTopicCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeTopicCategoryMapper {
    long countByExample(EyeTopicCategoryExample example);

    int deleteByExample(EyeTopicCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeTopicCategory record);

    int insertSelective(EyeTopicCategory record);

    EyeTopicCategory selectOneByExample(EyeTopicCategoryExample example);

    EyeTopicCategory selectOneByExampleSelective(@Param("example") EyeTopicCategoryExample example, @Param("selective") EyeTopicCategory.Column ... selective);

    List<EyeTopicCategory> selectByExampleSelective(@Param("example") EyeTopicCategoryExample example, @Param("selective") EyeTopicCategory.Column ... selective);

    List<EyeTopicCategory> selectByExample(EyeTopicCategoryExample example);

    EyeTopicCategory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeTopicCategory.Column ... selective);

    EyeTopicCategory selectByPrimaryKey(Integer id);

    EyeTopicCategory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeTopicCategory record, @Param("example") EyeTopicCategoryExample example);

    int updateByExample(@Param("record") EyeTopicCategory record, @Param("example") EyeTopicCategoryExample example);

    int updateByPrimaryKeySelective(EyeTopicCategory record);

    int updateByPrimaryKey(EyeTopicCategory record);

    int logicalDeleteByExample(@Param("example") EyeTopicCategoryExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}