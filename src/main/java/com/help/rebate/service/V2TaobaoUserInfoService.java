package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoUserInfoDao;
import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.dao.entity.V2TaobaoUserInfoExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息服务类
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoUserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoUserInfoService.class);

    /**
     * 用户操作
     */
    @Resource
    private V2TaobaoUserInfoDao v2TaobaoUserInfoDao;

    /**
     * 插入一个新用户
     * @param openId
     * @param relationId
     * @param specialId
     * @param externalId
     * @return
     */
    public V2TaobaoUserInfo insertOrDoNone(String openId, String relationId, String specialId, String externalId) {
        //首先查看是否存在
        V2TaobaoUserInfoExample example = new V2TaobaoUserInfoExample();
        example.setLimit(1);
        example.setOrderByClause("gmt_modified desc");
        V2TaobaoUserInfoExample.Criteria criteria = example.createCriteria();
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        List<V2TaobaoUserInfo> userInfos = v2TaobaoUserInfoDao.selectByExample(example);

        //如果为空，则插入
        if (userInfos == null || userInfos.isEmpty()) {
            V2TaobaoUserInfo userInfo = new V2TaobaoUserInfo();
            userInfo.setGmtCreated(LocalDateTime.now());
            userInfo.setGmtModified(LocalDateTime.now());
            userInfo.setOpenId(openId);
            //因为暂时也拿不到
            userInfo.setOpenName(null);
            userInfo.setExternalId(externalId);
            userInfo.setSpecialId(specialId);
            userInfo.setRelationId(relationId);
            userInfo.setStatus((byte) 0);

            int affectedCnt = v2TaobaoUserInfoDao.insertSelective(userInfo);
            //应该能插入成功
            Checks.isTrue(affectedCnt == 1, "插入失败，原因未知");
            return userInfo;
        }

        //执行更新
        V2TaobaoUserInfo info = userInfos.get(0);
        info.setOpenId(openId);
        info.setGmtModified(LocalDateTime.now());
        info.setRelationId(relationId);
        info.setSpecialId(specialId);
        //更新操作，不能因为externalId为null，就指定成openId，需要加一层判断
        info.setExternalId(externalId);
        int affectedCnt = v2TaobaoUserInfoDao.updateByPrimaryKeySelective(info);

        Checks.isTrue(affectedCnt == 1, "更新失败，原因未知");
        return info;
    }

    /**
     * 列出所有的用户 - 反正也没有多少，查完
     * @return
     */
    public List<V2TaobaoUserInfo> listAll() {
        //查出所有没有被删除的
        V2TaobaoUserInfoExample example = new V2TaobaoUserInfoExample();
        V2TaobaoUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte) 0);

        //查询
        List<V2TaobaoUserInfo> userInfos = v2TaobaoUserInfoDao.selectByExample(example);
        return userInfos;
    }

    /**
     * 查询用户信息
     * @param openId
     * @return
     */
    public V2TaobaoUserInfo selectByOpenId(String openId) {
        return selectByOpenIdAndSpecialIdAndExternalId(openId, null, null);
    }

    /**
     * 查询用户信息
     * @param specialId
     * @return
     */
    public V2TaobaoUserInfo selectBySpecialId(String specialId) {
        return selectByOpenIdAndSpecialIdAndExternalId(null, specialId, null);
    }

    /**
     * 查询用户信息
     * @param externalId
     * @return
     */
    public V2TaobaoUserInfo selectByExternalId(String externalId) {
        return selectByOpenIdAndSpecialIdAndExternalId(null, null, externalId);
    }

    /**
     * 查询用户信息
     * @param openId
     * @param specialId
     * @return
     */
    public V2TaobaoUserInfo selectByOpenIdAndSpecialId(String openId, String specialId) {
        return selectByOpenIdAndSpecialIdAndExternalId(openId, specialId, null);
    }

    /**
     * 查询用户信息
     * @param openId
     * @param specialId
     * @return
     */
    public V2TaobaoUserInfo selectByOpenIdAndSpecialIdAndExternalId(String openId, String specialId, String externalId) {
        V2TaobaoUserInfoExample example = new V2TaobaoUserInfoExample();
        example.setLimit(1);
        example.setOrderByClause("gmt_modified desc");
        V2TaobaoUserInfoExample.Criteria criteria = example.createCriteria();

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
        List<V2TaobaoUserInfo> userInfos = v2TaobaoUserInfoDao.selectByExample(example);
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
    public int update(V2TaobaoUserInfo userInfos) {
        userInfos.setGmtModified(LocalDateTime.now());
        int affectedCnt = v2TaobaoUserInfoDao.updateByPrimaryKeySelective(userInfos);
        return affectedCnt;
    }
}
