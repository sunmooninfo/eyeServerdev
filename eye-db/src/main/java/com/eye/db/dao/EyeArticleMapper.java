package com.eye.db.dao;

import com.eye.db.domain.EyeArticle;
import com.eye.db.domain.EyeArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeArticleMapper {
    long countByExample(EyeArticleExample example);

    int deleteByExample(EyeArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeArticle record);

    int insertSelective(EyeArticle record);

    EyeArticle selectOneByExample(EyeArticleExample example);

    EyeArticle selectOneByExampleSelective(@Param("example") EyeArticleExample example, @Param("selective") EyeArticle.Column ... selective);

    EyeArticle selectOneByExampleWithBLOBs(EyeArticleExample example);

    List<EyeArticle> selectByExampleSelective(@Param("example") EyeArticleExample example, @Param("selective") EyeArticle.Column ... selective);

    List<EyeArticle> selectByExampleWithBLOBs(EyeArticleExample example);

    List<EyeArticle> selectByExample(EyeArticleExample example);

    EyeArticle selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeArticle.Column ... selective);

    EyeArticle selectByPrimaryKey(Integer id);

    EyeArticle selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeArticle record, @Param("example") EyeArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") EyeArticle record, @Param("example") EyeArticleExample example);

    int updateByExample(@Param("record") EyeArticle record, @Param("example") EyeArticleExample example);

    int updateByPrimaryKeySelective(EyeArticle record);

    int updateByPrimaryKeyWithBLOBs(EyeArticle record);

    int updateByPrimaryKey(EyeArticle record);

    int logicalDeleteByExample(@Param("example") EyeArticleExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}