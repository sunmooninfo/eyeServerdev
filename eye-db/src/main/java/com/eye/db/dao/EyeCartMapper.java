package com.eye.db.dao;

import com.eye.db.domain.EyeCart;
import com.eye.db.domain.EyeCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCartMapper {
    long countByExample(EyeCartExample example);

    int deleteByExample(EyeCartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeCart record);

    int insertSelective(EyeCart record);

    EyeCart selectOneByExample(EyeCartExample example);

    EyeCart selectOneByExampleSelective(@Param("example") EyeCartExample example, @Param("selective") EyeCart.Column ... selective);

    List<EyeCart> selectByExampleSelective(@Param("example") EyeCartExample example, @Param("selective") EyeCart.Column ... selective);

    List<EyeCart> selectByExample(EyeCartExample example);

    EyeCart selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeCart.Column ... selective);

    EyeCart selectByPrimaryKey(Integer id);

    EyeCart selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeCart record, @Param("example") EyeCartExample example);

    int updateByExample(@Param("record") EyeCart record, @Param("example") EyeCartExample example);

    int updateByPrimaryKeySelective(EyeCart record);

    int updateByPrimaryKey(EyeCart record);

    int logicalDeleteByExample(@Param("example") EyeCartExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}