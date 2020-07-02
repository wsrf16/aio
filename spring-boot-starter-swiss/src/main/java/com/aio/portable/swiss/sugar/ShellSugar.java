package com.aio.portable.swiss.sugar;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ShellSugar {
    public static List<String> run(String... cmd) {
        List<String> feedbackList = Arrays.stream(cmd).map(c -> {
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
}
