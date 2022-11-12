package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoCommissionAccountFlowInfo;
import com.help.rebate.dao.entity.V2TaobaoCommissionAccountFlowInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoCommissionAccountFlowInfoDao {
    long countByExample(V2TaobaoCommissionAccountFlowInfoExample example);

    int deleteByExample(V2TaobaoCommissionAccountFlowInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoCommissionAccountFlowInfo record);

    int insertSelective(V2TaobaoCommissionAccountFlowInfo record);

    List<V2TaobaoCommissionAccountFlowInfo> selectByExample(V2TaobaoCommissionAccountFlowInfoExample example);

    V2TaobaoCommissionAccountFlowInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoCommissionAccountFlowInfo record, @Param("example") V2TaobaoCommissionAccountFlowInfoExample example);

    int updateByExample(@Param("record") V2TaobaoCommissionAccountFlowInfo record, @Param("example") V2TaobaoCommissionAccountFlowInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoCommissionAccountFlowInfo record);

    int updateByPrimaryKey(V2TaobaoCommissionAccountFlowInfo record);
}