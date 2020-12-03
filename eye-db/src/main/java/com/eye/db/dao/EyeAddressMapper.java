package com.eye.db.dao;

import com.eye.db.domain.EyeAddress;
import com.eye.db.domain.EyeAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeAddressMapper {
    long countByExample(EyeAddressExample example);

    int deleteByExample(EyeAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeAddress record);

    int insertSelective(EyeAddress record);

    EyeAddress selectOneByExample(EyeAddressExample example);

    EyeAddress selectOneByExampleSelective(@Param("example") EyeAddressExample example, @Param("selective") EyeAddress.Column ... selective);

    List<EyeAddress> selectByExampleSelective(@Param("example") EyeAddressExample example, @Param("selective") EyeAddress.Column ... selective);

    List<EyeAddress> selectByExample(EyeAddressExample example);

    EyeAddress selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeAddress.Column ... selective);

    EyeAddress selectByPrimaryKey(Integer id);

    EyeAddress selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeAddress record, @Param("example") EyeAddressExample example);

    int updateByExample(@Param("record") EyeAddress record, @Param("example") EyeAddressExample example);

    int updateByPrimaryKeySelective(EyeAddress record);

    int updateByPrimaryKey(EyeAddress record);

    int logicalDeleteByExample(@Param("example") EyeAddressExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}