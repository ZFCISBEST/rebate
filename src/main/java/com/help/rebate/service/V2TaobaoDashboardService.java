package com.help.rebate.service;


import com.alibaba.fastjson.JSON;
import com.help.rebate.dao.V2TaobaoOrderDetailInfoDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.model.DashboardVO;
import com.help.rebate.model.WideOrderDetailListDTO;
import com.help.rebate.model.WideOrderDetailListVO;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 订单数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
@Transactional
public class V2TaobaoDashboardService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoDashboardService.class);

    @Resource
    private V2TaobaoOrderBindService v2TaobaoOrderBindService;

    /**
     * 订单详情接口
     */
    @Resource
    private V2TaobaoOrderDetailInfoDao v2TaobaoOrderDetailInfoDao;

    /**
     * 订单详情接口
     */
    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    @Resource
    private V2TaobaoOrderOpenidMapService v2TaobaoOrderOpenidMapService;

    @Resource
    private V2TaobaoTklConvertHistoryService v2TaobaoTklConvertHistoryService;

    @Resource
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    @Resource
    private V2TaobaoCommissionAccountService v2TaobaoCommissionAccountService;

    @Resource
    private V2TaobaoOrderOfflineAccountDetailService v2TaobaoOrderOfflineAccountDetailService;

    /**
     *
     * @param wideOrderDetailListDTO
     * @param current
     * @param pageSize
     * @return
     */
    public List<WideOrderDetailListVO> listAll(WideOrderDetailListDTO wideOrderDetailListDTO, Integer current, Integer pageSize) {
        //查出所有的，包括删除和没删除的
        V2TaobaoOrderDetailInfoExample example = new V2TaobaoOrderDetailInfoExample();
        example.setOrderByClause("gmt_modified desc");
        example.setOffset((current - 1) * pageSize + 0L);
        example.setLimit(pageSize);
        V2TaobaoOrderDetailInfoExample.Criteria criteria = example.createCriteria();

        //如果openId不为空
        List<V2TaobaoOrderOpenidMapInfo> v2TaobaoOrderOpenidMapInfos = null;
        String openId = wideOrderDetailListDTO.getOpenId();
        if (openId != null) {
            v2TaobaoOrderOpenidMapInfos =
                    v2TaobaoOrderOpenidMapService.selectBindInfoByTradeParentIdsAndOpenId(null, openId, (current - 1) * pageSize + 0L, pageSize);

            //获取所有的tradeId
            List<String> tradeIds = v2TaobaoOrderOpenidMapInfos.stream().map(a -> a.getTradeId()).collect(Collectors.toList());
            criteria.andTradeIdIn(tradeIds);
        }

        //添加其他查询条件
        addOtherQueryCondition(wideOrderDetailListDTO, criteria);

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderDetailInfoDao.selectByExample(example);

        //根据结果，查询绑定信息
        if (v2TaobaoOrderOpenidMapInfos == null) {
            List<String> tradeIds = orderDetails.stream().map(a -> a.getTradeId()).collect(Collectors.toList());
            v2TaobaoOrderOpenidMapInfos = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeId(tradeIds);
        }
        Map<String, V2TaobaoOrderOpenidMapInfo> orderId2BindInfoMap = v2TaobaoOrderOpenidMapInfos.stream().collect(Collectors.toMap(a -> a.getTradeId(), a -> a, (a, b) -> a));

        //序列化、反序列化
        List<WideOrderDetailListVO> wideOrderDetailListVOS = JSON.parseArray(JSON.toJSONString(orderDetails), WideOrderDetailListVO.class);
        wideOrderDetailListVOS.stream().forEach(a -> {
            String tradeId = a.getTradeId();
            V2TaobaoOrderOpenidMapInfo openidMapInfo = orderId2BindInfoMap.get(tradeId);
            if (openidMapInfo != null) {
                a.setOpenId(openidMapInfo.getOpenId());
                a.setCommissionRatio(openidMapInfo.getCommissionRatio());
                a.setMapTypeMsg(openidMapInfo.getMapTypeMsg());
                a.setCommissionStatusMsg(openidMapInfo.getCommissionStatusMsg());
            }
        });

        return wideOrderDetailListVOS;
    }

    /**
     *
     * @param wideOrderDetailListDTO
     * @param current
     * @param pageSize
     * @return
     */
    public long countAll(WideOrderDetailListDTO wideOrderDetailListDTO, Integer current, Integer pageSize) {
        //如果openId不为空
        String openId = wideOrderDetailListDTO.getOpenId();
        if (openId != null) {
            return v2TaobaoOrderOpenidMapService.countBindInfoByTradeParentIdsAndOpenId(null, openId);
        }

        //查出所有的，包括删除和没删除的
        V2TaobaoOrderDetailInfoExample example = new V2TaobaoOrderDetailInfoExample();
        V2TaobaoOrderDetailInfoExample.Criteria criteria = example.createCriteria();

        //添加其他查询条件
        addOtherQueryCondition(wideOrderDetailListDTO, criteria);

        //返回结果
        long count = v2TaobaoOrderDetailInfoDao.countByExample(example);
        return count;
    }

    /**
     * 添加其他的查询条件
     * @param wideOrderDetailListDTO
     * @param criteria
     */
    private void addOtherQueryCondition(WideOrderDetailListDTO wideOrderDetailListDTO, V2TaobaoOrderDetailInfoExample.Criteria criteria) {
        //这个使用in
        String tradeParentId = wideOrderDetailListDTO.getTradeParentId();
        String modifiedTime = wideOrderDetailListDTO.getModifiedTime();
        Integer tkStatus = wideOrderDetailListDTO.getTkStatus();
        Integer refundTag = wideOrderDetailListDTO.getRefundTag();
        String specialId = wideOrderDetailListDTO.getSpecialId();
        //这个使用like
        String itemTitle = wideOrderDetailListDTO.getItemTitle();
        Byte orderStatus = wideOrderDetailListDTO.getStatus();

        //切分
        if (!EmptyUtils.isEmpty(tradeParentId)) {
            String[] split = tradeParentId.split(",| |;");
            List<String> collect = Arrays.stream(split).map(String::trim).filter(a -> !a.isEmpty()).collect(Collectors.toList());
            if (collect.size() > 0) {
                criteria.andTradeParentIdIn(collect);
            }
        }

        //时间
        if (!EmptyUtils.isEmpty(modifiedTime)) {
            criteria.andModifiedTimeGreaterThanOrEqualTo(modifiedTime);
        }

        //订单状态
        if (tkStatus != null) {
            criteria.andTkStatusEqualTo(tkStatus);
        }

        //退单标
        if (refundTag != null) {
            criteria.andRefundTagEqualTo(refundTag);
        }

        //specialId
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        //itemTitle
        if (!EmptyUtils.isEmpty(itemTitle)) {
            criteria.andItemTitleLike("%" + itemTitle + "%");
        }

        //自有字段，是否删除
        if (orderStatus != null) {
            criteria.andStatusEqualTo(orderStatus);
        }
    }

    /**
     * 查询大盘
     * @return
     * @param lastDaysOfOrder
     */
    public DashboardVO queryDashboard(Integer lastDaysOfOrder) {
        Checks.isTrue(lastDaysOfOrder > 0, "天数必须大于0");

        //大盘视图
        DashboardVO dashboardVO = new DashboardVO();

        //先查总用户数
        long userCount = v2TaobaoUserInfoService.countAll();
        dashboardVO.setUserCount(userCount);

        //再查所有用户的返利
        List<V2TaobaoCommissionAccountInfo> v2TaobaoCommissionAccountInfos = v2TaobaoCommissionAccountService.listAllUserCommissions();
        BigDecimal allTotalCommission = v2TaobaoCommissionAccountInfos.stream().map(a -> a.getTotalCommission()).reduce((x, y) -> x.add(y)).orElse(new BigDecimal("0"));
        BigDecimal allRemainingCommission = v2TaobaoCommissionAccountInfos.stream().map(a -> a.getRemainCommission()).reduce((x, y) -> x.add(y)).orElse(new BigDecimal("0"));
        dashboardVO.setAllUserTotalCommission(NumberUtil.format(allTotalCommission));
        dashboardVO.setAllUserRemainingCommission(NumberUtil.format(allRemainingCommission));

        //统计所有理论上的返利
        BigDecimal allTheoreticalCommission = v2TaobaoOrderOpenidMapService.sumAllTheoreticalCommission();
        dashboardVO.setTheoreticalEarnings(NumberUtil.format(allTheoreticalCommission.subtract(allTotalCommission)));

        //最近7日转链的次数
        long convertCount = v2TaobaoTklConvertHistoryService.countConvertCountOfLastDays(lastDaysOfOrder);
        dashboardVO.setConvertTklCountOfLast7Days(convertCount);

        //最近7天的订单量
        long orderCount = v2TaobaoOrderOpenidMapService.countParentOrderCountOfLastDays(lastDaysOfOrder);
        dashboardVO.setOrderCountOfLast7Days(orderCount);
        return dashboardVO;
    }

    /**
     * 执行订单结算
     * @param orderIds
     * @param commissionMsg
     */
    public void offlineOrderAccount(String orderIds, String commissionMsg) {
        if (commissionMsg == null || commissionMsg.trim().isEmpty()) {
            commissionMsg = "无";
        }

        List<Integer> ids = Arrays.stream(orderIds.split(","))
                .map(a -> a.trim())
                .filter(a -> !a.isEmpty())
                .map(a -> Integer.parseInt(a))
                .collect(Collectors.toList());

        //查出所有的记录
        List<V2TaobaoOrderDetailInfo> v2TaobaoOrderDetailInfos = v2TaobaoOrderService.selectByIds(ids);

        //必须完全匹配才行
        Checks.isTrue(ids.size() == v2TaobaoOrderDetailInfos.size(), "存在不可结算项");

        //状态是否都是已经可以结算的了
        Optional<V2TaobaoOrderDetailInfo> detailInfoOptional = v2TaobaoOrderDetailInfos.stream().filter(a -> a.getTkStatus() != 3).findFirst();
        if (detailInfoOptional.isPresent()) {
            Checks.isTrue(!detailInfoOptional.isPresent(), "存在联盟尚未结算的订单-" + detailInfoOptional.get().getId());
        }

        //开始订正
        for (V2TaobaoOrderDetailInfo v2TaobaoOrderDetailInfo : v2TaobaoOrderDetailInfos) {
            v2TaobaoOrderDetailInfo.setStatus((byte) 1);
            int updateCnt = v2TaobaoOrderService.update(v2TaobaoOrderDetailInfo);
            Checks.isTrue(updateCnt == 1, "未知错误，标记失败-" + v2TaobaoOrderDetailInfo.getId());

            //记录下去
            int insertCnt = v2TaobaoOrderOfflineAccountDetailService.insertNewRecord(v2TaobaoOrderDetailInfo, commissionMsg);
            Checks.isTrue(insertCnt == 1, "未知错误，记录标记过程失败-" + v2TaobaoOrderDetailInfo.getId());
        }
    }
}
