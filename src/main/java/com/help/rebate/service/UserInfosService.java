package com.help.rebate.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.dao.UserInfosDao;
import com.help.rebate.dao.entity.TklConvertHistory;
import com.help.rebate.dao.entity.TklConvertHistoryExample;
import com.help.rebate.dao.entity.UserInfos;
import com.help.rebate.dao.entity.UserInfosExample;
import com.help.rebate.service.ddx.tb.DdxInviteCodeManager;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.PropertyValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户信息服务类
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class UserInfosService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfosService.class);

    /**
     * 用户操作
     */
    @Resource
    private UserInfosDao userInfosDao;

    /**
     * 会员操作
     */
    @Autowired
    private DdxInviteCodeManager ddxInviteCodeManager;

    /**
     * 插入一个新用户
     * @param openId
     * @param relationId
     * @param specialId
     * @param externalId
     * @param dataFrom
     * @return
     */
    public UserInfos insertOrDoNone(String openId, String relationId, String specialId, String externalId, String dataFrom) {
        //首先查看是否存在
        UserInfosExample example = new UserInfosExample();
        example.setLimit(1);
        UserInfosExample.Criteria criteria = example.createCriteria();
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            criteria.andExternalIdEqualTo(externalId);
        }
        criteria.andDataFromEqualTo(dataFrom);
        List<UserInfos> userInfos = userInfosDao.selectByExample(example);

        //如果为空，则插入
        if (userInfos == null || userInfos.isEmpty()) {
            UserInfos userInfo = new UserInfos();
            userInfo.setGmtCreated(new Date());
            userInfo.setGmtModified(new Date());
            userInfo.setOpenId(openId);
            userInfo.setOpenName(null);
            userInfo.setExternalId(EmptyUtils.isEmpty(externalId) ? openId : externalId);
            userInfo.setSpecialId(specialId);
            userInfo.setRelationId(relationId);
            userInfo.setDataFrom(dataFrom);
            userInfo.setStatus(0);

            int affectedCnt = userInfosDao.insertSelective(userInfo);
            //应该能插入成功
            Checks.isTrue(affectedCnt == 1, "不存在的用户，创建新用户失败");
            return userInfo;
        }

        //执行更新
        UserInfos infos = userInfos.get(0);
        infos.setGmtModified(new Date());
        infos.setRelationId(relationId);
        infos.setSpecialId(specialId);
        if (!EmptyUtils.isEmpty(openId)){
            infos.setOpenId(openId);
        }
        //更新操作，不能因为externalId为null，就指定成openId，需要加一层判断
        if (!EmptyUtils.isEmpty(externalId)){
            infos.setExternalId(externalId);
        }
        else if (EmptyUtils.isEmpty(externalId) && EmptyUtils.isEmpty(infos.getExternalId())){
            infos.setExternalId(openId);
        }
        int affectedCnt = userInfosDao.updateByPrimaryKeySelective(infos);

        Checks.isTrue(affectedCnt == 1, "存在的用户，更新用户信息失败");
        return infos;
    }

    /**
     * 列出所有的用户 - 反正也没有多少，查完
     * @return
     */
    public List<UserInfos> listAll() {
        //查出所有没有被删除的
        UserInfosExample userInfosExample = new UserInfosExample();
        UserInfosExample.Criteria criteria = userInfosExample.createCriteria();
        criteria.andStatusEqualTo(0);

        //查询
        List<UserInfos> userInfos = userInfosDao.selectByExample(userInfosExample);
        return userInfos;
    }

    /**
     * 构建openId的数据（自己数据库表的数据） 和淘宝联盟拉取的数据之间的关系，主要以externalID作为中间连接关系
     * 连接openId - externalId - specialId之间的映射
     * @param specialId 拉去的条件之一，可以为空
     * @param externalId 拉取的条件之一，可以为空
     * @return
     */
    public int autoMap(String specialId, String externalId) {
        //设定初始参数
        int pageNo = 1;
        int pageSize = 50;
        int hasProcessCnt = 0;

        //首次查询
        JSONObject members = ddxInviteCodeManager.getInviterCodeManager(null, specialId, externalId, DdxInviteCodeManager.PublisherInfoType.specialId, pageNo, pageSize);
        int totalCount = PropertyValueResolver.getProperty(members, "data.total_count");

        //循环判定
        while (true) {
            JSONArray inviterList = PropertyValueResolver.getProperty(members, "data.inviter_list.map_data");
            for (int i = 0; i < inviterList.size(); i++) {
                Object item = inviterList.get(i);

                //插入数据库
                int affectedCnt = insertOrUpdate(item);
                Checks.isTrue(affectedCnt == 1, "建立自动映射失败 - 该淘宝联盟会员信息 - " + JSON.toJSONString(item));
            }

            //如果没有读取完，那么循环再读取
            hasProcessCnt += inviterList.size();
            if (hasProcessCnt >= totalCount) {
                break;
            }
            else {
                pageNo++;
                members = ddxInviteCodeManager.getInviterCodeManager(null, specialId, externalId, DdxInviteCodeManager.PublisherInfoType.specialId, pageNo, pageSize);
            }
        }

        return hasProcessCnt;
    }

    /**
     * 插入或者更新一个新用户的绑定
     * @param item
     * @return
     */
    private int insertOrUpdate(Object item){
        String specialId = PropertyValueResolver.getProperty(item, "special_id") + "";
        String externalId = PropertyValueResolver.getProperty(item, "external_id") + "";

        //查询，是否存在
        UserInfosExample userInfosExample = new UserInfosExample();
        userInfosExample.setLimit(1);
        UserInfosExample.Criteria criteria = userInfosExample.createCriteria();
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andExternalIdEqualTo(specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            criteria.andExternalIdEqualTo(externalId);
        }
        criteria.andDataFromEqualTo("tb");
        List<UserInfos> userInfos = userInfosDao.selectByExample(userInfosExample);

        //不存在 - 直接插入
        if (EmptyUtils.isEmpty(userInfos)) {
            UserInfos userInfo = new UserInfos();
            userInfo.setGmtCreated(new Date());
            userInfo.setGmtModified(new Date());
            userInfo.setOpenId(null);
            userInfo.setOpenName(null);
            userInfo.setExternalId(externalId);
            userInfo.setSpecialId(specialId);
            userInfo.setRelationId(null);
            userInfo.setDataFrom("tb");
            userInfo.setStatus(0);

            int affectedCnt = userInfosDao.insertSelective(userInfo);
            //应该能插入成功
            Checks.isTrue(affectedCnt == 1, "新用户插入失败 - " + JSON.toJSONString(userInfo));
            return affectedCnt;
        }

        //存在，则直接更新
        UserInfos infos = userInfos.get(0);
        infos.setSpecialId(specialId);
        infos.setExternalId(externalId);
        infos.setGmtModified(new Date());
        int affectedCnt = userInfosDao.updateByPrimaryKey(infos);
        Checks.isTrue(affectedCnt == 1, "更新用户信息失败 - " + JSON.toJSONString(infos));
        return affectedCnt;
    }

    /**
     * 查询用户信息
     * @param openId
     * @return
     */
    public UserInfos selectByOpenId(String openId) {
        return selectByOpenIdAndSpecialIdAndExternalId(openId, null, null);
    }

    /**
     * 查询用户信息
     * @param specialId
     * @return
     */
    public UserInfos selectBySpecialId(String specialId) {
        return selectByOpenIdAndSpecialIdAndExternalId(null, specialId, null);
    }

    /**
     * 查询用户信息
     * @param externalId
     * @return
     */
    public UserInfos selectByExternalId(String externalId) {
        return selectByOpenIdAndSpecialIdAndExternalId(null, null, externalId);
    }

    /**
     * 查询用户信息
     * @param openId
     * @param specialId
     * @return
     */
    public UserInfos selectByOpenIdAndSpecialId(String openId, String specialId) {
        return selectByOpenIdAndSpecialIdAndExternalId(openId, specialId, null);
    }

    /**
     * 查询用户信息
     * @param openId
     * @param specialId
     * @return
     */
    public UserInfos selectByOpenIdAndSpecialIdAndExternalId(String openId, String specialId, String externalId) {
        UserInfosExample userInfosExample = new UserInfosExample();
        userInfosExample.setLimit(1);
        UserInfosExample.Criteria criteria = userInfosExample.createCriteria();

        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            criteria.andExternalIdEqualTo(externalId);
        }

        //查询 - 按理应该都存在的
        List<UserInfos> userInfos = userInfosDao.selectByExample(userInfosExample);
        if (userInfos.isEmpty()) {
            return null;
        }
        return userInfos.get(0);
    }

    /**
     * 更新用户信息
     * @param userInfos
     * @return
     */
    public int update(UserInfos userInfos) {
        userInfos.setGmtModified(new Date(System.currentTimeMillis()));
        int affectedCnt = userInfosDao.updateByPrimaryKeySelective(userInfos);
        return affectedCnt;
    }
}
