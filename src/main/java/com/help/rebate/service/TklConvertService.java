package com.help.rebate.service;

import com.help.rebate.dao.PubsiteCombinationDao;
import com.help.rebate.dao.TklConvertHistoryDao;
import com.help.rebate.dao.UserInfosDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.service.ddx.tb.DdxReturnPriceService;
import com.help.rebate.service.dtk.tb.DtkReturnPriceService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.MD5Utils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
public class TklConvertService {
    private static final Logger logger = LoggerFactory.getLogger(TklConvertService.class);

    /**
     * 用户操作接口
     */
    @Resource
    private UserInfosDao userInfosDao;

    /**
     * 淘口令转链历史记录表
     */
    @Resource
    private TklConvertHistoryDao tklConvertHistoryDao;

    /**
     * 推广位，组合表
     */
    @Resource
    private PubsiteCombinationDao pubsiteCombinationDao;

    /**
     * 淘口令转链服务
     */
    @Resource
    private DdxReturnPriceService ddxReturnPriceService;

    @Resource
    private DtkReturnPriceService dtkReturnPriceService;

    /**
     * 转链服务
     * @param tkl 原始淘口令
     * @param openId 外部ID
     * @param externalId 外部ID
     * @param tklType 口令类型 - 渠道、会员、虚拟、无
     * @param dataFrom 来源 tb、jd、pdd、wph（唯品会）、mt（美团）、elem（饿了么）
     * @return
     */
    public String convert(String tkl, String openId, String externalId, String tklType, String dataFrom) {
        // TODO: 21/11/15 接入缓存系统，这里暂略

        //查询openId的用户信息，如果不存在，那么重新插入
        UserInfos userInfo = getOrCreateUserInfos(openId, externalId, dataFrom);
        openId = userInfo.getOpenId();
        externalId = userInfo.getExternalId();
        Checks.isNotEmpty(externalId, "openId必须依附externalId存在，当前不存在externalId，请管理员维护映射关系");

        //1、优先检查，是否是会员
        if ("special".equals(tklType)) {
            DtkReturnPriceService.TklDO newTkl = null;
            if (!EmptyUtils.isEmpty(userInfo.getSpecialId())) {
                //使用会员的specialId
                newTkl = dtkReturnPriceService.generateReturnPriceInfo(tkl, null, userInfo.getSpecialId(), null, 0.88);
            }
            else {
                //使用引导添加会员的externalId，默认与openId是一样的
                newTkl = dtkReturnPriceService.generateReturnPriceInfo(tkl, null, null, userInfo.getExternalId(), 0.88);
            }

            //存储到转链记录表
            storeConvertRecord(tkl, openId, externalId, tklType, newTkl, null);
            return newTkl.getTkl();
        }

        //2、否则 - 走渠道方式，剩下三种，都默认作为渠道类型处理
        List<String> allPubSiteCombinations = getAllPubSiteCombinations(tklType);

        //2.1.1、选择一个合适的 - 首先按照hash的方式选择一个
        String md5ExternalId = MD5Utils.getMd5Hex(externalId.getBytes());
        int hashCode = md5ExternalId.hashCode();
        int index = Math.abs(hashCode) % allPubSiteCombinations.size();
        String pubSiteCombination = allPubSiteCombinations.get(index);

        //2.1.2、hash方式，选中后，开始转链
        DtkReturnPriceService.TklDO newTkl = convertTklByRelationId(tkl, pubSiteCombination);

        //2.1.3、获取商品ID - 查询转链记录表，看看该用户下是否已经转换过该商品，并使用了某个推广位
        String itemId = newTkl.getItemId();
        int limit = 1;
        List<TklConvertHistory> tklConvertHistories = getTklConvertHistories(tklType, itemId, externalId, null, limit);

        //2.1.3.1、说明该用户以前转过，直接复用
        if (!EmptyUtils.isEmpty(tklConvertHistories)) {
            TklConvertHistory tklConvertHistory = tklConvertHistories.get(0);
            String newPubsiteCombination = tklConvertHistory.getPubsiteCombination();

            //说明是相同的，那么因为刚刚转过了，直接返回吧
            if (!newPubsiteCombination.equals(pubSiteCombination)) {
                //重新转
                newTkl = convertTklByRelationId(tkl, newPubsiteCombination);
            }

            //存储淘口令
            storeConvertRecord(tkl, openId, externalId, tklType, newTkl, newPubsiteCombination);
            logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], has used by self, use old", externalId, newPubsiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.1.3.2、说明该用户以前没有转过，那么查出来所有的
        tklConvertHistories = getTklConvertHistories(tklType, itemId, null, null, -1);

        //2.2、情况1 - 不存在
        if (EmptyUtils.isEmpty(tklConvertHistories)) {
            storeConvertRecord(tkl, openId, externalId, tklType, newTkl, pubSiteCombination);
            logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], not used - 1, itemId not exist", externalId, pubSiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.3、情况2 - 存在，但是当前的推广位没有被使用过
        List<String> hasCostPubSiteCombination = tklConvertHistories.stream().map(t -> t.getPubsiteCombination()).collect(Collectors.toList());
        if (!hasCostPubSiteCombination.contains(pubSiteCombination)) {
            storeConvertRecord(tkl, openId, externalId, tklType, newTkl, pubSiteCombination);
            logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], not used - 2, itemId exist", externalId, pubSiteCombination, newTkl.getItemId());
            return newTkl.getTkl();
        }

