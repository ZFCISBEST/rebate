package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoPubsiteCombinationInfoDao;
import com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfo;
import com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfoExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class V2TaobaoPubSiteService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoPubSiteService.class);

    /**
     * 推广位操作
     */
    @Resource
    private V2TaobaoPubsiteCombinationInfoDao v2TaobaoPubsiteCombinationInfoDao;

    /**
     * 构建组合
     * @param pubSiteType
     * @param vipIds
     * @param pubSites
     * @return
     */
    public int buildOrDoNone(String pubSiteType, String vipIds, String pubSites) {
        String[] vipIdArray = vipIds.split(",");
        String[] pubSiteArray = pubSites.split(",");

        //双层循环构建
        int affectedCnt = 0;
        for (String vipId : vipIdArray) {
            for (String pubSite : pubSiteArray) {
                //判断是否存在
                V2TaobaoPubsiteCombinationInfoExample pubsiteCombinationExample = new V2TaobaoPubsiteCombinationInfoExample();
                pubsiteCombinationExample.setLimit(1);
                V2TaobaoPubsiteCombinationInfoExample.Criteria criteria = pubsiteCombinationExample.createCriteria();
                criteria.andStatusEqualTo((byte) 0);
                criteria.andPubSiteTypeEqualTo(pubSiteType);
                criteria.andVipIdEqualTo(vipId);
                criteria.andPubSiteEqualTo(pubSite);
                List<V2TaobaoPubsiteCombinationInfo> pubsiteCombinations = v2TaobaoPubsiteCombinationInfoDao.selectByExample(pubsiteCombinationExample);

                //存在，就跳过
                if (!EmptyUtils.isEmpty(pubsiteCombinations)) {
                    continue;
                }

                //不存在，则插入
                V2TaobaoPubsiteCombinationInfo pubsiteCombination = new V2TaobaoPubsiteCombinationInfo();
                pubsiteCombination.setGmtCreated(LocalDateTime.now());
                pubsiteCombination.setGmtModified(LocalDateTime.now());
                pubsiteCombination.setPubSiteType(pubSiteType);
                pubsiteCombination.setVipId(vipId);
                pubsiteCombination.setPubSite(pubSite);
                pubsiteCombination.setStatus((byte) 0);
                int affectedCnt2 = v2TaobaoPubsiteCombinationInfoDao.insertSelective(pubsiteCombination);
                Checks.isTrue(affectedCnt2 == 1, "插入新组合失败，原因未知，终止构建");
                affectedCnt += affectedCnt2;
            }
        }

        return affectedCnt;
    }

    /**
     * 列出所有的组合
     * @return
     */
    public List<V2TaobaoPubsiteCombinationInfo> listAll(String pubSiteType, String vipId) {
        V2TaobaoPubsiteCombinationInfoExample pubsiteCombinationExample = new V2TaobaoPubsiteCombinationInfoExample();
        V2TaobaoPubsiteCombinationInfoExample.Criteria criteria = pubsiteCombinationExample.createCriteria();
        if (!EmptyUtils.isEmpty(pubSiteType)) {
            criteria.andPubSiteTypeEqualTo(pubSiteType);
        }
        if (!EmptyUtils.isEmpty(vipId)) {
            criteria.andVipIdEqualTo(vipId);
        }
        criteria.andStatusEqualTo((byte) 0);

        //查询
        List<V2TaobaoPubsiteCombinationInfo> pubsiteCombinations = v2TaobaoPubsiteCombinationInfoDao.selectByExample(pubsiteCombinationExample);
        return pubsiteCombinations;
    }

    /**
     * 获取所有相关类型的推广位组合
     * @param pubSiteType
     * @return
     */
    public List<String> getAllPubSiteCombinations(String pubSiteType) {
        List<V2TaobaoPubsiteCombinationInfo> combinationInfoList = listAll(pubSiteType, null);

        //不能为空
        Checks.isNotEmpty(combinationInfoList, "没有任何可用的推广位组合");

        List<String> allComb = combinationInfoList.stream().map(a -> a.getVipId() + "|" + a.getPubSite()).collect(Collectors.toList());
        return allComb;
    }

    /**
     * 删除推广位
     * @param ids
     * @return
     */
    public int deletePubSite(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(",")).map(a -> Integer.parseInt(a)).collect(Collectors.toList());

        //构建
        V2TaobaoPubsiteCombinationInfoExample pubsiteCombinationExample = new V2TaobaoPubsiteCombinationInfoExample();
        V2TaobaoPubsiteCombinationInfoExample.Criteria criteria = pubsiteCombinationExample.createCriteria();
        criteria.andIdIn(idList);

        //设置
        V2TaobaoPubsiteCombinationInfo v2TaobaoPubsiteCombinationInfo = new V2TaobaoPubsiteCombinationInfo();
        v2TaobaoPubsiteCombinationInfo.setStatus((byte) 1);

        //更新
        int affectedCnt = v2TaobaoPubsiteCombinationInfoDao.updateByExampleSelective(v2TaobaoPubsiteCombinationInfo, pubsiteCombinationExample);
        return affectedCnt;
    }
}
