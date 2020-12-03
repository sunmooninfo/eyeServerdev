package com.eye.db.dao;

import com.eye.db.domain.EyeGoodsSpecification;
import com.eye.db.domain.EyeGoodsSpecificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGoodsSpecificationMapper {
    long countByExample(EyeGoodsSpecificationExample example);

    int deleteByExample(EyeGoodsSpecificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGoodsSpecification record);

    int insertSelective(EyeGoodsSpecification record);

    EyeGoodsSpecification selectOneByExample(EyeGoodsSpecificationExample example);

    EyeGoodsSpecification selectOneByExampleSelective(@Param("example") EyeGoodsSpecificationExample example, @Param("selective") EyeGoodsSpecification.Column ... selective);

    List<EyeGoodsSpecification> selectByExampleSelective(@Param("example") EyeGoodsSpecificationExample example, @Param("selective") EyeGoodsSpecification.Column ... selective);

    List<EyeGoodsSpecification> selectByExample(EyeGoodsSpecificationExample example);

    EyeGoodsSpecification selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGoodsSpecification.Column ... selective);

    EyeGoodsSpecification selectByPrimaryKey(Integer id);

    EyeGoodsSpecification selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGoodsSpecification record, @Param("example") EyeGoodsSpecificationExample example);

    int updateByExample(@Param("record") EyeGoodsSpecification record, @Param("example") EyeGoodsSpecificationExample example);

    int updateByPrimaryKeySelective(EyeGoodsSpecification record);

    int updateByPrimaryKey(EyeGoodsSpecification record);

    int logicalDeleteByExample(@Param("example") EyeGoodsSpecificationExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}