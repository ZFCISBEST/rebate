package com.help.rebate.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    /**
     * 过滤掉字符串中的表情
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = new StringBuilder(source.length());;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }

    /**
     * 判断某个字符是不是表情
     * @param codePoint
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        boolean isNotEmoji = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
        return !isNotEmoji;
    }
}
