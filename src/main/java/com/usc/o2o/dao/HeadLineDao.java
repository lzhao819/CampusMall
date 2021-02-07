package com.usc.o2o.dao;

import com.usc.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 根据传入的查询条件
 */
public interface HeadLineDao {
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
