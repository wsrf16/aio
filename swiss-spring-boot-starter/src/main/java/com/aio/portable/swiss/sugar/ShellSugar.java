package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.suite.bean.BeanSugar;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ShellSugar {
    private static final String VARIABLE_LONG = "\\$\\'{'{0}\\'}'";
    private static final String VARIABLE_SHORT = "\\${0}";
    private static final List<String> VARIABLE_LIST = Arrays.asList(VARIABLE_LONG, VARIABLE_SHORT);

    public static final String spellLongVariable(String name) {
        return MessageFormat.format(VARIABLE_LONG, name);
    }

    public static final String spellShortVariable(String name) {
        return MessageFormat.format(VARIABLE_SHORT, name);
    }

    public static final String setVariable(String input, Object bean) {
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

    public static final List<String> exec(String... scripts) {
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
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return feedbackList;
    }

    private static final Echo getEcho(Process process) {
        try {
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            Echo echo = new Echo();
            String cmdStd = org.springframework.util.StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            String errStd = org.springframework.util.StreamUtils.copyToString(errorStream, StandardCharsets.UTF_8);
            echo.setCmdStd(cmdStd);
            echo.setErrStd(errStd);
            return echo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final Process run(String... scripts) {
        ProcessBuilder builder = new ProcessBuilder(scripts);
        try {
            Process process = builder.start();
            return process;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Echo {
        private String cmdStd;
        private String errStd;

        public String getCmdStd() {
            return cmdStd;
        }

        public void setCmdStd(String cmdStd) {
            this.cmdStd = cmdStd;
        }

        public String getErrStd() {
            return errStd;
        }

        public void setErrStd(String errStd) {
            this.errStd = errStd;
        }
    }

    public static class Windows {
        public static void loadURL(String url) {
//            exec("rundll32 url.dll,FileProtocolHandler " + url);
            exec("cmd /c start " + url);
        }
    }





}
