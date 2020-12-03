package com.eye.db.dao;

import com.eye.db.domain.EyeGrouponCart;
import com.eye.db.domain.EyeGrouponCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGrouponCartMapper {
    long countByExample(EyeGrouponCartExample example);

    int deleteByExample(EyeGrouponCartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGrouponCart record);

    int insertSelective(EyeGrouponCart record);

    EyeGrouponCart selectOneByExample(EyeGrouponCartExample example);

    EyeGrouponCart selectOneByExampleSelective(@Param("example") EyeGrouponCartExample example, @Param("selective") EyeGrouponCart.Column ... selective);

    List<EyeGrouponCart> selectByExampleSelective(@Param("example") EyeGrouponCartExample example, @Param("selective") EyeGrouponCart.Column ... selective);

    List<EyeGrouponCart> selectByExample(EyeGrouponCartExample example);

    EyeGrouponCart selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGrouponCart.Column ... selective);

    EyeGrouponCart selectByPrimaryKey(Integer id);

    EyeGrouponCart selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGrouponCart record, @Param("example") EyeGrouponCartExample example);

    int updateByExample(@Param("record") EyeGrouponCart record, @Param("example") EyeGrouponCartExample example);

    int updateByPrimaryKeySelective(EyeGrouponCart record);

    int updateByPrimaryKey(EyeGrouponCart record);

    int logicalDeleteByExample(@Param("example") EyeGrouponCartExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}