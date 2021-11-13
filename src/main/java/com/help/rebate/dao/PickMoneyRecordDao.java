package com.help.rebate.dao;

import com.help.rebate.dao.entity.PickMoneyRecord;
import com.help.rebate.dao.entity.PickMoneyRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PickMoneyRecordDao {
    long countByExample(PickMoneyRecordExample example);

    int deleteByExample(PickMoneyRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PickMoneyRecord record);

    int insertSelective(PickMoneyRecord record);

    List<PickMoneyRecord> selectByExample(PickMoneyRecordExample example);

    PickMoneyRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PickMoneyRecord record, @Param("example") PickMoneyRecordExample example);

    int updateByExample(@Param("record") PickMoneyRecord record, @Param("example") PickMoneyRecordExample example);

    int updateByPrimaryKeySelective(PickMoneyRecord record);

    int updateByPrimaryKey(PickMoneyRecord record);
}