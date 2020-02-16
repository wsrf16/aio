package com.aio.portable.swiss.sugar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class PatternSugar {
    private final static String REGEX_PHONE = "^(1)\\d{10}$";
    private final static String FULL_REGEX_PHONE = "^((00|\\+)86)?1\\d{10}$";
    private final static String FAKE_TEXT = "****";
    private final static String REGEX_DATE = "([0-9]{4})(-([0-9]{2})(-([0-9]{2})" +
            "(T([0-9]{2}):([0-9]{2})(:([0-9]{2})(\\.([0-9]+))?)?" +
            "(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?";


    public final static boolean matchPhone(String phone) {
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
    public final static boolean match(String regex, String input) {
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
    public final static List<List<String>> findMore(String regex, String input) {
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

    public final static List<String> find(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        List<List<String>> matches = new ArrayList<>();
        while (matcher.find()) {
            List<String> matchesInOneLine = new ArrayList<>();
            for (int ii = 1; ii <= matcher.groupCount(); ii++) {
                matchesInOneLine.add(matcher.group(ii));
            }
            matches.add(matchesInOneLine);
        }
        List<String> eachFirst = matches.stream().map(c -> c.get(0)).collect(Collectors.toList());
        return eachFirst;
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
    public static String replace(String regex, String input, Object... replacement) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        int i = -1;
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, replacement[++i].toString());
        }
        return sb.toString();
    }


}
