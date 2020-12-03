package com.eye.db.dao;

import com.eye.db.domain.EyeCouponUser;
import com.eye.db.domain.EyeCouponUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeCouponUserMapper {
    long countByExample(EyeCouponUserExample example);

    int deleteByExample(EyeCouponUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeCouponUser record);

    int insertSelective(EyeCouponUser record);

    EyeCouponUser selectOneByExample(EyeCouponUserExample example);

    EyeCouponUser selectOneByExampleSelective(@Param("example") EyeCouponUserExample example, @Param("selective") EyeCouponUser.Column ... selective);

    List<EyeCouponUser> selectByExampleSelective(@Param("example") EyeCouponUserExample example, @Param("selective") EyeCouponUser.Column ... selective);

    List<EyeCouponUser> selectByExample(EyeCouponUserExample example);

    EyeCouponUser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeCouponUser.Column ... selective);

    EyeCouponUser selectByPrimaryKey(Integer id);

    EyeCouponUser selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeCouponUser record, @Param("example") EyeCouponUserExample example);

    int updateByExample(@Param("record") EyeCouponUser record, @Param("example") EyeCouponUserExample example);

    int updateByPrimaryKeySelective(EyeCouponUser record);

    int updateByPrimaryKey(EyeCouponUser record);

    int logicalDeleteByExample(@Param("example") EyeCouponUserExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}