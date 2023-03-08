package com.help.rebate.dao;

import com.help.rebate.dao.entity.V2TaobaoOrderOfflineAccountDetail;
import com.help.rebate.dao.entity.V2TaobaoOrderOfflineAccountDetailExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface V2TaobaoOrderOfflineAccountDetailDao {
    long countByExample(V2TaobaoOrderOfflineAccountDetailExample example);

    int deleteByExample(V2TaobaoOrderOfflineAccountDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(V2TaobaoOrderOfflineAccountDetail record);

    int insertSelective(V2TaobaoOrderOfflineAccountDetail record);

    List<V2TaobaoOrderOfflineAccountDetail> selectByExample(V2TaobaoOrderOfflineAccountDetailExample example);

    V2TaobaoOrderOfflineAccountDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") V2TaobaoOrderOfflineAccountDetail record, @Param("example") V2TaobaoOrderOfflineAccountDetailExample example);

    int updateByExample(@Param("record") V2TaobaoOrderOfflineAccountDetail record, @Param("example") V2TaobaoOrderOfflineAccountDetailExample example);

    int updateByPrimaryKeySelective(V2TaobaoOrderOfflineAccountDetail record);

    int updateByPrimaryKey(V2TaobaoOrderOfflineAccountDetail record);
}