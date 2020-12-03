package com.eye.db.dao;

import com.eye.db.domain.EyeMemberOrder;
import com.eye.db.domain.EyeMemberOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeMemberOrderMapper {
    long countByExample(EyeMemberOrderExample example);

    int deleteByExample(EyeMemberOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeMemberOrder record);

    int insertSelective(EyeMemberOrder record);

    EyeMemberOrder selectOneByExample(EyeMemberOrderExample example);

    EyeMemberOrder selectOneByExampleSelective(@Param("example") EyeMemberOrderExample example, @Param("selective") EyeMemberOrder.Column ... selective);

    List<EyeMemberOrder> selectByExampleSelective(@Param("example") EyeMemberOrderExample example, @Param("selective") EyeMemberOrder.Column ... selective);

    List<EyeMemberOrder> selectByExample(EyeMemberOrderExample example);

    EyeMemberOrder selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeMemberOrder.Column ... selective);

    EyeMemberOrder selectByPrimaryKey(Integer id);

    EyeMemberOrder selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeMemberOrder record, @Param("example") EyeMemberOrderExample example);

    int updateByExample(@Param("record") EyeMemberOrder record, @Param("example") EyeMemberOrderExample example);

    int updateByPrimaryKeySelective(EyeMemberOrder record);

    int updateByPrimaryKey(EyeMemberOrder record);

    int logicalDeleteByExample(@Param("example") EyeMemberOrderExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}