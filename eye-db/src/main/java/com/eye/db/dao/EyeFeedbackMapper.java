package com.eye.db.dao;

import com.eye.db.domain.EyeFeedback;
import com.eye.db.domain.EyeFeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeFeedbackMapper {
    long countByExample(EyeFeedbackExample example);

    int deleteByExample(EyeFeedbackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeFeedback record);

    int insertSelective(EyeFeedback record);

    EyeFeedback selectOneByExample(EyeFeedbackExample example);

    EyeFeedback selectOneByExampleSelective(@Param("example") EyeFeedbackExample example, @Param("selective") EyeFeedback.Column ... selective);

    List<EyeFeedback> selectByExampleSelective(@Param("example") EyeFeedbackExample example, @Param("selective") EyeFeedback.Column ... selective);

    List<EyeFeedback> selectByExample(EyeFeedbackExample example);

    EyeFeedback selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeFeedback.Column ... selective);

    EyeFeedback selectByPrimaryKey(Integer id);

    EyeFeedback selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeFeedback record, @Param("example") EyeFeedbackExample example);

    int updateByExample(@Param("record") EyeFeedback record, @Param("example") EyeFeedbackExample example);

    int updateByPrimaryKeySelective(EyeFeedback record);

    int updateByPrimaryKey(EyeFeedback record);

    int logicalDeleteByExample(@Param("example") EyeFeedbackExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}