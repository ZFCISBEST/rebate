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


    public static String KeyOpen(String inputString) {
        char[] a = inputString.toCharArray();
        for(int i=0;i<a.length;i++){
            a[i] = (char) ((a[i] - i%5));
        }
        String s = new String(a);
        s = JM(KL(s));
        return s;
    }



    public static String IdOpen(String inputString) {
        char[] a = inputString.toCharArray();
        for(int i=0;i<a.length;i++){
            a[i] = (char) ((a[i] - 1 - i/9));
        }
        String s = new String(a);
        s = JM(KL(s));
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

}
