package com.eye.db.dao;

import com.eye.db.domain.EyeCoupon;
import com.eye.db.domain.EyeCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCouponMapper {
    long countByExample(EyeCouponExample example);

    int deleteByExample(EyeCouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeCoupon record);

    int insertSelective(EyeCoupon record);

    EyeCoupon selectOneByExample(EyeCouponExample example);

    EyeCoupon selectOneByExampleSelective(@Param("example") EyeCouponExample example, @Param("selective") EyeCoupon.Column ... selective);

    List<EyeCoupon> selectByExampleSelective(@Param("example") EyeCouponExample example, @Param("selective") EyeCoupon.Column ... selective);

    List<EyeCoupon> selectByExample(EyeCouponExample example);

    EyeCoupon selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeCoupon.Column ... selective);

    EyeCoupon selectByPrimaryKey(Integer id);

    EyeCoupon selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeCoupon record, @Param("example") EyeCouponExample example);

    int updateByExample(@Param("record") EyeCoupon record, @Param("example") EyeCouponExample example);

    int updateByPrimaryKeySelective(EyeCoupon record);

    int updateByPrimaryKey(EyeCoupon record);

    int logicalDeleteByExample(@Param("example") EyeCouponExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}