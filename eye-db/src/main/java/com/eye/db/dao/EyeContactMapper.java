package com.eye.db.dao;

import com.eye.db.domain.EyeContact;
import com.eye.db.domain.EyeContactExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeContactMapper {
    long countByExample(EyeContactExample example);

    int deleteByExample(EyeContactExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeContact record);

    int insertSelective(EyeContact record);

    EyeContact selectOneByExample(EyeContactExample example);

    EyeContact selectOneByExampleSelective(@Param("example") EyeContactExample example, @Param("selective") EyeContact.Column ... selective);

    List<EyeContact> selectByExampleSelective(@Param("example") EyeContactExample example, @Param("selective") EyeContact.Column ... selective);

    List<EyeContact> selectByExample(EyeContactExample example);

    EyeContact selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeContact.Column ... selective);

    EyeContact selectByPrimaryKey(Integer id);

    EyeContact selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeContact record, @Param("example") EyeContactExample example);

    int updateByExample(@Param("record") EyeContact record, @Param("example") EyeContactExample example);

    int updateByPrimaryKeySelective(EyeContact record);

    int updateByPrimaryKey(EyeContact record);

    int logicalDeleteByExample(@Param("example") EyeContactExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}