package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoOrderOpenidMapFailureInfoDao;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapFailureInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapFailureInfoExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单绑定服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
@Transactional
public class V2TaobaoOrderOpenidMapFailureService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoOrderOpenidMapFailureService.class);

    /**
     * 订单失败记录表
     */
    @Resource
    private V2TaobaoOrderOpenidMapFailureInfoDao v2TaobaoOrderOpenidMapFailureInfoDao;

    /**
     * 插入或者更新订单绑定失败的订单
     * @param orderDetailList
     * @param reason
     */
    public void insertOrDoNoneOrderInfo(List<V2TaobaoOrderDetailInfo> orderDetailList, String reason) {
        if (EmptyUtils.isEmpty(orderDetailList)) {
            return;
        }

        //循环插入
        for (V2TaobaoOrderDetailInfo orderDetail : orderDetailList) {
            V2TaobaoOrderOpenidMapFailureInfo orderOpenidMapFailure = selectFailureOrderByTradeId(orderDetail.getTradeId());

            //更新
            if (orderOpenidMapFailure != null) {
                continue;
            }
            //插入
            else {
                orderOpenidMapFailure = new V2TaobaoOrderOpenidMapFailureInfo();
                orderOpenidMapFailure.setGmtCreated(LocalDateTime.now());
                orderOpenidMapFailure.setGmtModified(LocalDateTime.now());
                orderOpenidMapFailure.setTradeId(orderDetail.getTradeId());
                orderOpenidMapFailure.setTradeParentId(orderDetail.getTradeParentId());
                orderOpenidMapFailure.setItemId(orderDetail.getItemId());
                orderOpenidMapFailure.setFailReason(reason);
                orderOpenidMapFailure.setStatus((byte) 0);
                int affectedCnt = v2TaobaoOrderOpenidMapFailureInfoDao.insert(orderOpenidMapFailure);
                Checks.isTrue(affectedCnt == 1, "记录未绑定订单失败");
            }
        }
    }

    /**
     * 获取失败的订单
     * @param tradeId
     * @return
     */
    private V2TaobaoOrderOpenidMapFailureInfo selectFailureOrderByTradeId(String tradeId) {
        V2TaobaoOrderOpenidMapFailureInfoExample example = new V2TaobaoOrderOpenidMapFailureInfoExample();
        example.setLimit(1);
        V2TaobaoOrderOpenidMapFailureInfoExample.Criteria criteria = example.createCriteria();
        criteria.andTradeIdEqualTo(tradeId);
        criteria.andStatusEqualTo((byte) 0);

        List<V2TaobaoOrderOpenidMapFailureInfo> orderOpenidMapFailures = v2TaobaoOrderOpenidMapFailureInfoDao.selectByExample(example);
        if (orderOpenidMapFailures.isEmpty()) {
            return null;
        }
        return orderOpenidMapFailures.get(0);
    }
}
