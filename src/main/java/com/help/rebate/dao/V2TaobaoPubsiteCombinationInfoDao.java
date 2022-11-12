package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfo;
import com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoPubsiteCombinationInfoDao {
    long countByExample(V2TaobaoPubsiteCombinationInfoExample example);

    int deleteByExample(V2TaobaoPubsiteCombinationInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoPubsiteCombinationInfo record);

    int insertSelective(V2TaobaoPubsiteCombinationInfo record);

    List<V2TaobaoPubsiteCombinationInfo> selectByExample(V2TaobaoPubsiteCombinationInfoExample example);

    V2TaobaoPubsiteCombinationInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoPubsiteCombinationInfo record, @Param("example") V2TaobaoPubsiteCombinationInfoExample example);

    int updateByExample(@Param("record") V2TaobaoPubsiteCombinationInfo record, @Param("example") V2TaobaoPubsiteCombinationInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoPubsiteCombinationInfo record);

    int updateByPrimaryKey(V2TaobaoPubsiteCombinationInfo record);
}