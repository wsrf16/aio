package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.suite.bean.BeanSugar;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ShellSugar {
    private final static String VARIABLE_LONG = "\\$\\'{'{0}\\'}'";
    private final static String VARIABLE_SHORT = "\\${0}";
    private final static List<String> VARIABLE_LIST = Arrays.asList(VARIABLE_LONG, VARIABLE_SHORT);

    public final static String spellLongVariable(String name) {
        return MessageFormat.format(VARIABLE_LONG, name);
    }

    public final static String spellShortVariable(String name) {
        return MessageFormat.format(VARIABLE_SHORT, name);
    }

    public final static String setVariable(String input, Object bean) {
        Map<String, Object> map = bean instanceof Map ? (Map<String, Object>) bean : BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(bean);
        String result = input;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            List<String> variables = Arrays.asList(spellLongVariable(name), spellShortVariable(name));
            result = RegexSugar.replaceAll(result, variables, value);
        }
        return result;
    }

    public static List<String> exec(String... scripts) {
        List<String> feedbackList = Arrays.stream(scripts).map(c -> {
            try {
                Process process = Runtime.getRuntime().exec(c);
                process.waitFor();
                InputStream inputStream = process.getInputStream();
                InputStream errorStream = process.getErrorStream();
                String feedback = org.springframework.util.StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                String error = org.springframework.util.StreamUtils.copyToString(errorStream, StandardCharsets.UTF_8);
                if (error == null)
                    throw new NullPointerException(error);
                return feedback;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return feedbackList;
    }

    public static class Windows {
        public static void loadURL(String url) {
//            exec("rundll32 url.dll,FileProtocolHandler " + url);
            exec("cmd /c start " + url);
        }
    }




    public void print(String s) {
        System.out.println(s);
    }

    public void clean() {
        System.out.println("\r");
    }
}
