package com.help.rebate.dao;

import com.help.rebate.dao.entity.TklConvertHistory;
import com.help.rebate.dao.entity.TklConvertHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TklConvertHistoryDao {
    long countByExample(TklConvertHistoryExample example);

    int deleteByExample(TklConvertHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TklConvertHistory record);

    int insertSelective(TklConvertHistory record);

    List<TklConvertHistory> selectByExample(TklConvertHistoryExample example);

    TklConvertHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TklConvertHistory record, @Param("example") TklConvertHistoryExample example);

    int updateByExample(@Param("record") TklConvertHistory record, @Param("example") TklConvertHistoryExample example);

    int updateByPrimaryKeySelective(TklConvertHistory record);

    int updateByPrimaryKey(TklConvertHistory record);
}