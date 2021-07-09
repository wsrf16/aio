package com.aio.portable.swiss.suite.algorithm.encode;

public class UnicodeConvert {
    public static String decode(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        StringBuilder sb = new StringBuilder();
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            sb.append((char) Integer.valueOf(strs[i], 16).intValue());
        }
        return sb.toString();
    }

    public static String encode(String cn) {
        char[] chars = cn.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            sb.append("\\u" + Integer.toString(chars[i], 16));
        }
        return sb.toString();
    }
}
