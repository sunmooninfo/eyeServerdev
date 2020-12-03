package com.eye.db.dao;

import com.eye.db.domain.EyeArticleCategory;
import com.eye.db.domain.EyeArticleCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeArticleCategoryMapper {
    long countByExample(EyeArticleCategoryExample example);

    int deleteByExample(EyeArticleCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeArticleCategory record);

    int insertSelective(EyeArticleCategory record);

    EyeArticleCategory selectOneByExample(EyeArticleCategoryExample example);

    EyeArticleCategory selectOneByExampleSelective(@Param("example") EyeArticleCategoryExample example, @Param("selective") EyeArticleCategory.Column ... selective);

    List<EyeArticleCategory> selectByExampleSelective(@Param("example") EyeArticleCategoryExample example, @Param("selective") EyeArticleCategory.Column ... selective);

    List<EyeArticleCategory> selectByExample(EyeArticleCategoryExample example);

    EyeArticleCategory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeArticleCategory.Column ... selective);

    EyeArticleCategory selectByPrimaryKey(Integer id);

    EyeArticleCategory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeArticleCategory record, @Param("example") EyeArticleCategoryExample example);

    int updateByExample(@Param("record") EyeArticleCategory record, @Param("example") EyeArticleCategoryExample example);

    int updateByPrimaryKeySelective(EyeArticleCategory record);

    int updateByPrimaryKey(EyeArticleCategory record);

    int logicalDeleteByExample(@Param("example") EyeArticleCategoryExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}