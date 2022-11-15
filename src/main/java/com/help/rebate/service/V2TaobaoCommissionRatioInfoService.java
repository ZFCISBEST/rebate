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
     * @param openId
     * @return
     */
    public double selectCommissionRatio(String openId) {
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
            return 0.85;
        }

        //获取第一个,千分位的
        Integer commissionRatio = v2TaobaoCommissionRatioInfos.get(0).getCommissionRatio();
        if (commissionRatio == null || commissionRatio <= 0) {
            return 0.85;
        }

        return commissionRatio * 1.0 / 1000.0;
    }
}
