package com.eye.db.dao;

import com.eye.db.domain.EyeCommission;
import com.eye.db.domain.EyeCommissionExample;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCommissionMapper {
    long countByExample(EyeCommissionExample example);

    int deleteByExample(EyeCommissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeCommission record);

    int insertSelective(EyeCommission record);

    EyeCommission selectOneByExample(EyeCommissionExample example);

    EyeCommission selectOneByExampleSelective(@Param("example") EyeCommissionExample example, @Param("selective") EyeCommission.Column ... selective);

    List<EyeCommission> selectByExampleSelective(@Param("example") EyeCommissionExample example, @Param("selective") EyeCommission.Column ... selective);

    List<EyeCommission> selectByExample(EyeCommissionExample example);

    EyeCommission selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeCommission.Column ... selective);

    EyeCommission selectByPrimaryKey(Integer id);

    EyeCommission selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeCommission record, @Param("example") EyeCommissionExample example);

    int updateByExample(@Param("record") EyeCommission record, @Param("example") EyeCommissionExample example);

    int updateByPrimaryKeySelective(EyeCommission record);

    int updateByPrimaryKey(EyeCommission record);

    int logicalDeleteByExample(@Param("example") EyeCommissionExample example);

    int logicalDeleteByPrimaryKey(Integer id);
    
    List<EyeCommission> selectCommisionInfo(@Param("managerMobile") String managerMobile,@Param("orderSn") String orderSn,@Param("storeName") String storeName,@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,@Param("deleted") Boolean deleted);
}