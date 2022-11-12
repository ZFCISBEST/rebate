package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoOrderOpenidMapInfoDao {
    long countByExample(V2TaobaoOrderOpenidMapInfoExample example);

    int deleteByExample(V2TaobaoOrderOpenidMapInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoOrderOpenidMapInfo record);

    int insertSelective(V2TaobaoOrderOpenidMapInfo record);

    List<V2TaobaoOrderOpenidMapInfo> selectByExample(V2TaobaoOrderOpenidMapInfoExample example);

    V2TaobaoOrderOpenidMapInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoOrderOpenidMapInfo record, @Param("example") V2TaobaoOrderOpenidMapInfoExample example);

    int updateByExample(@Param("record") V2TaobaoOrderOpenidMapInfo record, @Param("example") V2TaobaoOrderOpenidMapInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoOrderOpenidMapInfo record);

    int updateByPrimaryKey(V2TaobaoOrderOpenidMapInfo record);
}