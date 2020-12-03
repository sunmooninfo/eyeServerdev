package com.eye.db.dao;

import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeAccessoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeAccessoryMapper {
    long countByExample(EyeAccessoryExample example);

    int deleteByExample(EyeAccessoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeAccessory record);

    int insertSelective(EyeAccessory record);

    EyeAccessory selectOneByExample(EyeAccessoryExample example);

    EyeAccessory selectOneByExampleSelective(@Param("example") EyeAccessoryExample example, @Param("selective") EyeAccessory.Column ... selective);

    List<EyeAccessory> selectByExampleSelective(@Param("example") EyeAccessoryExample example, @Param("selective") EyeAccessory.Column ... selective);

    List<EyeAccessory> selectByExample(EyeAccessoryExample example);

    EyeAccessory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeAccessory.Column ... selective);

    EyeAccessory selectByPrimaryKey(Integer id);

    EyeAccessory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeAccessory record, @Param("example") EyeAccessoryExample example);

    int updateByExample(@Param("record") EyeAccessory record, @Param("example") EyeAccessoryExample example);

    int updateByPrimaryKeySelective(EyeAccessory record);

    int updateByPrimaryKey(EyeAccessory record);

    int logicalDeleteByExample(@Param("example") EyeAccessoryExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}