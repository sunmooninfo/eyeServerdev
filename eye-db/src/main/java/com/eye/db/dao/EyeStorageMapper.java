package com.eye.db.dao;

import com.eye.db.domain.EyeStorage;
import com.eye.db.domain.EyeStorageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeStorageMapper {
    long countByExample(EyeStorageExample example);

    int deleteByExample(EyeStorageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeStorage record);

    int insertSelective(EyeStorage record);

    EyeStorage selectOneByExample(EyeStorageExample example);

    EyeStorage selectOneByExampleSelective(@Param("example") EyeStorageExample example, @Param("selective") EyeStorage.Column ... selective);

    List<EyeStorage> selectByExampleSelective(@Param("example") EyeStorageExample example, @Param("selective") EyeStorage.Column ... selective);

    List<EyeStorage> selectByExample(EyeStorageExample example);

    EyeStorage selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeStorage.Column ... selective);

    EyeStorage selectByPrimaryKey(Integer id);

    EyeStorage selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeStorage record, @Param("example") EyeStorageExample example);

    int updateByExample(@Param("record") EyeStorage record, @Param("example") EyeStorageExample example);

    int updateByPrimaryKeySelective(EyeStorage record);

    int updateByPrimaryKey(EyeStorage record);

    int logicalDeleteByExample(@Param("example") EyeStorageExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}