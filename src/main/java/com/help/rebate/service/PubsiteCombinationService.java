package com.help.rebate.service;

import com.help.rebate.dao.PubsiteCombinationDao;
import com.help.rebate.dao.entity.PubsiteCombination;
import com.help.rebate.dao.entity.PubsiteCombinationExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 推广位操作服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class PubsiteCombinationService {
    private static final Logger logger = LoggerFactory.getLogger(PubsiteCombinationService.class);

    /**
     * 推广位操作
     */
    @Autowired
    private PubsiteCombinationDao pubsiteCombinationDao;

    /**
     * 构建组合
     * @param tklType
     * @param vipIds
     * @param pubSites
     * @return
     */
    public int buildOrDoNone(String tklType, String vipIds, String pubSites) {
        String[] vipIdArray = vipIds.split(",");
        String[] pubSiteArray = pubSites.split(",");

        //双层循环构建
        int affectedCnt = 0;
        for (String vipId : vipIdArray) {
            for (String pubSite : pubSiteArray) {
                //判断是否存在
                PubsiteCombinationExample pubsiteCombinationExample = new PubsiteCombinationExample();
                pubsiteCombinationExample.setLimit(1);
                PubsiteCombinationExample.Criteria criteria = pubsiteCombinationExample.createCriteria();
                criteria.andStatusEqualTo(0);
                criteria.andTklTypeEqualTo(tklType);
                criteria.andVipIdEqualTo(vipId);
                criteria.andPubSiteEqualTo(pubSite);
                List<PubsiteCombination> pubsiteCombinations = pubsiteCombinationDao.selectByExample(pubsiteCombinationExample);

                //存在，就跳过
                if (!EmptyUtils.isEmpty(pubsiteCombinations)) {
                    continue;
                }

                //不存在，则插入
                PubsiteCombination pubsiteCombination = new PubsiteCombination();
                pubsiteCombination.setGmtCreated(new Date());
                pubsiteCombination.setGmtModified(new Date());
                pubsiteCombination.setTklType(tklType);
                pubsiteCombination.setVipId(vipId);
                pubsiteCombination.setPubSite(pubSite);
                pubsiteCombination.setStatus(0);
                int affectedCnt2 = pubsiteCombinationDao.insertSelective(pubsiteCombination);
                Checks.isTrue(affectedCnt2 == 1, "插入新组合失败，终止构建");
                affectedCnt += affectedCnt2;
            }
        }

        return affectedCnt;
    }

    /**
     * 列出所有的组合
     * @return
     */
    public List<PubsiteCombination> listAll(String tklType, String vipId) {
        PubsiteCombinationExample pubsiteCombinationExample = new PubsiteCombinationExample();
        PubsiteCombinationExample.Criteria criteria = pubsiteCombinationExample.createCriteria();
        if (!EmptyUtils.isEmpty(tklType)) {
            criteria.andTklTypeEqualTo(tklType);
        }
        if (!EmptyUtils.isEmpty(vipId)) {
            criteria.andVipIdEqualTo(vipId);
        }
        criteria.andStatusEqualTo(0);

        //查询
        List<PubsiteCombination> pubsiteCombinations = pubsiteCombinationDao.selectByExample(pubsiteCombinationExample);
        return pubsiteCombinations;
    }
}
