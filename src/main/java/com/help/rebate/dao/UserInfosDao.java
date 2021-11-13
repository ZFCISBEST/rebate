package com.help.rebate.dao;

import com.help.rebate.dao.entity.UserInfos;
import com.help.rebate.dao.entity.UserInfosExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfosDao {
    long countByExample(UserInfosExample example);

    int deleteByExample(UserInfosExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfos record);

    int insertSelective(UserInfos record);

    List<UserInfos> selectByExample(UserInfosExample example);

    UserInfos selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserInfos record, @Param("example") UserInfosExample example);

    int updateByExample(@Param("record") UserInfos record, @Param("example") UserInfosExample example);

    int updateByPrimaryKeySelective(UserInfos record);

    int updateByPrimaryKey(UserInfos record);
}