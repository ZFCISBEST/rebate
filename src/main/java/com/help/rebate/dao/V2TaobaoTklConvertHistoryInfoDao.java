package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfo;
import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoTklConvertHistoryInfoDao {
    long countByExample(V2TaobaoTklConvertHistoryInfoExample example);

    int deleteByExample(V2TaobaoTklConvertHistoryInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoTklConvertHistoryInfo record);

    int insertSelective(V2TaobaoTklConvertHistoryInfo record);

    List<V2TaobaoTklConvertHistoryInfo> selectByExample(V2TaobaoTklConvertHistoryInfoExample example);

    V2TaobaoTklConvertHistoryInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoTklConvertHistoryInfo record, @Param("example") V2TaobaoTklConvertHistoryInfoExample example);

    int updateByExample(@Param("record") V2TaobaoTklConvertHistoryInfo record, @Param("example") V2TaobaoTklConvertHistoryInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoTklConvertHistoryInfo record);

    int updateByPrimaryKey(V2TaobaoTklConvertHistoryInfo record);
}