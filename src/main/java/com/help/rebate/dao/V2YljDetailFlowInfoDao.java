package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2YljDetailFlowInfo;
import com.help.rebate.dao.entity.V2YljDetailFlowInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2YljDetailFlowInfoDao {
    long countByExample(V2YljDetailFlowInfoExample example);

    int deleteByExample(V2YljDetailFlowInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(V2YljDetailFlowInfo record);

    int insertSelective(V2YljDetailFlowInfo record);

    List<V2YljDetailFlowInfo> selectByExample(V2YljDetailFlowInfoExample example);

    V2YljDetailFlowInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") V2YljDetailFlowInfo record, @Param("example") V2YljDetailFlowInfoExample example);

    int updateByExample(@Param("record") V2YljDetailFlowInfo record, @Param("example") V2YljDetailFlowInfoExample example);

    int updateByPrimaryKeySelective(V2YljDetailFlowInfo record);

    int updateByPrimaryKey(V2YljDetailFlowInfo record);
}