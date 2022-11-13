package com.help.rebate.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间服务
 * @author zfcisbest
 * @date 21/11/14
 */
public class TimeUtil {
    private static final SimpleDateFormat ymd_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    private static final DateTimeFormatter local_ymd_hms = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final Map<String, SimpleDateFormat> format2ObjMap = new HashMap<String, SimpleDateFormat>(16, 1);

    static {
        format2ObjMap.put("yyyy-MM-dd HH:mm:ss", ymd_hms);
        format2ObjMap.put("yyyyMMddHHmmss", ymdhms);
        format2ObjMap.put("yyyy-MM-dd", y_m_d);
        format2ObjMap.put("yyyyMMdd", ymd_hms);
    }

    /**
     * 解析出时间，并格式化
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        ParsePosition pos = new ParsePosition(0);
        Date parse = ymd_hms.parse(date, pos);
        return parse;
    }

    /**
     * 解析出时间，并格式化
     * @param date
     * @return
     */
    public static LocalDateTime parseLocalDate(String date) {
        return LocalDateTime.parse(date, local_ymd_hms);
    }

    /**
     * 解析出时间，并格式化
     * @param date
     * @return
     */
    public static Date parseDateShort(String date) {
        ParsePosition pos = new ParsePosition(0);
        Date parse = y_m_d.parse(date, pos);
        return parse;
    }

    /**
     * 直接格式化
     * @param dataLong
     * @return
     */
    public static String format(Date dataLong) {
        if (dataLong == null) {
            return null;
        }

        return ymd_hms.format(dataLong);
    }

    /**
     * 直接格式化
     * @param dataLong
     * @return
     */
    public static String formatLocalDate(LocalDateTime dataLong) {
        if (dataLong == null) {
            return null;
        }

        return dataLong.format(local_ymd_hms);
    }

    /**
     * 直接格式化
     * @param dataLong
     * @return
     */
    public static String format(Object dataLong, String format) {
        if (dataLong == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = format2ObjMap.get(format);
        if (simpleDateFormat != null) {
            return simpleDateFormat.format(dataLong);
        }

        synchronized (TimeUtil.class) {
            simpleDateFormat = new SimpleDateFormat(format);
            format2ObjMap.put(format, simpleDateFormat);
        }

        return simpleDateFormat.format(dataLong);
    }
}
