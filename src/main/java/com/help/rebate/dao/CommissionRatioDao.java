package com.help.rebate.dao;

import com.help.rebate.dao.entity.CommissionRatio;
import com.help.rebate.dao.entity.CommissionRatioExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommissionRatioDao {
    long countByExample(CommissionRatioExample example);

    int deleteByExample(CommissionRatioExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommissionRatio record);

    int insertSelective(CommissionRatio record);

    List<CommissionRatio> selectByExample(CommissionRatioExample example);

    CommissionRatio selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommissionRatio record, @Param("example") CommissionRatioExample example);

    int updateByExample(@Param("record") CommissionRatio record, @Param("example") CommissionRatioExample example);

    int updateByPrimaryKeySelective(CommissionRatio record);

    int updateByPrimaryKey(CommissionRatio record);
}