package com.eye.db.dao;

import com.eye.db.domain.EyeKeyword;
import com.eye.db.domain.EyeKeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeKeywordMapper {
    long countByExample(EyeKeywordExample example);

    int deleteByExample(EyeKeywordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeKeyword record);

    int insertSelective(EyeKeyword record);

    EyeKeyword selectOneByExample(EyeKeywordExample example);

    EyeKeyword selectOneByExampleSelective(@Param("example") EyeKeywordExample example, @Param("selective") EyeKeyword.Column ... selective);

    List<EyeKeyword> selectByExampleSelective(@Param("example") EyeKeywordExample example, @Param("selective") EyeKeyword.Column ... selective);

    List<EyeKeyword> selectByExample(EyeKeywordExample example);

    EyeKeyword selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeKeyword.Column ... selective);

    EyeKeyword selectByPrimaryKey(Integer id);

    EyeKeyword selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeKeyword record, @Param("example") EyeKeywordExample example);

    int updateByExample(@Param("record") EyeKeyword record, @Param("example") EyeKeywordExample example);

    int updateByPrimaryKeySelective(EyeKeyword record);

    int updateByPrimaryKey(EyeKeyword record);

    int logicalDeleteByExample(@Param("example") EyeKeywordExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}