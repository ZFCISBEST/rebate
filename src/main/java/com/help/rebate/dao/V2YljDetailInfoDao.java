package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2YljDetailInfo;
import com.help.rebate.dao.entity.V2YljDetailInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2YljDetailInfoDao {
    long countByExample(V2YljDetailInfoExample example);

    int deleteByExample(V2YljDetailInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(V2YljDetailInfo record);

    int insertSelective(V2YljDetailInfo record);

    List<V2YljDetailInfo> selectByExample(V2YljDetailInfoExample example);

    V2YljDetailInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") V2YljDetailInfo record, @Param("example") V2YljDetailInfoExample example);

    int updateByExample(@Param("record") V2YljDetailInfo record, @Param("example") V2YljDetailInfoExample example);

    int updateByPrimaryKeySelective(V2YljDetailInfo record);

    int updateByPrimaryKey(V2YljDetailInfo record);
}