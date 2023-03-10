package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoCommissionRatioInfoDao;
import com.help.rebate.dao.entity.V2TaobaoCommissionRatioInfo;
import com.help.rebate.dao.entity.V2TaobaoCommissionRatioInfoExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 千分位
     * @param openId
     * @return
     */
    public int selectCommissionRatio(String openId) {
        V2TaobaoCommissionRatioInfoExample info = new V2TaobaoCommissionRatioInfoExample();
        info.setOrderByClause("id desc");
        info.setLimit(1);

        //为openId专门的
        V2TaobaoCommissionRatioInfoExample.Criteria criteria = info.createCriteria();
        criteria.andStatusEqualTo((byte) 0);
        criteria.andOpenIdEqualTo(openId);
        List<V2TaobaoCommissionRatioInfo> v2TaobaoCommissionRatioInfos = v2TaobaoCommissionRatioInfoDao.selectByExample(info);
        if (v2TaobaoCommissionRatioInfos.size() == 0) {
            info.clear();
            //或者默认的，为空
            V2TaobaoCommissionRatioInfoExample.Criteria criteria1 = info.createCriteria();
            criteria1.andStatusEqualTo((byte) 0);
            criteria1.andOpenIdIsNull();

            v2TaobaoCommissionRatioInfos = v2TaobaoCommissionRatioInfoDao.selectByExample(info);
        }

        //返回一个默认值
        if (v2TaobaoCommissionRatioInfos.size() == 0) {
            return 850;
        }

        //获取第一个,千分位的
        Integer commissionRatio = v2TaobaoCommissionRatioInfos.get(0).getCommissionRatio();
        if (commissionRatio == null || commissionRatio <= 0) {
            return 850;
        }

        return commissionRatio;
    }

    /**
     * 直接计算动态返利比率
     * 千分位
     * @param totalCommissionRate 联盟官方返利比例, 佣金比率，百分位，如5.5，表示5.5%。
     * @return
     */
    public int queryDynamicCommissionRatio(String totalCommissionRate) {
        //这是默认值
        int commissionRatio = 850;

        try {
            //直接算出来一份
            double commissionRate = Double.parseDouble(totalCommissionRate);

            //判定
            if (commissionRate <= 10) {
                commissionRatio = 850;
            }
            else if (commissionRate <= 15) {
                commissionRatio = 800;
            }
            else if (commissionRate <= 25) {
                commissionRatio = 750;
            }
            else {
                commissionRatio = 700;
            }
        }
        catch (Throwable throwable) {
            logger.error("【queryDynamicCommissionRatio】计算动态返利出错, 将返回默认值{}", commissionRatio, throwable);
        }

        //分成这么几个档吧
        return commissionRatio;
    }

    /**
     * 直接计算动态返利比率
     * 千分位
     * @param totalCommissionRate 联盟官方返利比例, 佣金比率，百分位，如5.5，表示5.5%。
     * @param totalCommissionFee 联盟官方返利金额，是比率*结算金额来的
     * @return
     */
    public int queryDynamicCommissionRatio(String totalCommissionRate, String totalCommissionFee) {
        //这是默认值
        int commissionRatio = 850;

        //直接算出来一份
        double commissionRate = Double.parseDouble(totalCommissionRate);

        //判定
        if (commissionRate <= 10) {
            commissionRatio = 850;
        }
        else if (commissionRate <= 15) {
            commissionRatio = 800;
        }
        else if (commissionRate <= 25) {
            commissionRatio = 750;
        }
        else {
            commissionRatio = 700;
        }

        //分成这么几个档吧
        return commissionRatio;
    }
}
