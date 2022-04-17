package com.help.rebate.service;

import com.help.rebate.dao.TklConvertHistoryDao;
import com.help.rebate.dao.entity.TklConvertHistory;
import com.help.rebate.dao.entity.TklConvertHistoryExample;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单映射数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class TklConvertHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(TklConvertHistoryService.class);

    /**
     * 淘口令转链历史记录表接口
     */
    @Resource
    private TklConvertHistoryDao tklConvertHistoryDao;

    /**
     * 通过商品ID查询是否被转链接过
     * @param itemId 商品ID
     * @param pubSite 推广位
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return
     */
    public List<TklConvertHistory> selectByItemId(String itemId, String pubSite, Date startTime, Date endTime) {
        TklConvertHistoryExample tklConvertHistoryExample = new TklConvertHistoryExample();
        tklConvertHistoryExample.setLimit(20);
        TklConvertHistoryExample.Criteria criteria = tklConvertHistoryExample.createCriteria();

        if (!EmptyUtils.isEmpty(itemId)) {
            criteria.andItemIdEqualTo(itemId);
        }
        if (!EmptyUtils.isEmpty(pubSite)) {
            //这里现在只默认查询的是 虚拟virtual，会员ID为唯一的virtualId //virtualId|mm_120037479_18710025_65896653
            criteria.andPubsiteCombinationEqualTo("virtualId|" + pubSite);
        }
        if (startTime != null) {
            criteria.andGmtModifiedGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andGmtModifiedLessThanOrEqualTo(endTime);
        }

        //排序
        //tklConvertHistoryExample.setOrderByClause();

        //查询
        List<TklConvertHistory> tklConvertHistoryList = tklConvertHistoryDao.selectByExample(tklConvertHistoryExample);
        return tklConvertHistoryList;
    }

    /**
     * 保存一个新对象
     * @param tklConvertHistory
     * @return
     */
    public int save(TklConvertHistory tklConvertHistory) {
        int affectedCnt = tklConvertHistoryDao.insertSelective(tklConvertHistory);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param tklConvertHistory
     * @return
     */
    public int update(TklConvertHistory tklConvertHistory) {
        int affectedCnt = tklConvertHistoryDao.updateByPrimaryKeySelective(tklConvertHistory);
        return affectedCnt;
    }
}
