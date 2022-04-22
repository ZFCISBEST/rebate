package com.help.rebate.service;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.OrderOpenidMapFailureDao;
import com.help.rebate.dao.TklConvertHistoryDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.service.schedule.FixedOrderBindSyncTask;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.vo.CommissionVO;
import com.help.rebate.vo.OrderBindResultVO;
import com.help.rebate.vo.PickCommissionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单绑定服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
@Transactional
public class OrderOpenidMapFailureService {
    private static final Logger logger = LoggerFactory.getLogger(OrderOpenidMapFailureService.class);

    /**
     * 订单映射表
     */
    @Resource
    private OrderOpenidMapDao orderOpenidMapDao;
    @Resource
    private OrderOpenidMapService orderOpenidMapService;

    /**
     * 订单失败记录表
     */
    @Resource
    private OrderOpenidMapFailureDao orderOpenidMapFailureDao;


}
