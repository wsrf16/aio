package com.aio.portable.swiss.suite.algorithm.encode;

public abstract class Base62Convert {
    private static final String BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String encode(long number) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % 62);
            sb.insert(0, BASE62_CHARS.charAt(remainder));
            number = number / 62;
        }
        return sb.toString();
    }

    public static final long decode(String base62) {
        long number = 0;
        for (int i = 0; i < base62.length(); i++) {
            char c = base62.charAt(i);
            int digit = BASE62_CHARS.indexOf(c);
            number = number * 62 + digit;
        }
        return number;
    }
}
