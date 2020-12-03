package com.eye.db.dao;

import com.eye.db.domain.EyeGoodsAttribute;
import com.eye.db.domain.EyeGoodsAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGoodsAttributeMapper {
    long countByExample(EyeGoodsAttributeExample example);

    int deleteByExample(EyeGoodsAttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGoodsAttribute record);

    int insertSelective(EyeGoodsAttribute record);

    EyeGoodsAttribute selectOneByExample(EyeGoodsAttributeExample example);

    EyeGoodsAttribute selectOneByExampleSelective(@Param("example") EyeGoodsAttributeExample example, @Param("selective") EyeGoodsAttribute.Column ... selective);

    List<EyeGoodsAttribute> selectByExampleSelective(@Param("example") EyeGoodsAttributeExample example, @Param("selective") EyeGoodsAttribute.Column ... selective);

    List<EyeGoodsAttribute> selectByExample(EyeGoodsAttributeExample example);

    EyeGoodsAttribute selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGoodsAttribute.Column ... selective);

    EyeGoodsAttribute selectByPrimaryKey(Integer id);

    EyeGoodsAttribute selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGoodsAttribute record, @Param("example") EyeGoodsAttributeExample example);

    int updateByExample(@Param("record") EyeGoodsAttribute record, @Param("example") EyeGoodsAttributeExample example);

    int updateByPrimaryKeySelective(EyeGoodsAttribute record);

    int updateByPrimaryKey(EyeGoodsAttribute record);

    int logicalDeleteByExample(@Param("example") EyeGoodsAttributeExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}