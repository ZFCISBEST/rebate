package com.help.rebate.dao;

import com.help.rebate.dao.entity.PubsiteCombination;
import com.help.rebate.dao.entity.PubsiteCombinationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PubsiteCombinationDao {
    long countByExample(PubsiteCombinationExample example);

    int deleteByExample(PubsiteCombinationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PubsiteCombination record);

    int insertSelective(PubsiteCombination record);

    List<PubsiteCombination> selectByExample(PubsiteCombinationExample example);

    PubsiteCombination selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PubsiteCombination record, @Param("example") PubsiteCombinationExample example);

    int updateByExample(@Param("record") PubsiteCombination record, @Param("example") PubsiteCombinationExample example);

    int updateByPrimaryKeySelective(PubsiteCombination record);

    int updateByPrimaryKey(PubsiteCombination record);
}