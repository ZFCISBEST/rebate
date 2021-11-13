package com.help.rebate.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private final static char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_CHAR[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_CHAR[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String getMd5Hex(byte[] source) {
        byte[] result = getMd5(source);
        if (result != null) {
            return toHexString(result);
        }
        return "0000000000000000";
    }

    private static byte[] getMd5(byte[] source) {
        if (source == null) {
            return null;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            return md.digest();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
