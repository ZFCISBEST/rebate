package com.help.rebate.utils;

import java.util.*;

/**
 * 校验
 * @author zfcisbest
 * @date 21/11/13
 */
public class EmptyUtils {

    /**
     * 断言条件必须为真
     * @param object
     */
    public static boolean isEmpty(String object) {
        return object == null || object.trim().isEmpty();
    }

    /**
     * 断言条件必须为真
     * @param object
     */
    public static boolean isEmpty(Collection object) {
        return object == null || object.size() == 0;
    }

    /**
     * 断言条件必须为真
     * @param target
     * @param range
     */
    public static boolean isIn(String target, String[] range) {
        Optional<String> first = Arrays.stream(range).filter(r -> target.equals(r)).findFirst();
        return first.isPresent();
    }

    /**
     * 断言条件必须为真
     * @param target
     * @param range
     */
    public static boolean isIn(int target, int[] range) {
        OptionalInt first = Arrays.stream(range).filter(r -> target == r).findFirst();
        return first.isPresent();
    }

    /***
     * 断言条件必须为真
     * @param target
     * @param range
     */
    public static boolean isIn(long target, long[] range) {
        OptionalLong first = Arrays.stream(range).filter(r -> target == r).findFirst();
        return first.isPresent();
    }
}
