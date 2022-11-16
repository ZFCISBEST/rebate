package com.help.rebate.vo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 返利信息封装
 */
@Data
public class CommissionVO {
    private static final Logger logger = LoggerFactory.getLogger(CommissionVO.class);

    /**
     * 可提取的返利
     */
    private String remainCommission;

    /**
     * 将来可提取的返利，尚未走到结算态的数据
     */
    private String futureCommission;
}
