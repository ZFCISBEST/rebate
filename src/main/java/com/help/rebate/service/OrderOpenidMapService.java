package com.help.rebate.service;

import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.entity.OrderOpenidMap;
import com.help.rebate.dao.entity.OrderOpenidMapExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import com.help.rebate.vo.CommissionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单映射数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class OrderOpenidMapService {
    private static final Logger logger = LoggerFactory.getLogger(OrderOpenidMapService.class);

    /**
     * 订单映射详情接口
     */
    @Resource
    private OrderOpenidMapDao orderOpenidMapDao;



    /**
     * 列出符合条件的所有绑定的订单
     * @return
     */
    public List<OrderOpenidMap> listAll(String parentTradeId, String tradeId, String specialId, String relationId, int pageNo, int pageSize) {
        //查出所有没有被删除的
        OrderOpenidMapExample orderOpenidMapExample = new OrderOpenidMapExample();
        OrderOpenidMapExample.Criteria criteria = orderOpenidMapExample.createCriteria();
        if (!EmptyUtils.isEmpty(parentTradeId)) {
            criteria.andParentTradeIdEqualTo(parentTradeId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }
        if (!EmptyUtils.isEmpty(relationId)) {
            criteria.andRelationIdEqualTo(relationId);
        }

        orderOpenidMapExample.setOffset((long) (pageNo - 1) * pageSize);
        orderOpenidMapExample.setLimit(pageSize);

        //查询
        List<OrderOpenidMap> orderOpenidMaps = orderOpenidMapDao.selectByExample(orderOpenidMapExample);
        return orderOpenidMaps;
    }

    /**
     * 通过交易单号、openId和specialId一起查询，是否已经绑定
     * @param tradeParentId
     * @param openId
     * @param specialId
     * @return
     */
    public List<OrderOpenidMap> selectBy(String tradeParentId, String openId, String specialId) {
        OrderOpenidMapExample orderOpenidMapExample = new OrderOpenidMapExample();
        orderOpenidMapExample.setLimit(50);
        OrderOpenidMapExample.Criteria criteria = orderOpenidMapExample.createCriteria();

        if (!EmptyUtils.isEmpty(tradeParentId)) {
            criteria.andParentTradeIdEqualTo(tradeParentId);
        }
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        //查询
        List<OrderOpenidMap> orderDetails = orderOpenidMapDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     *
     * 通过交易单号查询
     * @param parentTradeId
     * @param tradeId
     * @return
     */
    public List<OrderOpenidMap> selectByTradeId(String parentTradeId, String tradeId) {
        OrderOpenidMapExample orderOpenidMapExample = new OrderOpenidMapExample();
        orderOpenidMapExample.setLimit(50);
        OrderOpenidMapExample.Criteria criteria = orderOpenidMapExample.createCriteria();

        if (!EmptyUtils.isEmpty(parentTradeId)) {
            criteria.andParentTradeIdEqualTo(parentTradeId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
            orderOpenidMapExample.setLimit(1);
        }

        //查询
        List<OrderOpenidMap> orderDetails = orderOpenidMapDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     * 保存一个新对象
     * @param orderOpenidMap
     * @return
     */
    public int save(OrderOpenidMap orderOpenidMap) {
        int affectedCnt = orderOpenidMapDao.insertSelective(orderOpenidMap);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param orderOpenidMap
     * @return
     */
    public int update(OrderOpenidMap orderOpenidMap) {
        int affectedCnt = orderOpenidMapDao.updateByPrimaryKeySelective(orderOpenidMap);
        return affectedCnt;
    }

    /**
     * 查询返利信息
     * 显然，这里需要优化，不然一次查出来太多了
     * @param openId
     * @param specialId
     * @param orderStatuss 订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功
     * @param commissionStatus 给用户的结算状态 - 待提取、提取中，提取成功，提取失败
     * @return
     */
    public CommissionVO selectCommissionBy(String openId, String specialId, String orderStatuss, String[] commissionStatus) {
        // TODO: 2022/3/28 优化查询方式
        OrderOpenidMapExample orderOpenidMapExample = new OrderOpenidMapExample();
        OrderOpenidMapExample.Criteria criteria = orderOpenidMapExample.createCriteria();

        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        //多个状态解析
        String[] orderStatusStrs = orderStatuss.split(",");
        List<Integer> orderStatusList = Arrays.stream(orderStatusStrs).map(a -> Integer.parseInt(a)).collect(Collectors.toList());
        criteria.andOrderStatusIn(orderStatusList);
        criteria.andCommissionStatusIn(Arrays.stream(commissionStatus).collect(Collectors.toList()));

        //查询
        CommissionVO commissionVO = new CommissionVO();
        List<OrderOpenidMap> orderDetails = orderOpenidMapDao.selectByExample(orderOpenidMapExample);
        if (EmptyUtils.isEmpty(orderDetails)) {
            commissionVO.setCommission("0.0");
            commissionVO.setLabel("无记录");
            return commissionVO;
        }

        //循环计算
        BigDecimal allFee = new BigDecimal(0.0);
        BigDecimal allCommission = new BigDecimal(0.0);
        Map<String, List<String>> tradeParentId2ItemIdsMap = commissionVO.getTradeParentId2ItemIdsMap();
        for (OrderOpenidMap orderDetail : orderDetails) {
            //先存储详情
            String parentTradeId = orderDetail.getParentTradeId();
            String itemId = orderDetail.getItemId();
            List<String> itemIds = tradeParentId2ItemIdsMap.getOrDefault(parentTradeId, new ArrayList<String>());
            itemIds.add(itemId);
            tradeParentId2ItemIdsMap.put(parentTradeId, itemIds);

            //默认呢，就是预返利，哪怕是关闭
            String fee = "0.0";
            //付款
            Integer orderStatus = orderDetail.getOrderStatus();
            if (orderStatus == 12 || orderStatus == 14){
                fee = orderDetail.getPubSharePreFee();
            }
            else if(orderStatus == 3) {
                fee = orderDetail.getPubShareFee();
            }

            //alimama
            if (!"0.0".equals(fee)) {
                String alimamaFee = orderDetail.getAlimamaShareFee();
                allFee.add(new BigDecimal(fee).subtract(new BigDecimal(alimamaFee)));
            }

            //关于给用户返利
            String commission = "0.0";
            if (commissionStatus.equals("提取中") || commissionStatus.equals("提取成功")) {
                commission = orderDetail.getActualCommissionFee();
            }
            allCommission.add(new BigDecimal(commission));
        }
        commissionVO.setLabel("商家预返利");
        commissionVO.setPubFee(allFee.toString());
        commissionVO.setCommission(NumberUtil.format(allCommission));

        return commissionVO;
    }

    /**
     * 更改绑定的订单状态
     * @param openId
     * @param specialId
     * @param tradeParentIds
     * @param pickMoneyRecordId
     * @param commissionStatus
     * @return
     */
    public int changeCommissionStatusByTradeParentIds(String openId, String specialId, Map<String, List<String>> tradeParentIds, Integer pickMoneyRecordId, String commissionStatus) {
        //前置校验
        Checks.isIn(commissionStatus, new String[]{"提取中"}, "状态错误");
        Checks.isTrue(pickMoneyRecordId != null, "当前提取记录ID不能为空");

        //直接更新，也不查询了
        //OrderOpenidMap orderOpenidMap = new OrderOpenidMap();
        //orderOpenidMap.setGmtModified(new Date());
        //orderOpenidMap.setCommissionStatus(commissionStatus);
        //orderOpenidMap.setCurrentPickRecordId(pickMoneyRecordId);

        //条件
        OrderOpenidMapExample example = new OrderOpenidMapExample();
        OrderOpenidMapExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(0);
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }
        if (!EmptyUtils.isEmpty(tradeParentIds.keySet())) {
            criteria.andParentTradeIdIn(tradeParentIds.keySet().stream().collect(Collectors.toList()));
        }
        List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapDao.selectByExample(example);
        for (OrderOpenidMap openidMap : orderOpenidMapList) {
            openidMap.setGmtModified(new Date());
            openidMap.setCommissionStatus(commissionStatus);
            openidMap.setCurrentPickRecordId(pickMoneyRecordId);

            //实际返利的钱
            //默认呢，就是预返利，哪怕是关闭
            String fee = "0.0";
            //付款
            Integer orderStatus = openidMap.getOrderStatus();
            if (orderStatus == 12 || orderStatus == 14){
                fee = openidMap.getPubSharePreFee();
            }
            else if(orderStatus == 3) {
                fee = openidMap.getPubShareFee();
            }

            //alimama
            String alimamaFee = "0.0";
            if (!"0.0".equals(fee)) {
                alimamaFee = openidMap.getAlimamaShareFee();
            }

            //实际要更新的值
            BigDecimal actualCommission = new BigDecimal(fee).subtract(new BigDecimal(alimamaFee));
            openidMap.setActualCommissionFee(NumberUtil.format(actualCommission));

            //更新 - 后面再写一个批量更新
            int affected = orderOpenidMapDao.updateByPrimaryKeySelective(openidMap);
            Checks.isTrue(affected == 1, "更新失败");
        }

        //返回
        return orderOpenidMapList.size();

        //int affectedCnt = orderOpenidMapDao.updateByExampleSelective(orderOpenidMap, example);
        //return affectedCnt;
    }

    /**
     * 更改绑定订单的状态
     * @param openId
     * @param specialId
     * @param pickMoneyRecordId
     * @param commissionStatus
     */
    public int changeCommissionStatusByPickMoneyId(String openId, String specialId, Integer pickMoneyRecordId, String commissionStatus) {
        //前置校验
        Checks.isIn(commissionStatus, new String[]{"提取超时", "提取失败", "提取成功"}, "状态错误");
        Checks.isTrue(pickMoneyRecordId != null, "当前提取记录ID不能为空");

        //直接更新，也不查询了
        OrderOpenidMap orderOpenidMap = new OrderOpenidMap();
        orderOpenidMap.setGmtModified(new Date());
        orderOpenidMap.setCommissionStatus(commissionStatus);

        //条件
        OrderOpenidMapExample example = new OrderOpenidMapExample();
        OrderOpenidMapExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(0);
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }
        criteria.andCurrentPickRecordIdEqualTo(pickMoneyRecordId);

        int affectedCnt = orderOpenidMapDao.updateByExampleSelective(orderOpenidMap, example);
        return affectedCnt;
    }
}
