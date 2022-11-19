package com.help.rebate.service.schedule;

import com.help.rebate.service.V2TaobaoCommissionAccountService;
import com.help.rebate.service.V2TaobaoOrderBindService;
import com.help.rebate.service.V2TaobaoOrderService;
import com.help.rebate.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 固定时间将订单转结算
 *
 * @author zfcisbest
 * @date 21/11/14
 */
//@Component
public class FixedCommissionAccountTask {
    private static final Logger logger = LoggerFactory.getLogger(FixedCommissionAccountTask.class);

    /**
     * 需要的令牌个数
     */
    private int tokenCnt = 300;


    /**
     * 速度控制器
     */
    private RateLimiterManager rateLimiterManager = new RateLimiterManager();

    /**
     * 订单服务
     */
    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    /**
     * 订单绑定服务
     */
    @Resource
    private V2TaobaoOrderBindService v2TaobaoOrderBindService;

    /**
     * 账户服务
     */
    @Resource
    private V2TaobaoCommissionAccountService v2TaobaoCommissionAccountService;

    /**
     * 周期调度执行器
     */
    @Scheduled(cron = "*/2 * * * * ?")
    public void execute() {
        //获取令牌
        if (!rateLimiterManager.acquire(tokenCnt)) {
            logger.info("[fix-commission-account-task] rate limit: token is not enough[300]");
        }

        //执行
        try {

            //当前时间
            LocalDateTime tenMinuteAgo = LocalDateTime.now().minusMinutes(30);

            //十分钟以内的订单更新绑定
            String orderStartModifiedTime = TimeUtil.formatLocalDate(tenMinuteAgo);
            List<String> tradeParentIds = v2TaobaoCommissionAccountService.computeOrderDetailToAccount(orderStartModifiedTime, 30L);

            logger.info("[fix-commission-account-task] 转结算执行成功，时间范围:[{}, 分钟数:], 父订单列表:{}",
                    orderStartModifiedTime, 30, tradeParentIds.stream().collect(Collectors.joining(",")));
        } catch(Exception e) {
            logger.error("[fix-commission-account-task] 将绑定订单转结算发生错误", e);
        }
    }
}
