package com.eye.db.dao;

import org.apache.ibatis.annotations.Param;
import com.eye.db.domain.EyeOrder;
import java.time.LocalDateTime;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") EyeOrder order);
}