package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoTklConvertHistoryInfoDao;
import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfo;
import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfoExample;
import com.help.rebate.service.dtk.tb.DtkReturnPriceService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单映射数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoTklConvertHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoTklConvertHistoryService.class);

    /**
     * 淘口令转链历史记录表接口
     */
    @Resource
    private V2TaobaoTklConvertHistoryInfoDao v2TaobaoTklConvertHistoryInfoDao;

    /**
     * 通过商品ID查询是否被转链接过
     * @param itemId 商品ID
     * @param pubSite 推广位
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return
     */
    public List<V2TaobaoTklConvertHistoryInfo> selectByItemId(String itemId, String pubSite, LocalDateTime startTime, LocalDateTime endTime) {
        V2TaobaoTklConvertHistoryInfoExample tklConvertHistoryExample = new V2TaobaoTklConvertHistoryInfoExample();
        tklConvertHistoryExample.setLimit(200);
        V2TaobaoTklConvertHistoryInfoExample.Criteria criteria01 = tklConvertHistoryExample.or();
        V2TaobaoTklConvertHistoryInfoExample.Criteria criteria02 = tklConvertHistoryExample.or();

        if (!EmptyUtils.isEmpty(itemId)) {
            criteria01.andItemIdEqualTo(itemId);
            criteria02.andItemIdEqualTo(itemId);
        }
        if (!EmptyUtils.isEmpty(pubSite)) {
            //这里现在只默认查询的是 虚拟virtual，会员ID为唯一的virtualId //virtualId|mm_120037479_18710025_65896653
            criteria01.andPubsiteCombinationEqualTo("virtualId|" + pubSite);
            criteria02.andPubsiteCombinationEqualTo("virtualId|" + pubSite);
        }

        if (startTime != null) {
            criteria01.andGmtModifiedGreaterThanOrEqualTo(startTime);
            criteria02.andGmtCreatedGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria01.andGmtModifiedLessThanOrEqualTo(endTime);
            criteria02.andGmtCreatedLessThanOrEqualTo(endTime);
        }

        //查询
        List<V2TaobaoTklConvertHistoryInfo> tklConvertHistoryList = v2TaobaoTklConvertHistoryInfoDao.selectByExample(tklConvertHistoryExample);
        return tklConvertHistoryList;
    }

    /**
     * 保存一个新对象
     * @param tklConvertHistory
     * @return
     */
    public int save(V2TaobaoTklConvertHistoryInfo tklConvertHistory) {
        int affectedCnt = v2TaobaoTklConvertHistoryInfoDao.insertSelective(tklConvertHistory);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param tklConvertHistory
     * @return
     */
    public int update(V2TaobaoTklConvertHistoryInfo tklConvertHistory) {
        int affectedCnt = v2TaobaoTklConvertHistoryInfoDao.updateByPrimaryKeySelective(tklConvertHistory);
        return affectedCnt;
    }

    /**
     * 存储转链记录
     *
     * @param tkl
     * @param openId
     * @param specialId
     * @param pubSiteType
     * @param newTkl
     * @param pubSiteCombination
     */
    public void storeConvertRecord(String tkl, String openId, String specialId, String pubSiteType,
                                    DtkReturnPriceService.TklDO newTkl, String pubSiteCombination) {
        V2TaobaoTklConvertHistoryInfo tklConvertHistory = new V2TaobaoTklConvertHistoryInfo();
        tklConvertHistory.setGmtCreated(LocalDateTime.now());
        tklConvertHistory.setGmtModified(LocalDateTime.now());
        tklConvertHistory.setOpenId(openId);

        //会员转码，该字段传入的是null，因为不需要
        tklConvertHistory.setPubsiteCombination(pubSiteCombination);
        tklConvertHistory.setTkl(Base64.encodeBase64String(tkl.getBytes()));
        tklConvertHistory.setNewTkl(Base64.encodeBase64String(newTkl.getTkl().getBytes()));
        tklConvertHistory.setItemId(newTkl.getItemId());
        tklConvertHistory.setPubSiteType(pubSiteType);
        if (!EmptyUtils.isEmpty(specialId)) {
            tklConvertHistory.setAttachInfo("specialId=" + specialId);
        }
        tklConvertHistory.setStatus((byte) 0);

        int affectedCnt = v2TaobaoTklConvertHistoryInfoDao.insertSelective(tklConvertHistory);
        Checks.isTrue(affectedCnt == 1, "存储新生成的淘口令失败");
        return;
    }

    /**
     * 获取转链历史，通过商品ID，和转链类型
     * @param pubSiteType
     * @param itemId
     * @param openId
     * @param pubSiteCombination
     * @param limit
     * @return
     */
    public List<V2TaobaoTklConvertHistoryInfo> getTklConvertHistories(String pubSiteType, String itemId, String openId, String pubSiteCombination, int limit) {
        V2TaobaoTklConvertHistoryInfoExample historyExample = new V2TaobaoTklConvertHistoryInfoExample();
        if (limit > 0) {
            historyExample.setLimit(limit);
        }

        //针对动态itemId，重构一下
        String[] itemIds = itemId.split("-");
        itemIds[0] = itemId;
        List<String> staticAndDynamicItemId = Arrays.stream(itemIds).collect(Collectors.toList());

        V2TaobaoTklConvertHistoryInfoExample.Criteria criteria = historyExample.createCriteria();
        criteria.andItemIdIn(staticAndDynamicItemId);
        criteria.andPubSiteTypeEqualTo(pubSiteType);
        criteria.andOpenIdEqualTo(openId);

        //这种表示，只查某个特定推广位的
        if (pubSiteCombination != null) {
            criteria.andPubsiteCombinationEqualTo(pubSiteCombination);
        }

        //我们只对7天内转链的商品负责 - 7 * 24 * 3600 * 1000
        criteria.andGmtCreatedGreaterThanOrEqualTo(LocalDateTime.now().minusDays(7));
        criteria.andStatusEqualTo((byte) 0);

        //查询
        List<V2TaobaoTklConvertHistoryInfo> tklConvertHistories = v2TaobaoTklConvertHistoryInfoDao.selectByExample(historyExample);
        return tklConvertHistories;
    }
}
