package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapFailureInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapFailureInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoOrderOpenidMapFailureInfoDao {
    long countByExample(V2TaobaoOrderOpenidMapFailureInfoExample example);

    int deleteByExample(V2TaobaoOrderOpenidMapFailureInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoOrderOpenidMapFailureInfo record);

    int insertSelective(V2TaobaoOrderOpenidMapFailureInfo record);

    List<V2TaobaoOrderOpenidMapFailureInfo> selectByExample(V2TaobaoOrderOpenidMapFailureInfoExample example);

    V2TaobaoOrderOpenidMapFailureInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoOrderOpenidMapFailureInfo record, @Param("example") V2TaobaoOrderOpenidMapFailureInfoExample example);

    int updateByExample(@Param("record") V2TaobaoOrderOpenidMapFailureInfo record, @Param("example") V2TaobaoOrderOpenidMapFailureInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoOrderOpenidMapFailureInfo record);

    int updateByPrimaryKey(V2TaobaoOrderOpenidMapFailureInfo record);
}