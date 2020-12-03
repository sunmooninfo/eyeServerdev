package com.eye.db.dao;

import com.eye.db.domain.EyeCollect;
import com.eye.db.domain.EyeCollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCollectMapper {
    long countByExample(EyeCollectExample example);

    int deleteByExample(EyeCollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeCollect record);

    int insertSelective(EyeCollect record);

    EyeCollect selectOneByExample(EyeCollectExample example);

    EyeCollect selectOneByExampleSelective(@Param("example") EyeCollectExample example, @Param("selective") EyeCollect.Column ... selective);

    List<EyeCollect> selectByExampleSelective(@Param("example") EyeCollectExample example, @Param("selective") EyeCollect.Column ... selective);

    List<EyeCollect> selectByExample(EyeCollectExample example);

    EyeCollect selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeCollect.Column ... selective);

    EyeCollect selectByPrimaryKey(Integer id);

    EyeCollect selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeCollect record, @Param("example") EyeCollectExample example);

    int updateByExample(@Param("record") EyeCollect record, @Param("example") EyeCollectExample example);

    int updateByPrimaryKeySelective(EyeCollect record);

    int updateByPrimaryKey(EyeCollect record);

    int logicalDeleteByExample(@Param("example") EyeCollectExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}