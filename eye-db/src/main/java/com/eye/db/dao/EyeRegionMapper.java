package com.eye.db.dao;

import com.eye.db.domain.EyeRegion;
import com.eye.db.domain.EyeRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeRegionMapper {
    long countByExample(EyeRegionExample example);

    int deleteByExample(EyeRegionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeRegion record);

    int insertSelective(EyeRegion record);

    EyeRegion selectOneByExample(EyeRegionExample example);

    EyeRegion selectOneByExampleSelective(@Param("example") EyeRegionExample example, @Param("selective") EyeRegion.Column ... selective);

    List<EyeRegion> selectByExampleSelective(@Param("example") EyeRegionExample example, @Param("selective") EyeRegion.Column ... selective);

    List<EyeRegion> selectByExample(EyeRegionExample example);

    EyeRegion selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeRegion.Column ... selective);

    EyeRegion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EyeRegion record, @Param("example") EyeRegionExample example);

    int updateByExample(@Param("record") EyeRegion record, @Param("example") EyeRegionExample example);

    int updateByPrimaryKeySelective(EyeRegion record);

    int updateByPrimaryKey(EyeRegion record);
}