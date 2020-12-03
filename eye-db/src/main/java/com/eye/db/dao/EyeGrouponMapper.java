package com.eye.db.dao;

import com.eye.db.domain.EyeGroupon;
import com.eye.db.domain.EyeGrouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGrouponMapper {
    long countByExample(EyeGrouponExample example);

    int deleteByExample(EyeGrouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGroupon record);

    int insertSelective(EyeGroupon record);

    EyeGroupon selectOneByExample(EyeGrouponExample example);

    EyeGroupon selectOneByExampleSelective(@Param("example") EyeGrouponExample example, @Param("selective") EyeGroupon.Column ... selective);

    List<EyeGroupon> selectByExampleSelective(@Param("example") EyeGrouponExample example, @Param("selective") EyeGroupon.Column ... selective);

    List<EyeGroupon> selectByExample(EyeGrouponExample example);

    EyeGroupon selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGroupon.Column ... selective);

    EyeGroupon selectByPrimaryKey(Integer id);

    EyeGroupon selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGroupon record, @Param("example") EyeGrouponExample example);

    int updateByExample(@Param("record") EyeGroupon record, @Param("example") EyeGrouponExample example);

    int updateByPrimaryKeySelective(EyeGroupon record);

    int updateByPrimaryKey(EyeGroupon record);

    int logicalDeleteByExample(@Param("example") EyeGrouponExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}