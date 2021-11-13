package com.help.rebate.dao;

import com.help.rebate.dao.entity.TimeCursorPosition;
import com.help.rebate.dao.entity.TimeCursorPositionExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TimeCursorPositionDao {
    long countByExample(TimeCursorPositionExample example);

    int deleteByExample(TimeCursorPositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TimeCursorPosition record);

    int insertSelective(TimeCursorPosition record);

    List<TimeCursorPosition> selectByExample(TimeCursorPositionExample example);

    TimeCursorPosition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TimeCursorPosition record, @Param("example") TimeCursorPositionExample example);

    int updateByExample(@Param("record") TimeCursorPosition record, @Param("example") TimeCursorPositionExample example);

    int updateByPrimaryKeySelective(TimeCursorPosition record);

    int updateByPrimaryKey(TimeCursorPosition record);
}