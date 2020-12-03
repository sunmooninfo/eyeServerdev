package com.eye.db.dao;

import com.eye.db.domain.EyeSearchHistory;
import com.eye.db.domain.EyeSearchHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeSearchHistoryMapper {
    long countByExample(EyeSearchHistoryExample example);

    int deleteByExample(EyeSearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeSearchHistory record);

    int insertSelective(EyeSearchHistory record);

    EyeSearchHistory selectOneByExample(EyeSearchHistoryExample example);

    EyeSearchHistory selectOneByExampleSelective(@Param("example") EyeSearchHistoryExample example, @Param("selective") EyeSearchHistory.Column ... selective);

    List<EyeSearchHistory> selectByExampleSelective(@Param("example") EyeSearchHistoryExample example, @Param("selective") EyeSearchHistory.Column ... selective);

    List<EyeSearchHistory> selectByExample(EyeSearchHistoryExample example);

    EyeSearchHistory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeSearchHistory.Column ... selective);

    EyeSearchHistory selectByPrimaryKey(Integer id);

    EyeSearchHistory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeSearchHistory record, @Param("example") EyeSearchHistoryExample example);

    int updateByExample(@Param("record") EyeSearchHistory record, @Param("example") EyeSearchHistoryExample example);

    int updateByPrimaryKeySelective(EyeSearchHistory record);

    int updateByPrimaryKey(EyeSearchHistory record);

    int logicalDeleteByExample(@Param("example") EyeSearchHistoryExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}