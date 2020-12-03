package com.eye.db.dao;

import com.eye.db.domain.EyeArticleAttribute;
import com.eye.db.domain.EyeArticleAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeArticleAttributeMapper {
    long countByExample(EyeArticleAttributeExample example);

    int deleteByExample(EyeArticleAttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeArticleAttribute record);

    int insertSelective(EyeArticleAttribute record);

    EyeArticleAttribute selectOneByExample(EyeArticleAttributeExample example);

    EyeArticleAttribute selectOneByExampleSelective(@Param("example") EyeArticleAttributeExample example, @Param("selective") EyeArticleAttribute.Column ... selective);

    List<EyeArticleAttribute> selectByExampleSelective(@Param("example") EyeArticleAttributeExample example, @Param("selective") EyeArticleAttribute.Column ... selective);

    List<EyeArticleAttribute> selectByExample(EyeArticleAttributeExample example);

    EyeArticleAttribute selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeArticleAttribute.Column ... selective);

    EyeArticleAttribute selectByPrimaryKey(Integer id);

    EyeArticleAttribute selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeArticleAttribute record, @Param("example") EyeArticleAttributeExample example);

    int updateByExample(@Param("record") EyeArticleAttribute record, @Param("example") EyeArticleAttributeExample example);

    int updateByPrimaryKeySelective(EyeArticleAttribute record);

    int updateByPrimaryKey(EyeArticleAttribute record);

    int logicalDeleteByExample(@Param("example") EyeArticleAttributeExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}