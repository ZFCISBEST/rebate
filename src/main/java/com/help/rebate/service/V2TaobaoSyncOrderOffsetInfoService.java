package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoSyncOrderOffsetInfoDao;
import com.help.rebate.dao.entity.V2TaobaoSyncOrderOffsetInfo;
import com.help.rebate.dao.entity.V2TaobaoSyncOrderOffsetInfoExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 时间游标记录
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoSyncOrderOffsetInfoService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoSyncOrderOffsetInfoService.class);

    /**
     * 时间游标
     */
    @Resource
    private V2TaobaoSyncOrderOffsetInfoDao v2TaobaoSyncOrderOffsetInfoDao;

    /**
     * 更改时间点为
     * @param startTime
     * @param secondStep
     * @param orderScene
     * @param queryType
     */
    public void upsertSyncStartTimeOffset(LocalDateTime startTime, int secondStep, Integer orderScene, Integer queryType) {
        V2TaobaoSyncOrderOffsetInfoExample example = new V2TaobaoSyncOrderOffsetInfoExample();
        example.setLimit(1);
        example.setOrderByClause("gmt_modified desc");
        V2TaobaoSyncOrderOffsetInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte) 0);
        List<V2TaobaoSyncOrderOffsetInfo> syncOrderOffsetInfos = v2TaobaoSyncOrderOffsetInfoDao.selectByExample(example);

        //结束点位
        LocalDateTime endTime = startTime.plusSeconds(secondStep);

        //存在 - 更新
        if (!EmptyUtils.isEmpty(syncOrderOffsetInfos)) {
            V2TaobaoSyncOrderOffsetInfo v2TaobaoSyncOrderOffsetInfo = syncOrderOffsetInfos.get(0);
            v2TaobaoSyncOrderOffsetInfo.setGmtModified(LocalDateTime.now());
            v2TaobaoSyncOrderOffsetInfo.setStartTime(startTime);
            v2TaobaoSyncOrderOffsetInfo.setEndTime(endTime);
            v2TaobaoSyncOrderOffsetInfo.setStep(secondStep);

            v2TaobaoSyncOrderOffsetInfo.setSyncOrderType(orderScene);
            v2TaobaoSyncOrderOffsetInfo.setSyncTimeType((byte) queryType.intValue());

            int affectedCnt = v2TaobaoSyncOrderOffsetInfoDao.updateByPrimaryKey(v2TaobaoSyncOrderOffsetInfo);
            Checks.isTrue(affectedCnt == 1, "更新时间点失败，原因未知");
        }

        //不存在 - 插入
        else {
            V2TaobaoSyncOrderOffsetInfo v2TaobaoSyncOrderOffsetInfo = new V2TaobaoSyncOrderOffsetInfo();
            v2TaobaoSyncOrderOffsetInfo.setGmtCreated(LocalDateTime.now());
            v2TaobaoSyncOrderOffsetInfo.setGmtModified(LocalDateTime.now());
            v2TaobaoSyncOrderOffsetInfo.setStartTime(startTime);
            v2TaobaoSyncOrderOffsetInfo.setEndTime(endTime);
            v2TaobaoSyncOrderOffsetInfo.setStep(secondStep);
            v2TaobaoSyncOrderOffsetInfo.setSyncTimeType((byte) queryType.intValue());
            v2TaobaoSyncOrderOffsetInfo.setSyncOrderType(orderScene);
            v2TaobaoSyncOrderOffsetInfo.setStatus((byte) 0);

            int affectedCnt = v2TaobaoSyncOrderOffsetInfoDao.insertSelective(v2TaobaoSyncOrderOffsetInfo);
            Checks.isTrue(affectedCnt == 1, "插入时间点位失败，原因未知");
        }
    }

    /**
     * 获取订单同步的时间点位
     * @return
     */
    public V2TaobaoSyncOrderOffsetInfo selectOrderSyncTimeOffset() {
        V2TaobaoSyncOrderOffsetInfoExample example = new V2TaobaoSyncOrderOffsetInfoExample();
        example.setLimit(1);
        V2TaobaoSyncOrderOffsetInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte) 0);
        List<V2TaobaoSyncOrderOffsetInfo> v2TaobaoSyncOrderOffsetInfos = v2TaobaoSyncOrderOffsetInfoDao.selectByExample(example);

        if (EmptyUtils.isEmpty(v2TaobaoSyncOrderOffsetInfos)) {
            return null;
        }

        return v2TaobaoSyncOrderOffsetInfos.get(0);
    }
}
