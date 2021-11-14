package com.help.rebate.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * 属性提取器
 *
 * @author zfcisbest
 * @date 21/11/14
 */
public class PropertyValueResolver {
    private static final Logger logger = LoggerFactory.getLogger(PropertyValueResolver.class);

    private static final char NESTED = '.';
    private static final char MAPPED_START = '(';
    private static final char MAPPED_END = ')';
    private static final char INDEXED_START = '[';
    private static final char INDEXED_END = ']';

    private static final PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

    /**
     * 获取嵌套的属性值
     * @param bean
     * @param propStr
     * @param required
     * @param <T>
     * @return
     */
    public final static <T> T getProperty(Object bean, String propStr, boolean required) {
        try {
            return getProperty(bean, propStr);
        } catch (Exception e) {
            if (required) {
                throw e;
            }
        }
        return null;
    }


    /**
     * 根据给定的属性表达式，从bean中获取嵌套的值
     * @param bean
     * @param propStr
     * @param <T>
     * @return
     * @throws Exception
     */
    public final static <T> T getProperty(Object bean, String propStr){
        //直接返回原始对象
        if (propStr == null || propStr.trim().isEmpty()) {
            return (T) bean;
        }

        //转换
        propStr = convertPropertyPattern(propStr);
        Object nestedProperty = null;
        try {
            nestedProperty = propertyUtilsBean.getNestedProperty(bean, propStr);
        } catch (Exception e) {
            String[] propArr = propStr.split("\\.");
            Object targetObj = bean;
            for (int i = 0; i < propArr.length; i++) {
                String fieldName = propArr[i].trim();
                if (!fieldName.contains("[") && !(targetObj instanceof Collection) && !(targetObj instanceof Map) && !targetObj.getClass().isArray()) {
                    throw new IllegalArgumentException("can not resolve property [" + fieldName + "]");
                }

                //是数字
                try {
                    if (isNumber(fieldName)) {
                        for (Object key : ((Map) targetObj).keySet()) {
                            if (key.toString().equals(fieldName)) {
                                targetObj = ((Map) targetObj).get(key);
                                break;
                            }
                        }
                    } else {
                        targetObj = propertyUtilsBean.getNestedProperty(targetObj, fieldName);
                    }
                } catch (Exception ex) {
                    throw new IllegalArgumentException("can not resolve property [" + fieldName + "]");
                }
            }
        }

        return (T) nestedProperty;
    }

    /**
     * 判断是否为数字
     * @param fieldName
     * @return
     */
    public static boolean isNumber(String fieldName) {
        return NumberUtils.isDigits(fieldName);
    }

    /**
     * 转化为内部表达形式
     * @param propStr
     * @return
     */
    private static String convertPropertyPattern(String propStr) {
        if (propStr == null || propStr.isEmpty()) {
            return propStr;
        }

        //存储新的字符串
        StringBuilder sb = new StringBuilder();
        //对propStr进行适配
        int i = -1;
        for (i = propStr.length() - 1; i > 0; i--) {
            char c = propStr.charAt(i);
            sb.insert(0, c);

            if (c == INDEXED_START) {
                char c1 = propStr.charAt(i - 1);
                if (c1 != NESTED) {
                    sb.insert(0, NESTED);
                }
                sb.insert(0, c1);
                i--;
            }
        }

        if (i == 0) {
            char c = propStr.charAt(i);
            sb.insert(0, c);
        }

        //判断首字符是不是点
        if (sb.charAt(0) == NESTED) {
            sb.delete(0, 1);
        }

        return sb.toString();
    }
}
