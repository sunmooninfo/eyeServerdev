package com.eye.db.dao;

import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGoodsMapper {
    long countByExample(EyeGoodsExample example);

    int deleteByExample(EyeGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGoods record);

    int insertSelective(EyeGoods record);

    EyeGoods selectOneByExample(EyeGoodsExample example);

    EyeGoods selectOneByExampleSelective(@Param("example") EyeGoodsExample example, @Param("selective") EyeGoods.Column ... selective);

    EyeGoods selectOneByExampleWithBLOBs(EyeGoodsExample example);

    List<EyeGoods> selectByExampleSelective(@Param("example") EyeGoodsExample example, @Param("selective") EyeGoods.Column ... selective);

    List<EyeGoods> selectByExampleWithBLOBs(EyeGoodsExample example);

    List<EyeGoods> selectByExample(EyeGoodsExample example);

    EyeGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGoods.Column ... selective);

    EyeGoods selectByPrimaryKey(Integer id);

    EyeGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGoods record, @Param("example") EyeGoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") EyeGoods record, @Param("example") EyeGoodsExample example);

    int updateByExample(@Param("record") EyeGoods record, @Param("example") EyeGoodsExample example);

    int updateByPrimaryKeySelective(EyeGoods record);

    int updateByPrimaryKeyWithBLOBs(EyeGoods record);

    int updateByPrimaryKey(EyeGoods record);

    int logicalDeleteByExample(@Param("example") EyeGoodsExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}