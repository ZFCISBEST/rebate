package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.dao.entity.V2TaobaoUserInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoUserInfoDao {
    long countByExample(V2TaobaoUserInfoExample example);

    int deleteByExample(V2TaobaoUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(V2TaobaoUserInfo record);

    int insertSelective(V2TaobaoUserInfo record);

    List<V2TaobaoUserInfo> selectByExample(V2TaobaoUserInfoExample example);

    V2TaobaoUserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") V2TaobaoUserInfo record, @Param("example") V2TaobaoUserInfoExample example);

    int updateByExample(@Param("record") V2TaobaoUserInfo record, @Param("example") V2TaobaoUserInfoExample example);

    int updateByPrimaryKeySelective(V2TaobaoUserInfo record);

    int updateByPrimaryKey(V2TaobaoUserInfo record);
}