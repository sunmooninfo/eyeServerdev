package com.eye.db.dao;

import com.eye.db.domain.EyeFootprint;
import com.eye.db.domain.EyeFootprintExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeFootprintMapper {
    long countByExample(EyeFootprintExample example);

    int deleteByExample(EyeFootprintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeFootprint record);

    int insertSelective(EyeFootprint record);

    EyeFootprint selectOneByExample(EyeFootprintExample example);

    EyeFootprint selectOneByExampleSelective(@Param("example") EyeFootprintExample example, @Param("selective") EyeFootprint.Column ... selective);

    List<EyeFootprint> selectByExampleSelective(@Param("example") EyeFootprintExample example, @Param("selective") EyeFootprint.Column ... selective);

    List<EyeFootprint> selectByExample(EyeFootprintExample example);

    EyeFootprint selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeFootprint.Column ... selective);

    EyeFootprint selectByPrimaryKey(Integer id);

    EyeFootprint selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeFootprint record, @Param("example") EyeFootprintExample example);

    int updateByExample(@Param("record") EyeFootprint record, @Param("example") EyeFootprintExample example);

    int updateByPrimaryKeySelective(EyeFootprint record);

    int updateByPrimaryKey(EyeFootprint record);

    int logicalDeleteByExample(@Param("example") EyeFootprintExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}