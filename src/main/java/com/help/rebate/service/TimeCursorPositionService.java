package com.help.rebate.service;

import com.help.rebate.dao.TimeCursorPositionDao;
import com.help.rebate.dao.entity.TimeCursorPosition;
import com.help.rebate.dao.entity.TimeCursorPositionExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 时间游标记录
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class TimeCursorPositionService {
    private static final Logger logger = LoggerFactory.getLogger(TimeCursorPositionService.class);

    /**
     * 时间游标
     */
    @Resource
    private TimeCursorPositionDao timeCursorPositionDao;

    /**
     * 更改时间点为
     * @param startTime
     * @param secondStep
     * @param orderScene
     * @param queryType
     */
    public void saveOrUpdateTimeCursor(Date startTime, int secondStep, Integer orderScene, Integer queryType, TimeType timeType) {
        TimeCursorPositionExample example = new TimeCursorPositionExample();
        example.setLimit(1);
        TimeCursorPositionExample.Criteria criteria = example.createCriteria();
        criteria.andTimeTypeEqualTo(0);
        criteria.andStatusEqualTo(0);
        List<TimeCursorPosition> timeCursorPositions = timeCursorPositionDao.selectByExample(example);

        Date endTime = new Date(startTime.getTime() + secondStep * 1000);

        //存在 - 更新
        if (!EmptyUtils.isEmpty(timeCursorPositions)) {
            TimeCursorPosition timeCursorPosition = timeCursorPositions.get(0);
            timeCursorPosition.setGmtModified(new Date());
            timeCursorPosition.setStartTime(startTime);
            timeCursorPosition.setEndTime(endTime);
            timeCursorPosition.setStep(secondStep);
            timeCursorPosition.setQueryType(queryType);
            timeCursorPosition.setSubType(orderScene);
            timeCursorPosition.setTimeType(timeType.getTimeType());

            int affectedCnt = timeCursorPositionDao.updateByPrimaryKey(timeCursorPosition);
            Checks.isTrue(affectedCnt == 1, "更新时间点为失败，无法同步");
        }

        //不存在 - 插入
        else {
            TimeCursorPosition timeCursorPosition = new TimeCursorPosition();
            timeCursorPosition.setGmtCreated(new Date());
            timeCursorPosition.setGmtModified(new Date());
            timeCursorPosition.setStartTime(startTime);
            timeCursorPosition.setEndTime(endTime);
            timeCursorPosition.setStep(secondStep);
            timeCursorPosition.setQueryType(queryType);
            timeCursorPosition.setSubType(orderScene);
            timeCursorPosition.setTimeType(timeType.getTimeType());
            timeCursorPosition.setStatus(0);

            int affectedCnt = timeCursorPositionDao.insertSelective(timeCursorPosition);
            Checks.isTrue(affectedCnt == 1, "插入时间点为失败，无法同步");
        }
    }

    /**
     * 获取订单同步的时间点位
     * @param timeType
     * @return
     */
    public TimeCursorPosition fetchOrderSyncTimePosition(TimeType timeType) {
        TimeCursorPositionExample example = new TimeCursorPositionExample();
        example.setLimit(1);
        TimeCursorPositionExample.Criteria criteria = example.createCriteria();
        criteria.andTimeTypeEqualTo(timeType.getTimeType());
        criteria.andStatusEqualTo(0);
        List<TimeCursorPosition> timeCursorPositions = timeCursorPositionDao.selectByExample(example);

        if (EmptyUtils.isEmpty(timeCursorPositions)) {
            return null;
        }

        return timeCursorPositions.get(0);
    }

    public enum TimeType {
        ORDER_SYNC(0),

        ORDER_BIND_SYNC(1),

        ORDER_JIESUAN(2);

        private final int timeType;

        TimeType(int timeType) {
            this.timeType = timeType;
        }

        public int getTimeType() {
            return timeType;
        }
    }
}
