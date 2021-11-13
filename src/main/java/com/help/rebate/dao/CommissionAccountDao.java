package com.help.rebate.dao;

import com.help.rebate.dao.entity.CommissionAccount;
import com.help.rebate.dao.entity.CommissionAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommissionAccountDao {
    long countByExample(CommissionAccountExample example);

    int deleteByExample(CommissionAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommissionAccount record);

    int insertSelective(CommissionAccount record);

    List<CommissionAccount> selectByExample(CommissionAccountExample example);

    CommissionAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommissionAccount record, @Param("example") CommissionAccountExample example);

    int updateByExample(@Param("record") CommissionAccount record, @Param("example") CommissionAccountExample example);

    int updateByPrimaryKeySelective(CommissionAccount record);

    int updateByPrimaryKey(CommissionAccount record);
}