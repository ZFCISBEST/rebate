package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoCommissionAccountFlowInfoDao;
import com.help.rebate.dao.V2TaobaoCommissionAccountInfoDao;
import com.help.rebate.vo.CommissionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 提现服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoCommissionAccountService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoCommissionAccountService.class);

    /**
     * 账户流水dao
     */
    @Resource
    private V2TaobaoCommissionAccountFlowInfoDao v2TaobaoCommissionAccountFlowInfoDao;

    /**
     * 账户dao
     */
    @Resource
    private V2TaobaoCommissionAccountInfoDao v2TaobaoCommissionAccountInfoDao;

    /**
     * 返利比例服务
     */
    @Resource
    private V2TaobaoCommissionRatioInfoService v2TaobaoCommissionRatioInfoService;


    /**
     * 查询返利信息
     * 1、账户里的总值
     * 2、尚未走到结算的所有钱（剔除关闭的）
     * 3、所以，需要两者叠加给用户
     * @param openId
     * @param specialId
     * @return
     */
    public CommissionVO selectCommissionBy(String openId, String specialId) {
        return null;
    }
}
