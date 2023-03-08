package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoOrderOfflineAccountDetailDao;
import com.help.rebate.dao.V2TaobaoPubsiteCombinationInfoDao;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOfflineAccountDetail;
import com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfo;
import com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfoExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推广位操作服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
@Transactional
public class V2TaobaoOrderOfflineAccountDetailService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoOrderOfflineAccountDetailService.class);

    /**
     * 线下结算
     */
    @Resource
    private V2TaobaoOrderOfflineAccountDetailDao v2TaobaoOrderOfflineAccountDetailDao;


    /**
     * 插入新的记录
     * @param v2TaobaoOrderDetailInfo
     * @param commissionMsg
     * @return
     */
    public int insertNewRecord(V2TaobaoOrderDetailInfo v2TaobaoOrderDetailInfo, String commissionMsg) {
        V2TaobaoOrderOfflineAccountDetail offlineAccountDetail = new V2TaobaoOrderOfflineAccountDetail();

        offlineAccountDetail.setGmtCreate(LocalDateTime.now());
        offlineAccountDetail.setGmtModified(LocalDateTime.now());

        offlineAccountDetail.setTaobaoOrderId(Long.valueOf(v2TaobaoOrderDetailInfo.getId()));
        offlineAccountDetail.setTradeId(v2TaobaoOrderDetailInfo.getTradeId());
        offlineAccountDetail.setTradeParentId(v2TaobaoOrderDetailInfo.getTradeParentId());

        //计算
        String pubShareFee = v2TaobaoOrderDetailInfo.getPubShareFee();
        BigDecimal result = new BigDecimal(pubShareFee).multiply(new BigDecimal(0.9));
        String commission = NumberUtil.format(result);
        offlineAccountDetail.setCommission(commission);
        offlineAccountDetail.setCommissionMsg(commissionMsg);

        //插入
        int affectedCnt = v2TaobaoOrderOfflineAccountDetailDao.insertSelective(offlineAccountDetail);
        return affectedCnt;
    }
}
