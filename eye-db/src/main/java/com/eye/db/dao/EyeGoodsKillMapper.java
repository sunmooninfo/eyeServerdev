package com.eye.db.dao;

import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.domain.EyeGoodsKillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeGoodsKillMapper {
    long countByExample(EyeGoodsKillExample example);

    int deleteByExample(EyeGoodsKillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeGoodsKill record);

    int insertSelective(EyeGoodsKill record);

    EyeGoodsKill selectOneByExample(EyeGoodsKillExample example);

    EyeGoodsKill selectOneByExampleSelective(@Param("example") EyeGoodsKillExample example, @Param("selective") EyeGoodsKill.Column ... selective);

    List<EyeGoodsKill> selectByExampleSelective(@Param("example") EyeGoodsKillExample example, @Param("selective") EyeGoodsKill.Column ... selective);

    List<EyeGoodsKill> selectByExample(EyeGoodsKillExample example);

    EyeGoodsKill selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeGoodsKill.Column ... selective);

    EyeGoodsKill selectByPrimaryKey(Integer id);

    EyeGoodsKill selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeGoodsKill record, @Param("example") EyeGoodsKillExample example);

    int updateByExample(@Param("record") EyeGoodsKill record, @Param("example") EyeGoodsKillExample example);

    int updateByPrimaryKeySelective(EyeGoodsKill record);

    int updateByPrimaryKey(EyeGoodsKill record);

    int logicalDeleteByExample(@Param("example") EyeGoodsKillExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}