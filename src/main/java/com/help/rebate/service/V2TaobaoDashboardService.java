package com.help.rebate.service;


import com.alibaba.fastjson.JSON;
import com.help.rebate.dao.V2TaobaoOrderDetailInfoDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.model.WideOrderDetailListDTO;
import com.help.rebate.model.WideOrderDetailListVO;
import com.help.rebate.service.schedule.FixedOrderSyncTask;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoDashboardService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoDashboardService.class);

    /**
     * 订单详情接口
     */
    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    /**
     * 订单详情接口
     */
    @Resource
    private V2TaobaoOrderDetailInfoDao v2TaobaoOrderDetailInfoDao;

    @Resource
    private V2TaobaoOrderBindService v2TaobaoOrderBindService;

    @Resource
    private V2TaobaoOrderOpenidMapService v2TaobaoOrderOpenidMapService;

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

        //添加其他查询条件
        addOtherQueryCondition(wideOrderDetailListDTO, criteria);

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderDetailInfoDao.selectByExample(example);

        //根据结果，查询绑定信息
        List<String> tradeIds = orderDetails.stream().map(a -> a.getTradeId()).collect(Collectors.toList());
        List<V2TaobaoOrderOpenidMapInfo> v2TaobaoOrderOpenidMapInfos = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeId(tradeIds);
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
        Byte orderStatus = wideOrderDetailListDTO.getOrderStatus();

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
}
