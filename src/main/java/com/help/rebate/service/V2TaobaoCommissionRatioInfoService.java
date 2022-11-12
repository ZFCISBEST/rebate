package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoCommissionRatioInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推广位操作服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoCommissionRatioInfoService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoCommissionRatioInfoService.class);

    /**
     * 推广位操作
     */
    @Resource
    private V2TaobaoCommissionRatioInfoDao v2TaobaoCommissionRatioInfoDao;


    /**
     * 查询返利比例
     * @param openId
     * @return
     */
    public double selectCommissionRatio(String openId) {
        return 0.85;
    }
}
