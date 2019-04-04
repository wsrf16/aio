package com.york.portable.swiss.sugar;

//import org.apache.commons.io.IOUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShellUtils {
    public static List<String> run(String... cmd) {
        List<String> feedbackList = Arrays.stream(cmd).map(c -> {
            try {
                Process process = Runtime.getRuntime().exec(c);
                process.waitFor();
                InputStream inputStream = process.getInputStream();
                InputStream errorStream = process.getErrorStream();
                String feedback = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                String error = IOUtils.toString(errorStream, StandardCharsets.UTF_8);
                if (StringUtils.isNotEmpty(error))
                    throw new Exception(error);
                return feedback;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return feedbackList;
    }
}
