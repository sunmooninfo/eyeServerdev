package com.eye.db.dao;

import com.eye.db.domain.EyeMemberPackage;
import com.eye.db.domain.EyeMemberPackageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EyeMemberPackageMapper {
    long countByExample(EyeMemberPackageExample example);

    int deleteByExample(EyeMemberPackageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EyeMemberPackage record);

    int insertSelective(EyeMemberPackage record);

    EyeMemberPackage selectOneByExample(EyeMemberPackageExample example);

    EyeMemberPackage selectOneByExampleSelective(@Param("example") EyeMemberPackageExample example, @Param("selective") EyeMemberPackage.Column ... selective);

    List<EyeMemberPackage> selectByExampleSelective(@Param("example") EyeMemberPackageExample example, @Param("selective") EyeMemberPackage.Column ... selective);

    List<EyeMemberPackage> selectByExample(EyeMemberPackageExample example);

    EyeMemberPackage selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") EyeMemberPackage.Column ... selective);

    EyeMemberPackage selectByPrimaryKey(Integer id);

    EyeMemberPackage selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") EyeMemberPackage record, @Param("example") EyeMemberPackageExample example);

    int updateByExample(@Param("record") EyeMemberPackage record, @Param("example") EyeMemberPackageExample example);

    int updateByPrimaryKeySelective(EyeMemberPackage record);

    int updateByPrimaryKey(EyeMemberPackage record);

    int logicalDeleteByExample(@Param("example") EyeMemberPackageExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}