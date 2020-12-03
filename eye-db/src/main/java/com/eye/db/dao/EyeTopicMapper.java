package com.eye.db.dao;

import com.eye.db.domain.EyeTopic;
import com.eye.db.domain.EyeTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeTopicMapper {
    long countByExample(EyeTopicExample example);

    int deleteByExample(EyeTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeTopic record);

    int insertSelective(EyeTopic record);

    EyeTopic selectOneByExample(EyeTopicExample example);

    EyeTopic selectOneByExampleSelective(@Param("example") EyeTopicExample example, @Param("selective") EyeTopic.Column ... selective);

    EyeTopic selectOneByExampleWithBLOBs(EyeTopicExample example);

    List<EyeTopic> selectByExampleSelective(@Param("example") EyeTopicExample example, @Param("selective") EyeTopic.Column ... selective);

    List<EyeTopic> selectByExampleWithBLOBs(EyeTopicExample example);

    List<EyeTopic> selectByExample(EyeTopicExample example);

    EyeTopic selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeTopic.Column ... selective);

    EyeTopic selectByPrimaryKey(Integer id);

    EyeTopic selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeTopic record, @Param("example") EyeTopicExample example);

    int updateByExampleWithBLOBs(@Param("record") EyeTopic record, @Param("example") EyeTopicExample example);

    int updateByExample(@Param("record") EyeTopic record, @Param("example") EyeTopicExample example);

    int updateByPrimaryKeySelective(EyeTopic record);

    int updateByPrimaryKeyWithBLOBs(EyeTopic record);

    int updateByPrimaryKey(EyeTopic record);

    int logicalDeleteByExample(@Param("example") EyeTopicExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}