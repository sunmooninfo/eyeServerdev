package com.eye.db.dao;

import com.eye.db.domain.EyeOrderGoods;
import com.eye.db.domain.EyeOrderGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeOrderGoodsMapper {
    long countByExample(EyeOrderGoodsExample example);

    int deleteByExample(EyeOrderGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeOrderGoods record);

    int insertSelective(EyeOrderGoods record);

    EyeOrderGoods selectOneByExample(EyeOrderGoodsExample example);

    EyeOrderGoods selectOneByExampleSelective(@Param("example") EyeOrderGoodsExample example, @Param("selective") EyeOrderGoods.Column ... selective);

    List<EyeOrderGoods> selectByExampleSelective(@Param("example") EyeOrderGoodsExample example, @Param("selective") EyeOrderGoods.Column ... selective);

    List<EyeOrderGoods> selectByExample(EyeOrderGoodsExample example);

    EyeOrderGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeOrderGoods.Column ... selective);

    EyeOrderGoods selectByPrimaryKey(Integer id);

    EyeOrderGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeOrderGoods record, @Param("example") EyeOrderGoodsExample example);

    int updateByExample(@Param("record") EyeOrderGoods record, @Param("example") EyeOrderGoodsExample example);

    int updateByPrimaryKeySelective(EyeOrderGoods record);

    int updateByPrimaryKey(EyeOrderGoods record);

    int logicalDeleteByExample(@Param("example") EyeOrderGoodsExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}