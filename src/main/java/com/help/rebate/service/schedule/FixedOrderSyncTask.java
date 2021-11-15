package com.help.rebate.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 固定时间同步订单任务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Component
public class FixedOrderSyncTask {
    private static final Logger logger = LoggerFactory.getLogger(FixedOrderSyncTask.class);


    public void setRunning(boolean running) {

    }

    public void cleanContext(String orderUpdateTime, Long minuteStep, int orderScene, Integer queryType, Boolean running) {

    }
}
