package com.help.rebate.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * 校验
 * @author zfcisbest
 * @date 21/11/13
 */
public class Checks {

    /**
     * 断言条件必须为真，否则抛出指定异常
     * @param assertion
     * @param msg
     */
    public static void isTrue(boolean assertion, String msg) {
        if (!assertion) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 判定，对象不能为null，否则抛出异常
     * @param object
     * @param msg
     */
    public static void isNotNull(Object object, String msg) {
        if (object == null) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 字符串判定不能为空，否则抛出异常
     * @param object
     * @param msg
     */
    public static void isNotEmpty(String object, String msg) {
        if (object == null || object.trim().isEmpty()) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 集合判定不能为空，否则跑出异常
     * @param object
     * @param msg
     */
    public static void isNotEmpty(Collection object, String msg) {
        if (object == null || object.size() == 0) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * int型数组判定
     * @param target
     * @param range
     * @param msg
     */
    public static void isIn(int target, int[] range, String msg) {
        OptionalInt first = Arrays.stream(range).filter(r -> target == r).findFirst();
        if (!first.isPresent()) {
            throw new RuntimeException(msg);
        }
    }

    /***
     * long型数组判定
     * @param target
     * @param range
     * @param msg
     */
    public static void isIn(long target, long[] range, String msg) {
        OptionalLong first = Arrays.stream(range).filter(r -> target == r).findFirst();
        if (!first.isPresent()) {
            throw new RuntimeException(msg);
        }
    }
}
