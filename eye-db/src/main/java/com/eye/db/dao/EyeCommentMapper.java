package com.eye.db.dao;

import com.eye.db.domain.EyeComment;
import com.eye.db.domain.EyeCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCommentMapper {
    long countByExample(EyeCommentExample example);

    int deleteByExample(EyeCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeComment record);

    int insertSelective(EyeComment record);

    EyeComment selectOneByExample(EyeCommentExample example);

    EyeComment selectOneByExampleSelective(@Param("example") EyeCommentExample example, @Param("selective") EyeComment.Column ... selective);

    List<EyeComment> selectByExampleSelective(@Param("example") EyeCommentExample example, @Param("selective") EyeComment.Column ... selective);

    List<EyeComment> selectByExample(EyeCommentExample example);

    EyeComment selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeComment.Column ... selective);

    EyeComment selectByPrimaryKey(Integer id);

    EyeComment selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeComment record, @Param("example") EyeCommentExample example);

    int updateByExample(@Param("record") EyeComment record, @Param("example") EyeCommentExample example);

    int updateByPrimaryKeySelective(EyeComment record);

    int updateByPrimaryKey(EyeComment record);

    int logicalDeleteByExample(@Param("example") EyeCommentExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}