package com.help.rebate.dao;

import com.help.rebate.dao.entity.OrderOpenidMap;
import com.help.rebate.dao.entity.OrderOpenidMapExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderOpenidMapDao {
    long countByExample(OrderOpenidMapExample example);

    int deleteByExample(OrderOpenidMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderOpenidMap record);

    int insertSelective(OrderOpenidMap record);

    List<OrderOpenidMap> selectByExample(OrderOpenidMapExample example);

    OrderOpenidMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderOpenidMap record, @Param("example") OrderOpenidMapExample example);

    int updateByExample(@Param("record") OrderOpenidMap record, @Param("example") OrderOpenidMapExample example);

    int updateByPrimaryKeySelective(OrderOpenidMap record);

    int updateByPrimaryKey(OrderOpenidMap record);
}