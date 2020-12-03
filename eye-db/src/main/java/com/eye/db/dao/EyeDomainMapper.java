package com.eye.db.dao;

import com.eye.db.domain.EyeDomain;
import com.eye.db.domain.EyeDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeDomainMapper {
    long countByExample(EyeDomainExample example);

    int deleteByExample(EyeDomainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeDomain record);

    int insertSelective(EyeDomain record);

    EyeDomain selectOneByExample(EyeDomainExample example);

    EyeDomain selectOneByExampleSelective(@Param("example") EyeDomainExample example, @Param("selective") EyeDomain.Column ... selective);

    List<EyeDomain> selectByExampleSelective(@Param("example") EyeDomainExample example, @Param("selective") EyeDomain.Column ... selective);

    List<EyeDomain> selectByExample(EyeDomainExample example);

    EyeDomain selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeDomain.Column ... selective);

    EyeDomain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EyeDomain record, @Param("example") EyeDomainExample example);

    int updateByExample(@Param("record") EyeDomain record, @Param("example") EyeDomainExample example);

    int updateByPrimaryKeySelective(EyeDomain record);

    int updateByPrimaryKey(EyeDomain record);
}