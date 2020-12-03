package com.eye.db.dao;

import com.eye.db.domain.EyeCompany;
import com.eye.db.domain.EyeCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCompanyMapper {
    long countByExample(EyeCompanyExample example);

    int deleteByExample(EyeCompanyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeCompany record);

    int insertSelective(EyeCompany record);

    EyeCompany selectOneByExample(EyeCompanyExample example);

    EyeCompany selectOneByExampleSelective(@Param("example") EyeCompanyExample example, @Param("selective") EyeCompany.Column ... selective);

    List<EyeCompany> selectByExampleSelective(@Param("example") EyeCompanyExample example, @Param("selective") EyeCompany.Column ... selective);

    List<EyeCompany> selectByExample(EyeCompanyExample example);

    EyeCompany selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeCompany.Column ... selective);

    EyeCompany selectByPrimaryKey(Integer id);

    EyeCompany selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeCompany record, @Param("example") EyeCompanyExample example);

    int updateByExample(@Param("record") EyeCompany record, @Param("example") EyeCompanyExample example);

    int updateByPrimaryKeySelective(EyeCompany record);

    int updateByPrimaryKey(EyeCompany record);

    int logicalDeleteByExample(@Param("example") EyeCompanyExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}