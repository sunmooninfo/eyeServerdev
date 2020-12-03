package com.eye.db.dao;

import org.apache.ibatis.annotations.Param;

public interface GoodsKillMapper {
  int addStock(@Param("id") Integer id, @Param("num") Short num);
  int reduceStock(@Param("id") Integer id, @Param("num") Short num);
}
