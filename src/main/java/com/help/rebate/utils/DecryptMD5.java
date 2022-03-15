package com.help.rebate.utils;

import java.security.MessageDigest;

public class DecryptMD5 {

    public static String MD5(String inputString) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println((e.toString()));
            e.printStackTrace();
            return "";
        }

        char[] charArray = inputString.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append(0);
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String KL(String inputString) {
        char[] a = inputString.toCharArray();
        for(int i=0;i<a.length;i++){
            a[i] = (char) (a[i] ^'t');
        }
        String s = new String(a);
        return s;
    }

    public static String JM(String inputString) {
        char[] a = inputString.toCharArray();
        for(int i =0;i<a.length;i++){
            a[i] = (char) (a[i]^ 't');
        }
        String k = new String(a);
        return k;
    }

    public static void main(String args[]) {
        String s = "sa";
        System.out.println("原始："+s);
        System.out.println("MD5后："+MD5(s));
        System.out.println("MD5后再加密："+KL(MD5(s)));
        System.out.println("解密为MD5后"+JM(KL(MD5(s))));
    }
}