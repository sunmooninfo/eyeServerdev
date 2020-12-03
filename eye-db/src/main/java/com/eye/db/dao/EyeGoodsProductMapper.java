package com.eye.db.dao;

import com.eye.db.domain.EyeGoodsProduct;
import com.eye.db.domain.EyeGoodsProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGoodsProductMapper {
    long countByExample(EyeGoodsProductExample example);

    int deleteByExample(EyeGoodsProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGoodsProduct record);

    int insertSelective(EyeGoodsProduct record);

    EyeGoodsProduct selectOneByExample(EyeGoodsProductExample example);

    EyeGoodsProduct selectOneByExampleSelective(@Param("example") EyeGoodsProductExample example, @Param("selective") EyeGoodsProduct.Column ... selective);

    List<EyeGoodsProduct> selectByExampleSelective(@Param("example") EyeGoodsProductExample example, @Param("selective") EyeGoodsProduct.Column ... selective);

    List<EyeGoodsProduct> selectByExample(EyeGoodsProductExample example);

    EyeGoodsProduct selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGoodsProduct.Column ... selective);

    EyeGoodsProduct selectByPrimaryKey(Integer id);

    EyeGoodsProduct selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGoodsProduct record, @Param("example") EyeGoodsProductExample example);

    int updateByExample(@Param("record") EyeGoodsProduct record, @Param("example") EyeGoodsProductExample example);

    int updateByPrimaryKeySelective(EyeGoodsProduct record);

    int updateByPrimaryKey(EyeGoodsProduct record);

    int logicalDeleteByExample(@Param("example") EyeGoodsProductExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}