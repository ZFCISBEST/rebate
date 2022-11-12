package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoOrderDetailInfoDao {
    long countByExample(V2TaobaoOrderDetailInfoExample example);

    int deleteByExample(V2TaobaoOrderDetailInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoOrderDetailInfo record);

    int insertSelective(V2TaobaoOrderDetailInfo record);

    List<V2TaobaoOrderDetailInfo> selectByExample(V2TaobaoOrderDetailInfoExample example);

    V2TaobaoOrderDetailInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoOrderDetailInfo record, @Param("example") V2TaobaoOrderDetailInfoExample example);

    int updateByExample(@Param("record") V2TaobaoOrderDetailInfo record, @Param("example") V2TaobaoOrderDetailInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoOrderDetailInfo record);

    int updateByPrimaryKey(V2TaobaoOrderDetailInfo record);
}