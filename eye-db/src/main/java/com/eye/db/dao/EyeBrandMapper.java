package com.eye.db.dao;

import com.eye.db.domain.EyeBrand;
import com.eye.db.domain.EyeBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeBrandMapper {
    long countByExample(EyeBrandExample example);

    int deleteByExample(EyeBrandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeBrand record);

    int insertSelective(EyeBrand record);

    EyeBrand selectOneByExample(EyeBrandExample example);

    EyeBrand selectOneByExampleSelective(@Param("example") EyeBrandExample example, @Param("selective") EyeBrand.Column ... selective);

    List<EyeBrand> selectByExampleSelective(@Param("example") EyeBrandExample example, @Param("selective") EyeBrand.Column ... selective);

    List<EyeBrand> selectByExample(EyeBrandExample example);

    EyeBrand selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeBrand.Column ... selective);

    EyeBrand selectByPrimaryKey(Integer id);

    EyeBrand selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeBrand record, @Param("example") EyeBrandExample example);

    int updateByExample(@Param("record") EyeBrand record, @Param("example") EyeBrandExample example);

    int updateByPrimaryKeySelective(EyeBrand record);

    int updateByPrimaryKey(EyeBrand record);

    int logicalDeleteByExample(@Param("example") EyeBrandExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}