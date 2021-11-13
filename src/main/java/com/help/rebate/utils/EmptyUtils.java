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
public class EmptyUtils {

    /**
     * 断言条件必须为真
     * @param object
     */
    public static boolean isNotEmpty(String object) {
        return object!=null && !object.trim().isEmpty();
    }

    /**
     * 断言条件必须为真
     * @param object
     */
    public static boolean isNotEmpty(Collection object) {
        return object!=null && object.size()!=0;
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
