package com.help.rebate.model;

import lombok.Data;

/**
 * 1、用户数
 * 2、总用户返利
 * 3、已返利
 * 4、理论盈余
 *
 * 5、最近七日转链次数（去重商品数）
 * 6、最近七日订单数
 * 7、订单转化率
 */
@Data
public class DashboardVO {
    /**
     * 用户数
     */
    private Long userCount;

    /**
     * 所有用户的所有返利
     */
    private String allUserTotalCommission;

    /**
     * 所有用户的尚未提现的返利
     */
    private String allUserRemainingCommission;

    /**
     * 理论的盈余，所有映射中的返利-技术服务费的加总，减去，给所有用户的返利
     */
    private String theoreticalEarnings;

    /**
     * 最近7日转链次数
     */
    private Long convertTklCountOfLast7Days;

    /**
     * 最近7日订单量
     */
    private Long orderCountOfLast7Days;
}
