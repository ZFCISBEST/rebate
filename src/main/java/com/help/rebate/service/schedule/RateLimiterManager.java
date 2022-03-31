package com.help.rebate.service.schedule;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 速度控制器
 *
 * @author zfcisbest
 * @date 21/11/14
 */
public class RateLimiterManager {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterManager.class);

    /**
     * 速度控制器
     */
    private RateLimiter rateLimiter = RateLimiter.create(1.0);

    /**
     * 初始化
     */
    public RateLimiterManager() {
    }

    /**
     * 请求若干个令牌
     * @param factor
     * @return
     */
    public boolean acquire(int factor) {
        return rateLimiter.tryAcquire(factor, 1, TimeUnit.SECONDS);
    }

    /**
     * 更新令牌速度
     * @param factor
     */
    public void update(int factor) {
        rateLimiter = RateLimiter.create(factor);
    }
}
