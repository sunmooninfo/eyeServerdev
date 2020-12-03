package com.eye.db.dao;

import com.eye.db.domain.EyeOrder;
import com.eye.db.domain.EyeOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeOrderMapper {
    long countByExample(EyeOrderExample example);

    int deleteByExample(EyeOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeOrder record);

    int insertSelective(EyeOrder record);

    EyeOrder selectOneByExample(EyeOrderExample example);

    EyeOrder selectOneByExampleSelective(@Param("example") EyeOrderExample example, @Param("selective") EyeOrder.Column ... selective);

    List<EyeOrder> selectByExampleSelective(@Param("example") EyeOrderExample example, @Param("selective") EyeOrder.Column ... selective);

    List<EyeOrder> selectByExample(EyeOrderExample example);

    EyeOrder selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeOrder.Column ... selective);

    EyeOrder selectByPrimaryKey(Integer id);

    EyeOrder selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeOrder record, @Param("example") EyeOrderExample example);

    int updateByExample(@Param("record") EyeOrder record, @Param("example") EyeOrderExample example);

    int updateByPrimaryKeySelective(EyeOrder record);

    int updateByPrimaryKey(EyeOrder record);

    int logicalDeleteByExample(@Param("example") EyeOrderExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}