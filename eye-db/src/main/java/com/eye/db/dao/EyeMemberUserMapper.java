package com.eye.db.dao;

import com.eye.db.domain.EyeMemberUser;
import com.eye.db.domain.EyeMemberUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeMemberUserMapper {
    long countByExample(EyeMemberUserExample example);

    int deleteByExample(EyeMemberUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeMemberUser record);

    int insertSelective(EyeMemberUser record);

    EyeMemberUser selectOneByExample(EyeMemberUserExample example);

    EyeMemberUser selectOneByExampleSelective(@Param("example") EyeMemberUserExample example, @Param("selective") EyeMemberUser.Column ... selective);

    List<EyeMemberUser> selectByExampleSelective(@Param("example") EyeMemberUserExample example, @Param("selective") EyeMemberUser.Column ... selective);

    List<EyeMemberUser> selectByExample(EyeMemberUserExample example);

    EyeMemberUser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeMemberUser.Column ... selective);

    EyeMemberUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EyeMemberUser record, @Param("example") EyeMemberUserExample example);

    int updateByExample(@Param("record") EyeMemberUser record, @Param("example") EyeMemberUserExample example);

    int updateByPrimaryKeySelective(EyeMemberUser record);

    int updateByPrimaryKey(EyeMemberUser record);
}