package com.help.rebate.service.schedule;

import com.help.rebate.dao.entity.TimeCursorPosition;
import com.help.rebate.service.OrderBindService;
import com.help.rebate.service.TimeCursorPositionService;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.vo.OrderBindResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 固定时间订单绑定任务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Component
public class FixedOrderBindSyncTask {
    private static final Logger logger = LoggerFactory.getLogger(FixedOrderBindSyncTask.class);

    /**
     * 运行标志，表示是否运行
     */
    private boolean running = false;

    /**
     * 表示当前的位置缓存
     */
    private Date startTime;
    private Date endTime;
    private int secondStep;

    /**
     * 需要的令牌个数
     */
    private int tokenCnt = 2;


    /**
     * 速度控制器
     */
    private RateLimiterManager rateLimiterManager = new RateLimiterManager();

    /**
     * 订单绑定服务
     */
    @Resource
    private OrderBindService orderBindService;

    /**
     * 时间游标
     */
    @Resource
    private TimeCursorPositionService timeCursorPositionService;

    /**
     * 周期调度执行器
     */
    @Scheduled(cron = "*/2 * * * * ?")
    public void execute() {
        if (!running) {
            logger.debug("[fix-order-bind-sync-task] running flag:{}", running);
            return;
        }

        //获取令牌
        if (!rateLimiterManager.acquire(tokenCnt)) {
            logger.info("[fix-order-bind-sync-task] rate limit: token is not enough");
            return;
        }

        //获取同步开始时间
        if (startTime == null || endTime == null) {
            fetchSyncTime();
        }

        //二次判定
        if (startTime == null || endTime == null) {
            logger.info("[fix-order-bind-sync-task] find not any sync time config, skip sync");
            return;
        }

        //时间判定
        long endMillis = endTime.getTime();
        if (System.currentTimeMillis() < endMillis) {
            logger.info("[fix-order-bind-sync-task] 当前时间小于结束时间[{}], 跳过本次绑定执行", TimeUtil.format(endMillis));
            return;
        }

        //执行
        try {
            syncBindOrder();
        } catch(Exception e) {
            logger.info("[fix-order-bind-sync-task] error to sync bind order", e);
        }
    }

    /**
     * 执行订单同步
     * query_type - 查询时间类型，1-订单创建时间，2-订单支付时间，3-订单结算时间，4-订单更新时间
     * 场景订单类型，1-常规订单，2-渠道订单，3-会员运营订单，0-都查
     * 查询时间场景 - 查询时间类型，1-订单创建时间，2-订单支付时间，3-订单结算时间，4-订单更新时间，0-都查
     */
    private void syncBindOrder() {
        logger.info("[fix-order-bind-sync-task] sync bind order - time range[{}, {}]",
                TimeUtil.format(startTime), TimeUtil.format(endTime));

        //调用服务，按时间范围查找并绑定
        List<OrderBindResultVO> orderBindResultVOS = orderBindService.bindByTimeRange(null, null, TimeUtil.format(startTime), (long) (secondStep / 60));

        //log输出
        logOrderBindResults(orderBindResultVOS);

        //更改游标，并更新数据库
        startTime = new Date(startTime.getTime() + secondStep * 1000);
        endTime = new Date(endTime.getTime() + secondStep * 1000);
        timeCursorPositionService.saveOrUpdateTimeCursor(startTime, secondStep, -1, -1, TimeCursorPositionService.TimeType.ORDER_BIND_SYNC);
    }

    /**
     * 记录
     * @param orderBindResultVOS
     */
    private void logOrderBindResults(List<OrderBindResultVO> orderBindResultVOS) {
        if (EmptyUtils.isEmpty(orderBindResultVOS)) {
            logger.info("[fix-order-bind-sync-task] sync bind order - time range[{}, {}], no any bind order",
                    TimeUtil.format(startTime), TimeUtil.format(endTime));
            return;
        }

        //循环输出
        for (OrderBindResultVO orderBindResultVO : orderBindResultVOS) {
            String openId = orderBindResultVO.getOpenId();
            String specialId = orderBindResultVO.getSpecialId();
            String tradeParentId = orderBindResultVO.getTradeParentId();
            List<String> tradeIdItemIdList = orderBindResultVO.getTradeIdItemIdList();
            String tradeIds = tradeIdItemIdList.stream().collect(Collectors.joining(","));
            logger.info("[fix-order-bind-sync-task] sync bind order - time range[{}, {}], openId:{}, specialId:{}, tradeParentId:{}, tradeIds:{}",
                    TimeUtil.format(startTime), TimeUtil.format(endTime), openId, specialId, tradeParentId, tradeIds);
        }
    }

    /**
     * 获取时间点位
     */
    private void fetchSyncTime() {
        TimeCursorPosition timeCursorPosition = timeCursorPositionService.fetchOrderSyncTimePosition(TimeCursorPositionService.TimeType.ORDER_BIND_SYNC);
        if (timeCursorPosition == null) {
            return;
        }

        this.startTime = timeCursorPosition.getStartTime();
        this.endTime = timeCursorPosition.getEndTime();
        this.secondStep = timeCursorPosition.getStep();
    }

    /**
     *
     * @param orderBindTime
     * @param minuteStep
     * @param running
     */
    public void cleanContext(String orderBindTime, Long minuteStep, Boolean running) {
        this.startTime = null;
        this.endTime = null;
        this.running = running;
    }

    /**
     * 设置运行标志
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}