        //2.4、情况3 - 存在，当前的推广位已经被使用过，但是还没有用完
        Optional<String> firstNotCostPubSiteComb = allPubSiteCombinations.stream().filter(t -> !hasCostPubSiteCombination.contains(t)).findFirst();
        if (firstNotCostPubSiteComb.isPresent()) {
            String newNotCostPubSiteComb = firstNotCostPubSiteComb.get();

            //重新转链接
            newTkl = convertTklByRelationId(tkl, newNotCostPubSiteComb);
            storeConvertRecord(tkl, openId, externalId, tklType, newTkl, newNotCostPubSiteComb);
            logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], not used - 3, select new one", externalId, newNotCostPubSiteComb, newTkl.getItemId());
            return newTkl.getTkl();

        }

        //2.5、都用完了，直接返回
        storeConvertRecord(tkl, openId, externalId, tklType, newTkl, pubSiteCombination);
        logger.info("[TklConvertService] bind externalId[{}] and pubSite[{}] on item[{}], no remaining, reuse pubSite", externalId, pubSiteCombination, newTkl.getItemId());
        return newTkl.getTkl();
    }

    /**
     * 通过渠道方式转链接
     * @param tkl
     * @param pubSiteCombination
     * @return
     */
    private DtkReturnPriceService.TklDO convertTklByRelationId(String tkl, String pubSiteCombination) {
        String[] vipIdAndPubSite = pubSiteCombination.split("\\|");
        String vipId = null;
        String pubSite = vipIdAndPubSite[1];
        if (!"virtualId".equals(vipIdAndPubSite[0]) && !"noneId".equals(vipIdAndPubSite[0])) {
            vipId = vipIdAndPubSite[0];
        }

        return dtkReturnPriceService.generateReturnPriceInfo(tkl, vipId, null, null, pubSite, 0.88);
    }

    /**
     * 获取转链历史，通过商品ID，和转链类型
     * @param tklType
     * @param itemId
     * @param externalId
     * @param pubSiteCombination
     * @param limit
     * @return
     */
    private List<TklConvertHistory> getTklConvertHistories(String tklType, String itemId, String externalId, String pubSiteCombination, int limit) {
        TklConvertHistoryExample historyExample = new TklConvertHistoryExample();
        if (limit > 0) {
            historyExample.setLimit(limit);
        }

        TklConvertHistoryExample.Criteria criteria = historyExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        criteria.andTkltypeEqualTo(tklType);

        //这种表示，只查个人的
        if (externalId != null) {
            criteria.andExternalIdEqualTo(externalId);
        }

        //这种表示，只查某个特定推广位的
        if (pubSiteCombination != null) {
            criteria.andPubsiteCombinationEqualTo(pubSiteCombination);
        }

        //我们只对7天内转链的商品负责 - 7 * 24 * 3600 * 1000
        criteria.andGmtCreatedGreaterThanOrEqualTo(new Date(System.currentTimeMillis() - 604800000));

        criteria.andStatusEqualTo(0);
        List<TklConvertHistory> tklConvertHistories = tklConvertHistoryDao.selectByExample(historyExample);
        return tklConvertHistories;
    }

    /**
     * 获取所有相关类型的推广位组合
     * @param tklType
     * @return
     */
    private List<String> getAllPubSiteCombinations(String tklType) {
        // TODO: 21/11/15 这里应该有一个缓存的，后面再一起加上 a

        PubsiteCombinationExample example = new PubsiteCombinationExample();
        PubsiteCombinationExample.Criteria criteria = example.createCriteria();
        criteria.andTklTypeEqualTo(tklType);
        criteria.andStatusEqualTo(0);

        List<PubsiteCombination> pubsiteCombinations = pubsiteCombinationDao.selectByExample(example);

        //不能为空
        Checks.isNotEmpty(pubsiteCombinations, "没有任何可用的推广位组合");

        List<String> allComb = pubsiteCombinations.stream().map(a -> a.getVipId() + "|" + a.getPubSite()).collect(Collectors.toList());
        return allComb;
    }

    /**
     *
     * @param openId
     * @param externalId
     * @param dataFrom
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserInfos getOrCreateUserInfos(String openId, String externalId, String dataFrom) {
        //两个不能同时为空
        Checks.isTrue(!EmptyUtils.isEmpty(openId) || !EmptyUtils.isEmpty(externalId), "openId和externalId不能同时为空");

        //查询用户是否存在
        UserInfosExample userInfosExample = new UserInfosExample();
        userInfosExample.setLimit(1);
        UserInfosExample.Criteria criteria = userInfosExample.createCriteria();
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            criteria.andExternalIdEqualTo(externalId);
        }
        criteria.andDataFromEqualTo(dataFrom);
        criteria.andStatusEqualTo(0);

        List<UserInfos> userInfos = userInfosDao.selectByExample(userInfosExample);
        if (!EmptyUtils.isEmpty(userInfos)) {
            return userInfos.get(0);
        }

        //不存在，执行插入操作
        UserInfos infos = new UserInfos();
        infos.setGmtCreated(new Date());
        infos.setGmtModified(new Date());
        infos.setOpenId(openId);
        infos.setOpenName(null);
        infos.setExternalId(EmptyUtils.isEmpty(externalId) ? openId : externalId);
        infos.setSpecialId(null);
        infos.setRelationId(null);
        infos.setDataFrom(dataFrom);
        infos.setStatus(0);
        int affectedCnt = userInfosDao.insertSelective(infos);
        Checks.isTrue(affectedCnt > 0, "添加为新用户失败");
        return infos;
    }

    /**
     * 存储转链记录
     *
     * @param tkl
     * @param openId
     * @param externalId
     * @param tklType
     * @param newTkl
     * @param pubSiteCombination
     */
    private void storeConvertRecord(String tkl, String openId, String externalId, String tklType,
                                    DtkReturnPriceService.TklDO newTkl, String pubSiteCombination) {
        List<TklConvertHistory> tklConvertHistories = getTklConvertHistories(tklType, newTkl.getItemId(), externalId, pubSiteCombination, 1);
        if (EmptyUtils.isEmpty(tklConvertHistories)) {
            TklConvertHistory tklConvertHistory = new TklConvertHistory();
            tklConvertHistory.setGmtCreated(new Date());
            tklConvertHistory.setGmtModified(new Date());
            tklConvertHistory.setOpenId(openId);
            tklConvertHistory.setExternalId(externalId);

            //会员转码，该字段传入的是null，因为不需要
            tklConvertHistory.setPubsiteCombination(pubSiteCombination);
            tklConvertHistory.setTkl(Base64.encodeBase64String(tkl.getBytes()));
            tklConvertHistory.setNewTkl(Base64.encodeBase64String(newTkl.getTkl().getBytes()));
            tklConvertHistory.setItemId(newTkl.getItemId());
            tklConvertHistory.setTkltype(tklType);
            tklConvertHistory.setAttachInfo(null);
            tklConvertHistory.setExpired(0);
            tklConvertHistory.setStatus(0);

            int affectedCnt = tklConvertHistoryDao.insertSelective(tklConvertHistory);
            Checks.isTrue(affectedCnt == 1, "存储新生成的淘口令失败");
            return;
        }

        //执行更新
        TklConvertHistory tklConvertHistory = tklConvertHistories.get(0);
        tklConvertHistory.setGmtModified(new Date());
        tklConvertHistory.setTkl(Base64.encodeBase64String(tkl.getBytes()));
        tklConvertHistory.setNewTkl(Base64.encodeBase64String(newTkl.getTkl().getBytes()));
        int affectedCnt = tklConvertHistoryDao.updateByPrimaryKeySelective(tklConvertHistory);
        Checks.isTrue(affectedCnt == 1, "更新新生成的淘口令失败");
    }
}
