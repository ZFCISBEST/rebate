package com.help.rebate.service;

import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfo;
import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.service.dtk.tb.DtkReturnPriceService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.MD5Utils;
import com.help.rebate.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 返利比例服务表
     */
    @Resource
    private V2TaobaoCommissionRatioInfoService v2TaobaoCommissionRatioInfoService;

    /**
     * 淘口令转链历史记录服务
     */
    @Resource
    private V2TaobaoTklConvertHistoryService v2TaobaoTklConvertHistoryService;

    /**
     * 实际的转链服务
     */
    @Resource
    private DtkReturnPriceService dtkReturnPriceService;

    /**
     * 转链服务
     * 转换方式就两种
     * 1、relation的，全部使用pubsite的组合，不更改
     * 2、virtual的，只是用推广位，其他special和external都为空，但是如果用户openId本身带有specialId，那么带上这个specialId
     * @param tkl 原始淘口令
     * @param openId 外部ID
     * @param pubSiteType 口令类型 - 渠道、虚拟
     * @return
     */
    public String convert(String tkl, String openId, String pubSiteType) {
        //暂时不支持渠道的
        Checks.isTrue("virtual".equals(pubSiteType), "只能为virtual");

        //链接重置
        tkl = StringUtil.filterEmoji(tkl);

        //查询openId的用户信息，如果不存在，那么重新插入
        V2TaobaoUserInfo v2TaobaoUserInfo = v2TaobaoUserInfoService.selectByOpenId(openId);
        if (v2TaobaoUserInfo == null) {
            v2TaobaoUserInfo = v2TaobaoUserInfoService.insertOrDoNone(openId, null, null, null);
        }
        openId = v2TaobaoUserInfo.getOpenId();

        //有就带上，没有就不用
        //String specialId = v2TaobaoUserInfo.getSpecialId();

        //1、首先，获取默认的返利比例
        int commissionRatio = v2TaobaoCommissionRatioInfoService.selectCommissionRatio(openId);

        //2、vip|site vip = virtualId，或者各种relationId
        List<String> allPubSiteCombinations = v2TaobaoPubSiteService.getAllPubSiteCombinations(pubSiteType);

        //2.1.1、选择一个合适的 - 首先按照hash的方式选择一个
        String md5OpenId = MD5Utils.getMd5Hex(openId.getBytes());
        int hashCode = md5OpenId.hashCode();
        int index = Math.abs(hashCode) % allPubSiteCombinations.size();
        String pubSiteCombination = allPubSiteCombinations.get(index);

        //2.1.2、hash方式，选中后，开始转链
        DtkReturnPriceService.TklDO newTkl = convertTkl(tkl, null, pubSiteCombination, commissionRatio);

        //2.1.3、获取商品ID - 查询转链记录表，看看该用户下是否已经转换过该商品，并使用了某个推广位
        String itemId = newTkl.getItemId();
        int limit = 1;
        List<V2TaobaoTklConvertHistoryInfo> tklConvertHistories = v2TaobaoTklConvertHistoryService.getTklConvertHistories(pubSiteType, itemId, openId, null, limit);

        //2.1.3.1、说明该用户以前转过，直接复用相同的推广位组合
        if (!EmptyUtils.isEmpty(tklConvertHistories)) {
            V2TaobaoTklConvertHistoryInfo tklConvertHistory = tklConvertHistories.get(0);
            String oldPubSiteCombination = tklConvertHistory.getPubsiteCombination();

            //重新转
            newTkl = convertTkl(tkl, null, oldPubSiteCombination, commissionRatio);

            //存储淘口令
            v2TaobaoTklConvertHistoryService.storeConvertRecord(tkl, openId, null, pubSiteType, newTkl, oldPubSiteCombination);
            logger.info("[TklConvertService] bind openId[{}] and pubSite[{}] on item[{}], use old pubSite", openId, oldPubSiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.2、说明该用户以前没有转过，那么查出来所有的
        tklConvertHistories = v2TaobaoTklConvertHistoryService.getTklConvertHistories(pubSiteType, itemId, null, null, -1);

        //2.2、情况1 - 不存在
        if (EmptyUtils.isEmpty(tklConvertHistories)) {
            v2TaobaoTklConvertHistoryService.storeConvertRecord(tkl, openId, null, pubSiteType, newTkl, pubSiteCombination);
            logger.info("[TklConvertService] bind openId[{}] and pubSite[{}] on item[{}], not used - 1, itemId not exist", openId, pubSiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.3、情况2 - 存在，但是当前的推广位没有被使用过
        List<String> hasCostPubSiteCombination = tklConvertHistories.stream().map(t -> t.getPubsiteCombination()).collect(Collectors.toList());
        if (!hasCostPubSiteCombination.contains(pubSiteCombination)) {
            v2TaobaoTklConvertHistoryService.storeConvertRecord(tkl, openId, null, pubSiteType, newTkl, pubSiteCombination);
            logger.info("[TklConvertService] bind openId[{}] and pubSite[{}] on item[{}], not used - 2, itemId exist", openId, pubSiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.4、情况3 - 存在，当前的推广位已经被使用过，但是还没有用完
        Optional<String> firstNotCostPubSiteComb = allPubSiteCombinations.stream().filter(t -> !hasCostPubSiteCombination.contains(t)).findFirst();
        if (firstNotCostPubSiteComb.isPresent()) {
            String newNotCostPubSiteComb = firstNotCostPubSiteComb.get();

            //重新转链接
            newTkl = convertTkl(tkl, null, newNotCostPubSiteComb, commissionRatio);
            v2TaobaoTklConvertHistoryService.storeConvertRecord(tkl, openId, null, pubSiteType, newTkl, newNotCostPubSiteComb);
            logger.info("[TklConvertService] bind openId[{}] and pubSite[{}] on item[{}], not used - 3, select new one", openId, newNotCostPubSiteComb, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.5、都用完了，直接返回
        v2TaobaoTklConvertHistoryService.storeConvertRecord(tkl, openId, null, pubSiteType, newTkl, pubSiteCombination);
        logger.info("[TklConvertService] bind openId[{}] and pubSite[{}] on item[{}], no remaining, reuse pubSite", openId, pubSiteCombination, newTkl.getItemId());
        return newTkl.getTkl();
    }

    /**
     * 通过渠道方式转链接
     * @param tkl
     * @param specialId
     * @param pubSiteCombination
     * @param commissionRatio 千分位返利比例
     * @return
     */
    private DtkReturnPriceService.TklDO convertTkl(String tkl, String specialId, String pubSiteCombination, int commissionRatio) {
        String[] vipIdAndPubSite = pubSiteCombination.split("\\|");
        String vipId = null;
        String pubSite = vipIdAndPubSite[1];

        //优先，按照specialId来
        if (!EmptyUtils.isEmpty(specialId)) {
            return dtkReturnPriceService.generateReturnPriceInfo(tkl, null, specialId, null, pubSite, commissionRatio);
        }

        //再按照 relationId来
        if (!"virtualId".equals(vipIdAndPubSite[0])) {
            vipId = vipIdAndPubSite[0];
        }

        return dtkReturnPriceService.generateReturnPriceInfo(tkl, vipId, null, null, pubSite, commissionRatio);
    }
}
