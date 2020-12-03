package com.eye.db.dao;

import com.eye.db.domain.EyeToolAccount;
import com.eye.db.domain.EyeToolAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeToolAccountMapper {
    long countByExample(EyeToolAccountExample example);

    int deleteByExample(EyeToolAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeToolAccount record);

    int insertSelective(EyeToolAccount record);

    EyeToolAccount selectOneByExample(EyeToolAccountExample example);

    EyeToolAccount selectOneByExampleSelective(@Param("example") EyeToolAccountExample example, @Param("selective") EyeToolAccount.Column ... selective);

    List<EyeToolAccount> selectByExampleSelective(@Param("example") EyeToolAccountExample example, @Param("selective") EyeToolAccount.Column ... selective);

    List<EyeToolAccount> selectByExample(EyeToolAccountExample example);

    EyeToolAccount selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeToolAccount.Column ... selective);

    EyeToolAccount selectByPrimaryKey(Integer id);

    EyeToolAccount selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeToolAccount record, @Param("example") EyeToolAccountExample example);

    int updateByExample(@Param("record") EyeToolAccount record, @Param("example") EyeToolAccountExample example);

    int updateByPrimaryKeySelective(EyeToolAccount record);

    int updateByPrimaryKey(EyeToolAccount record);

    int logicalDeleteByExample(@Param("example") EyeToolAccountExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}