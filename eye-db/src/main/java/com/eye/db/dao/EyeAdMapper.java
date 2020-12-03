package com.eye.db.dao;

import com.eye.db.domain.EyeAd;
import com.eye.db.domain.EyeAdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeAdMapper {
    long countByExample(EyeAdExample example);

    int deleteByExample(EyeAdExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeAd record);

    int insertSelective(EyeAd record);

    EyeAd selectOneByExample(EyeAdExample example);

    EyeAd selectOneByExampleSelective(@Param("example") EyeAdExample example, @Param("selective") EyeAd.Column ... selective);

    List<EyeAd> selectByExampleSelective(@Param("example") EyeAdExample example, @Param("selective") EyeAd.Column ... selective);

    List<EyeAd> selectByExample(EyeAdExample example);

    EyeAd selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeAd.Column ... selective);

    EyeAd selectByPrimaryKey(Integer id);

    EyeAd selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeAd record, @Param("example") EyeAdExample example);

    int updateByExample(@Param("record") EyeAd record, @Param("example") EyeAdExample example);

    int updateByPrimaryKeySelective(EyeAd record);

    int updateByPrimaryKey(EyeAd record);

    int logicalDeleteByExample(@Param("example") EyeAdExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}