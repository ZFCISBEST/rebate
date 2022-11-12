package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoSyncOrderOffsetInfo;
import com.help.rebate.dao.entity.V2TaobaoSyncOrderOffsetInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoSyncOrderOffsetInfoDao {
    long countByExample(V2TaobaoSyncOrderOffsetInfoExample example);

    int deleteByExample(V2TaobaoSyncOrderOffsetInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoSyncOrderOffsetInfo record);

    int insertSelective(V2TaobaoSyncOrderOffsetInfo record);

    List<V2TaobaoSyncOrderOffsetInfo> selectByExample(V2TaobaoSyncOrderOffsetInfoExample example);

    V2TaobaoSyncOrderOffsetInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoSyncOrderOffsetInfo record, @Param("example") V2TaobaoSyncOrderOffsetInfoExample example);

    int updateByExample(@Param("record") V2TaobaoSyncOrderOffsetInfo record, @Param("example") V2TaobaoSyncOrderOffsetInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoSyncOrderOffsetInfo record);

    int updateByPrimaryKey(V2TaobaoSyncOrderOffsetInfo record);
}