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
     * @param fromUserName
     * @param specialId
     * @param s
     * @param 待提取
     * @param payStartTime
     * @param payEndTime
     * @return
     */
    public CommissionVO selectCommissionBy(String fromUserName, String specialId, String s, String 待提取, String payStartTime, String payEndTime) {
        return null;
    }
}
