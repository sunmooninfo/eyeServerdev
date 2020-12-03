package com.eye.db.dao;

import com.eye.db.domain.EyeBrandWebMer;
import com.eye.db.domain.EyeBrandWebMerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeBrandWebMerMapper {
    long countByExample(EyeBrandWebMerExample example);

    int deleteByExample(EyeBrandWebMerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeBrandWebMer record);

    int insertSelective(EyeBrandWebMer record);

    EyeBrandWebMer selectOneByExample(EyeBrandWebMerExample example);

    EyeBrandWebMer selectOneByExampleSelective(@Param("example") EyeBrandWebMerExample example, @Param("selective") EyeBrandWebMer.Column ... selective);

    List<EyeBrandWebMer> selectByExampleSelective(@Param("example") EyeBrandWebMerExample example, @Param("selective") EyeBrandWebMer.Column ... selective);

    List<EyeBrandWebMer> selectByExample(EyeBrandWebMerExample example);

    EyeBrandWebMer selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeBrandWebMer.Column ... selective);

    EyeBrandWebMer selectByPrimaryKey(Integer id);

    EyeBrandWebMer selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeBrandWebMer record, @Param("example") EyeBrandWebMerExample example);

    int updateByExample(@Param("record") EyeBrandWebMer record, @Param("example") EyeBrandWebMerExample example);

    int updateByPrimaryKeySelective(EyeBrandWebMer record);

    int updateByPrimaryKey(EyeBrandWebMer record);

    int logicalDeleteByExample(@Param("example") EyeBrandWebMerExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}