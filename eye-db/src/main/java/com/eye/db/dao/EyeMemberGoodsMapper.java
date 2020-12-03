package com.eye.db.dao;

import com.eye.db.domain.EyeMemberGoods;
import com.eye.db.domain.EyeMemberGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeMemberGoodsMapper {
    long countByExample(EyeMemberGoodsExample example);

    int deleteByExample(EyeMemberGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeMemberGoods record);

    int insertSelective(EyeMemberGoods record);

    EyeMemberGoods selectOneByExample(EyeMemberGoodsExample example);

    EyeMemberGoods selectOneByExampleSelective(@Param("example") EyeMemberGoodsExample example, @Param("selective") EyeMemberGoods.Column ... selective);

    List<EyeMemberGoods> selectByExampleSelective(@Param("example") EyeMemberGoodsExample example, @Param("selective") EyeMemberGoods.Column ... selective);

    List<EyeMemberGoods> selectByExample(EyeMemberGoodsExample example);

    EyeMemberGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeMemberGoods.Column ... selective);

    EyeMemberGoods selectByPrimaryKey(Integer id);

    EyeMemberGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeMemberGoods record, @Param("example") EyeMemberGoodsExample example);

    int updateByExample(@Param("record") EyeMemberGoods record, @Param("example") EyeMemberGoodsExample example);

    int updateByPrimaryKeySelective(EyeMemberGoods record);

    int updateByPrimaryKey(EyeMemberGoods record);

    int logicalDeleteByExample(@Param("example") EyeMemberGoodsExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}