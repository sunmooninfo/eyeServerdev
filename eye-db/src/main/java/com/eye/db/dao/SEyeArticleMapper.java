package com.eye.db.dao;

import com.eye.db.domain.SEyeArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SEyeArticleMapper {


    List<SEyeArticle> search(@Param("keywords") String keywords, @Param("keywordss") String keywordss, @Param("sortOrder") String sortOrder);

}
