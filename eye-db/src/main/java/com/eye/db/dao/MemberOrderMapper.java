package com.eye.db.dao;

import com.eye.db.domain.EyeMemberOrder;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface MemberOrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime preUpdateTime, @Param("memberOrder") EyeMemberOrder memberOrder);
}
