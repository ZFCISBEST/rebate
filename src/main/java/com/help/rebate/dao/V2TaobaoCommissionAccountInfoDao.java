package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoCommissionAccountInfo;
import com.help.rebate.dao.entity.V2TaobaoCommissionAccountInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoCommissionAccountInfoDao {
    long countByExample(V2TaobaoCommissionAccountInfoExample example);

    int deleteByExample(V2TaobaoCommissionAccountInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoCommissionAccountInfo record);

    int insertSelective(V2TaobaoCommissionAccountInfo record);

    List<V2TaobaoCommissionAccountInfo> selectByExample(V2TaobaoCommissionAccountInfoExample example);

    V2TaobaoCommissionAccountInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoCommissionAccountInfo record, @Param("example") V2TaobaoCommissionAccountInfoExample example);

    int updateByExample(@Param("record") V2TaobaoCommissionAccountInfo record, @Param("example") V2TaobaoCommissionAccountInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoCommissionAccountInfo record);

    int updateByPrimaryKey(V2TaobaoCommissionAccountInfo record);
}