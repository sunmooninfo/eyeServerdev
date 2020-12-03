package com.eye.db.dao;

import com.eye.db.domain.EyeBrandWeb;
import com.eye.db.domain.EyeBrandWebExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeBrandWebMapper {
    long countByExample(EyeBrandWebExample example);

    int deleteByExample(EyeBrandWebExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeBrandWeb record);

    int insertSelective(EyeBrandWeb record);

    EyeBrandWeb selectOneByExample(EyeBrandWebExample example);

    EyeBrandWeb selectOneByExampleSelective(@Param("example") EyeBrandWebExample example, @Param("selective") EyeBrandWeb.Column ... selective);

    EyeBrandWeb selectOneByExampleWithBLOBs(EyeBrandWebExample example);

    List<EyeBrandWeb> selectByExampleSelective(@Param("example") EyeBrandWebExample example, @Param("selective") EyeBrandWeb.Column ... selective);

    List<EyeBrandWeb> selectByExampleWithBLOBs(EyeBrandWebExample example);

    List<EyeBrandWeb> selectByExample(EyeBrandWebExample example);

    EyeBrandWeb selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeBrandWeb.Column ... selective);

    EyeBrandWeb selectByPrimaryKey(Integer id);

    EyeBrandWeb selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeBrandWeb record, @Param("example") EyeBrandWebExample example);

    int updateByExampleWithBLOBs(@Param("record") EyeBrandWeb record, @Param("example") EyeBrandWebExample example);

    int updateByExample(@Param("record") EyeBrandWeb record, @Param("example") EyeBrandWebExample example);

    int updateByPrimaryKeySelective(EyeBrandWeb record);

    int updateByPrimaryKeyWithBLOBs(EyeBrandWeb record);

    int updateByPrimaryKey(EyeBrandWeb record);

    int logicalDeleteByExample(@Param("example") EyeBrandWebExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}