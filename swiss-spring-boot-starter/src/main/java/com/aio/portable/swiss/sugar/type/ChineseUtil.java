package com.aio.portable.swiss.sugar.type;

public class ChineseUtil {
//    // 方法一：正则表达式判断（适用于常用汉字）
//    public static boolean containsChineseByRegex(String str) {
//        if (str == null) return false;
//        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//        return p.matcher(str).find();
//    }
//
//    // 方法二：UnicodeBlock 判断（更精准，包含生僻字和中文标点）
    public static boolean containsChineseByBlock(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
                return true;
            }
        }
        return false;
    }
}
