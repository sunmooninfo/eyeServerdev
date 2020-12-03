package com.eye.db.dao;

import com.eye.db.domain.EyeIssue;
import com.eye.db.domain.EyeIssueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeIssueMapper {
    long countByExample(EyeIssueExample example);

    int deleteByExample(EyeIssueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeIssue record);

    int insertSelective(EyeIssue record);

    EyeIssue selectOneByExample(EyeIssueExample example);

    EyeIssue selectOneByExampleSelective(@Param("example") EyeIssueExample example, @Param("selective") EyeIssue.Column ... selective);

    List<EyeIssue> selectByExampleSelective(@Param("example") EyeIssueExample example, @Param("selective") EyeIssue.Column ... selective);

    List<EyeIssue> selectByExample(EyeIssueExample example);

    EyeIssue selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeIssue.Column ... selective);

    EyeIssue selectByPrimaryKey(Integer id);

    EyeIssue selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeIssue record, @Param("example") EyeIssueExample example);

    int updateByExample(@Param("record") EyeIssue record, @Param("example") EyeIssueExample example);

    int updateByPrimaryKeySelective(EyeIssue record);

    int updateByPrimaryKey(EyeIssue record);

    int logicalDeleteByExample(@Param("example") EyeIssueExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}