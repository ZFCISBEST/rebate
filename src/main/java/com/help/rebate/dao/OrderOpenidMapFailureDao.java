package com.help.rebate.dao;

import com.help.rebate.dao.entity.OrderOpenidMapFailure;
import com.help.rebate.dao.entity.OrderOpenidMapFailureExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderOpenidMapFailureDao {
    long countByExample(OrderOpenidMapFailureExample example);

    int deleteByExample(OrderOpenidMapFailureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderOpenidMapFailure record);

    int insertSelective(OrderOpenidMapFailure record);

    List<OrderOpenidMapFailure> selectByExample(OrderOpenidMapFailureExample example);

    OrderOpenidMapFailure selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderOpenidMapFailure record, @Param("example") OrderOpenidMapFailureExample example);

    int updateByExample(@Param("record") OrderOpenidMapFailure record, @Param("example") OrderOpenidMapFailureExample example);

    int updateByPrimaryKeySelective(OrderOpenidMapFailure record);

    int updateByPrimaryKey(OrderOpenidMapFailure record);
}