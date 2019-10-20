package com.aio.portable.swiss.sugar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexSugar {
    private final static String REGEX_PHONE = "^(1)\\d{10}$";
    private final static String FULL_REGEX_PHONE = "^((00|\\+)86)?1\\d{10}$";
    private final static String FAKE_TEXT = "****";
    private final static String REGEX_DATE = "([0-9]{4})(-([0-9]{2})(-([0-9]{2})" +
            "(T([0-9]{2}):([0-9]{2})(:([0-9]{2})(\\.([0-9]+))?)?" +
            "(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?";


    public final static boolean checkPhone(String phone) {
        return Pattern.compile(FULL_REGEX_PHONE).matcher(phone).find();
    }

    public final static String sensitivePhone(String phone) {
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
     * @param regex
     * @param input
     * @return
     */
    public final static boolean find(String regex, String input) {
        return Pattern.compile(regex).matcher(input).find();
    }


    /**
     * 正则匹配
     * @param regex
     * @param input
     * @return
     */
    public final static Matcher matcher(String regex, String input) {
        return Pattern.compile(regex).matcher(input);
    }

    /**
     * 正则匹配
     * @param regex
     * @param input
     * @return
     */
    public final static List<List<String>> matches(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        List<List<String>> matches = new ArrayList<>();
        while (matcher.find()) {
            List<String> matchesInOneLine = new ArrayList<>();
            for (int ii = 1; ii <= matcher.groupCount(); ii++) {
                matchesInOneLine.add(matcher.group(ii));
            }
            matches.add(matchesInOneLine);
        }
        return matches;
    }

//    /**
//     * 正则替换
//     * @param content
//     * @param regex
//     * @param replacement
//     * @return
//     */
//    public final static String replaceAll(String content, String regex, String replacement) {
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(content);
//
//        String result = matcher.find() ? matcher..replaceAll(replacement) : regex;
//        return result;
//    }


    /**
     * 正则替换
     * @param input
     * @param regex
     * @param replacement
     * @return
     */
    public final static String replaceAll(String regex, String input, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        String result = matcher.find() ? matcher.replaceAll(replacement) : regex;
        return result;
    }


    /**
     * replace
     * @param regex
     * @param input
     * @param replacement
     * @return
     */
    public static String replace(String regex, String input, String... replacement) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        int i = -1;
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, replacement[++i]);
        }
        return sb.toString();
    }

    private static class BlahUnit {
        private static void regex() {
            String ret1 = RegexSugar.sensitivePhone("12345678901");
            String ret2 = RegexSugar.replaceAll("4567", "12345678901", "xxxx");
            boolean ret3 = RegexSugar.find("456", "12345678901");
            System.out.println(ret1);
            System.out.println(ret2);
            System.out.println(ret3);



            String input = "${name}-babalala-${age}-${address}++${name}-babalala";
            String regex = "\\$\\{(.+?)\\}-(ba.+?)";
            // .group(0): ${name}-baba
            // .group(1): name
            // .group(2): baba
            List<List<String>> matches = RegexSugar.matches(regex, input);

            String replacement[] = {"1", "2", "3", "4", "5"};
            RegexSugar.replace(regex, input, replacement);
        }
    }
}
