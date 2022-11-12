package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoTklConvertHistoryInfoDao;
import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfo;
import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfoExample;
import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.service.dtk.tb.DtkReturnPriceService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.MD5Utils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 口令转换信息服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoTklConvertService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoTklConvertService.class);

    /**
     * 用户操作接口
     */
    @Resource
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    /**
     * 推广位，组合表
     */
    @Resource
    private V2TaobaoPubSiteService v2TaobaoPubSiteService;

    /**
     * 淘口令转链历史记录表
     */
    @Resource
    private V2TaobaoTklConvertHistoryInfoDao v2TaobaoTklConvertHistoryInfoDao;

    /**
     * 返利比例服务表
     */
    @Resource
    private V2TaobaoCommissionRatioInfoService v2TaobaoCommissionRatioInfoService;

    /**
     * 实际的转链服务
     */
    @Resource
    private DtkReturnPriceService dtkReturnPriceService;

    /**
     * 转链服务
     * @param tkl 原始淘口令
     * @param openId 外部ID
     * @param pubSiteType 口令类型 - 渠道、会员、虚拟、无
     * @return
     */
    public String convert(String tkl, String openId, String pubSiteType) {
        //查询openId的用户信息，如果不存在，那么重新插入
        V2TaobaoUserInfo v2TaobaoUserInfo = v2TaobaoUserInfoService.selectByOpenId(openId);
        if (v2TaobaoUserInfo == null) {
            v2TaobaoUserInfo = v2TaobaoUserInfoService.insertOrDoNone(openId, null, null, null);
        }
        openId = v2TaobaoUserInfo.getOpenId();
        String specialId = v2TaobaoUserInfo.getSpecialId();
        String relationId = v2TaobaoUserInfo.getRelationId();

        //1、首先，获取默认的返利比例
        double commissionRatio = v2TaobaoCommissionRatioInfoService.selectCommissionRatio(openId);

        //2、vip|site
        List<String> allPubSiteCombinations = v2TaobaoPubSiteService.getAllPubSiteCombinations(pubSiteType);

        //2.1.1、选择一个合适的 - 首先按照hash的方式选择一个
        String md5OpenId = MD5Utils.getMd5Hex(openId.getBytes());
        int hashCode = md5OpenId.hashCode();
        int index = Math.abs(hashCode) % allPubSiteCombinations.size();
        String pubSiteCombination = allPubSiteCombinations.get(index);

        //2.1.2、hash方式，选中后，开始转链
        DtkReturnPriceService.TklDO newTkl = convertTkl(tkl, specialId, relationId, pubSiteCombination, commissionRatio);

        //2.1.3、获取商品ID - 查询转链记录表，看看该用户下是否已经转换过该商品，并使用了某个推广位
        String itemId = newTkl.getItemId();
        int limit = 1;
        List<V2TaobaoTklConvertHistoryInfo> tklConvertHistories = getTklConvertHistories(pubSiteType, itemId, openId, null, limit);

        //2.1.3.1、说明该用户以前转过，直接复用
        if (!EmptyUtils.isEmpty(tklConvertHistories)) {
            V2TaobaoTklConvertHistoryInfo tklConvertHistory = tklConvertHistories.get(0);
            String newPubsiteCombination = tklConvertHistory.getPubsiteCombination();

            //重新转
            newTkl = convertTkl(tkl,specialId, relationId, newPubsiteCombination, commissionRatio);

            //存储淘口令
            storeConvertRecord(tkl, openId, openId, pubSiteType, newTkl, newPubsiteCombination);
            logger.info("[TklConvertService] bind openId[{}] and pubSite[{}] on item[{}], has used by self, use old", openId, newPubsiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.1.3.2、说明该用户以前没有转过，那么查出来所有的
        tklConvertHistories = getTklConvertHistories(pubSiteType, itemId, null, null, -1);

        //2.2、情况1 - 不存在
        if (EmptyUtils.isEmpty(tklConvertHistories)) {
            storeConvertRecord(tkl, openId, null, pubSiteType, newTkl, pubSiteCombination);
            logger.info("[TklConvertService] bind specialId[{}] and pubSite[{}] on item[{}], not used - 1, itemId not exist", specialId, pubSiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.3、情况2 - 存在，但是当前的推广位没有被使用过
        List<String> hasCostPubSiteCombination = tklConvertHistories.stream().map(t -> t.getPubsiteCombination()).collect(Collectors.toList());
        if (!hasCostPubSiteCombination.contains(pubSiteCombination)) {
            storeConvertRecord(tkl, openId, specialId, pubSiteType, newTkl, pubSiteCombination);
            logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], not used - 2, itemId exist", specialId, pubSiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.4、情况3 - 存在，当前的推广位已经被使用过，但是还没有用完
        Optional<String> firstNotCostPubSiteComb = allPubSiteCombinations.stream().filter(t -> !hasCostPubSiteCombination.contains(t)).findFirst();
        if (firstNotCostPubSiteComb.isPresent()) {
            String newNotCostPubSiteComb = firstNotCostPubSiteComb.get();

            //重新转链接
            newTkl = convertTkl(tkl, specialId, relationId, newNotCostPubSiteComb, commissionRatio);
            storeConvertRecord(tkl, openId, specialId, pubSiteType, newTkl, newNotCostPubSiteComb);
            logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], not used - 3, select new one", specialId, newNotCostPubSiteComb, newTkl.getItemId());
            return newTkl.getTkl();

        }

        //2.5、都用完了，直接返回
        storeConvertRecord(tkl, openId, specialId, pubSiteType, newTkl, pubSiteCombination);
        logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], no remaining, reuse pubSite", specialId, pubSiteCombination, newTkl.getItemId());
        return newTkl.getTkl();
    }

    /**
     * 通过渠道方式转链接
     * @param tkl
     * @param specialId
     * @param relationId
     * @param pubSiteCombination
     * @param commissionRatio
     * @return
     */
    private DtkReturnPriceService.TklDO convertTkl(String tkl, String specialId, String relationId, String pubSiteCombination, double commissionRatio) {
        String[] vipIdAndPubSite = pubSiteCombination.split("\\|");
        String vipId = null;
        String pubSite = vipIdAndPubSite[1];
        if (!"virtualId".equals(vipIdAndPubSite[0])) {
            vipId = vipIdAndPubSite[0];
        }
        if (relationId != null) {
            vipId = relationId;
        }

        return dtkReturnPriceService.generateReturnPriceInfo(tkl, vipId, specialId, null, pubSite, commissionRatio);
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
    private List<V2TaobaoTklConvertHistoryInfo> getTklConvertHistories(String pubSiteType, String itemId, String openId, String pubSiteCombination, int limit) {
        V2TaobaoTklConvertHistoryInfoExample historyExample = new V2TaobaoTklConvertHistoryInfoExample();
        if (limit > 0) {
            historyExample.setLimit(limit);
        }

        V2TaobaoTklConvertHistoryInfoExample.Criteria criteria = historyExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
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
    private void storeConvertRecord(String tkl, String openId, String specialId, String pubSiteType,
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
}
