package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoCommissionRatioInfo;
import com.help.rebate.dao.entity.V2TaobaoCommissionRatioInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoCommissionRatioInfoDao {
    long countByExample(V2TaobaoCommissionRatioInfoExample example);

    int deleteByExample(V2TaobaoCommissionRatioInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoCommissionRatioInfo record);

    int insertSelective(V2TaobaoCommissionRatioInfo record);

    List<V2TaobaoCommissionRatioInfo> selectByExample(V2TaobaoCommissionRatioInfoExample example);

    V2TaobaoCommissionRatioInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoCommissionRatioInfo record, @Param("example") V2TaobaoCommissionRatioInfoExample example);

    int updateByExample(@Param("record") V2TaobaoCommissionRatioInfo record, @Param("example") V2TaobaoCommissionRatioInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoCommissionRatioInfo record);

    int updateByPrimaryKey(V2TaobaoCommissionRatioInfo record);
}