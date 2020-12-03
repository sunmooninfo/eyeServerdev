package com.eye.db.dao;

import com.eye.db.domain.EyeSignStore;
import com.eye.db.domain.EyeSignStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeSignStoreMapper {
    long countByExample(EyeSignStoreExample example);

    int deleteByExample(EyeSignStoreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeSignStore record);

    int insertSelective(EyeSignStore record);

    EyeSignStore selectOneByExample(EyeSignStoreExample example);

    EyeSignStore selectOneByExampleSelective(@Param("example") EyeSignStoreExample example, @Param("selective") EyeSignStore.Column ... selective);

    List<EyeSignStore> selectByExampleSelective(@Param("example") EyeSignStoreExample example, @Param("selective") EyeSignStore.Column ... selective);

    List<EyeSignStore> selectByExample(EyeSignStoreExample example);

    EyeSignStore selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeSignStore.Column ... selective);

    EyeSignStore selectByPrimaryKey(Integer id);

    EyeSignStore selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeSignStore record, @Param("example") EyeSignStoreExample example);

    int updateByExample(@Param("record") EyeSignStore record, @Param("example") EyeSignStoreExample example);

    int updateByPrimaryKeySelective(EyeSignStore record);

    int updateByPrimaryKey(EyeSignStore record);

    int logicalDeleteByExample(@Param("example") EyeSignStoreExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}