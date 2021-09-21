package com.aio.portable.swiss.suite.algorithm.adapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public interface Transcoder {
    String encode(String plain, Charset charset);

    String decode(String cipher, Charset charset);

    default String encode(String plain) {
        return encode(plain, StandardCharsets.UTF_8);
    }

    default String decode(String cipher) {
        return decode(cipher, StandardCharsets.UTF_8);
    }

    default boolean matches(String plain, String cipher) {
        return Objects.equals(encode(plain), cipher);
    }

    default boolean matches(String plain, Charset charset, String cipher) {
        return Objects.equals(encode(plain, charset), cipher);
    }

}
