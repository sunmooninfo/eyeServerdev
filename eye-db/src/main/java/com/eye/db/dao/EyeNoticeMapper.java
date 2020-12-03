package com.eye.db.dao;

import com.eye.db.domain.EyeNotice;
import com.eye.db.domain.EyeNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeNoticeMapper {
    long countByExample(EyeNoticeExample example);

    int deleteByExample(EyeNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeNotice record);

    int insertSelective(EyeNotice record);

    EyeNotice selectOneByExample(EyeNoticeExample example);

    EyeNotice selectOneByExampleSelective(@Param("example") EyeNoticeExample example, @Param("selective") EyeNotice.Column ... selective);

    List<EyeNotice> selectByExampleSelective(@Param("example") EyeNoticeExample example, @Param("selective") EyeNotice.Column ... selective);

    List<EyeNotice> selectByExample(EyeNoticeExample example);

    EyeNotice selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeNotice.Column ... selective);

    EyeNotice selectByPrimaryKey(Integer id);

    EyeNotice selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeNotice record, @Param("example") EyeNoticeExample example);

    int updateByExample(@Param("record") EyeNotice record, @Param("example") EyeNoticeExample example);

    int updateByPrimaryKeySelective(EyeNotice record);

    int updateByPrimaryKey(EyeNotice record);

    int logicalDeleteByExample(@Param("example") EyeNoticeExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}