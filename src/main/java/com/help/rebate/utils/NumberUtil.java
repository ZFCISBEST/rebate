package com.help.rebate.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间服务
 * @author zfcisbest
 * @date 21/11/14
 */
public class NumberUtil {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    /**
     * 解析出时间，并格式化
     * @param decimalString
     * @return
     */
    public static BigDecimal parseBigDecimal(String decimalString) {
        if (EmptyUtils.isEmpty(decimalString)) {
            return null;
        }

        return new BigDecimal(decimalString.replaceAll(",", ""));
    }

    /**
     * 直接格式化
     * @param BigDecimal
     * @return
     */
    public static String format(BigDecimal BigDecimal) {
        if (BigDecimal == null) {
            return null;
        }

        return decimalFormat.format(BigDecimal);
    }
}
