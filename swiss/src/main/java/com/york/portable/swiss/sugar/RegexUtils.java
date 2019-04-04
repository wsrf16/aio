package com.york.portable.swiss.sugar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    private final static String REGEX_PHONE = "^(1)\\d{10}$";
    private final static String FULL_REGEX_PHONE = "^((00|\\+)86)?1\\d{10}$";
    private final static String FAKE_TEXT = "****";
    private final static String REGEX_DATE = "([0-9]{4})(-([0-9]{2})(-([0-9]{2})" +
            "(T([0-9]{2}):([0-9]{2})(:([0-9]{2})(\\.([0-9]+))?)?" +
            "(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?";


    public final static boolean checkPhone(String phone) {
        return Pattern.compile(FULL_REGEX_PHONE).matcher(phone).find();
    }

    public final static String fakePhone(String phone) {
        if (phone.length() != 11)
            return phone;
        String result = null;
        final String condition = REGEX_PHONE;
        Pattern pattern = Pattern.compile(condition);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.find()) {
            String matchers = matcher.group(0);
            String prefix = matchers.substring(0, 3);
            String suffix = matchers.substring(7, 11);
            StringBuffer sb = new StringBuffer();
            result = sb.append(prefix).append(FAKE_TEXT).append(suffix).toString();
        }
        return result;
    }

    /**
     * 正则匹配
     * @param content
     * @param regex
     * @return
     */
    public final static boolean matches(String content, String regex) {
        return Pattern.compile(regex).matcher(content).find();
    }

    /**
     * 正则替换
     * @param content
     * @param regex
     * @param replacement
     * @return
     */
    public final static String replaceAll(String content, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        String result = matcher.find() ? matcher.replaceAll(replacement) : regex;
        return result;
    }
}
