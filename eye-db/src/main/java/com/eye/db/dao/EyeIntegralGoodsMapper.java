package com.eye.db.dao;

import com.eye.db.domain.EyeIntegralGoods;
import com.eye.db.domain.EyeIntegralGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeIntegralGoodsMapper {
    long countByExample(EyeIntegralGoodsExample example);

    int deleteByExample(EyeIntegralGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeIntegralGoods record);

    int insertSelective(EyeIntegralGoods record);

    EyeIntegralGoods selectOneByExample(EyeIntegralGoodsExample example);

    EyeIntegralGoods selectOneByExampleSelective(@Param("example") EyeIntegralGoodsExample example, @Param("selective") EyeIntegralGoods.Column ... selective);

    List<EyeIntegralGoods> selectByExampleSelective(@Param("example") EyeIntegralGoodsExample example, @Param("selective") EyeIntegralGoods.Column ... selective);

    List<EyeIntegralGoods> selectByExample(EyeIntegralGoodsExample example);

    EyeIntegralGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeIntegralGoods.Column ... selective);

    EyeIntegralGoods selectByPrimaryKey(Integer id);

    EyeIntegralGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeIntegralGoods record, @Param("example") EyeIntegralGoodsExample example);

    int updateByExample(@Param("record") EyeIntegralGoods record, @Param("example") EyeIntegralGoodsExample example);

    int updateByPrimaryKeySelective(EyeIntegralGoods record);

    int updateByPrimaryKey(EyeIntegralGoods record);

    int logicalDeleteByExample(@Param("example") EyeIntegralGoodsExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}