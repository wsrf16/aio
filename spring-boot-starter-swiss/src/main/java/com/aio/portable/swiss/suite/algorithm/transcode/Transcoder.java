package com.aio.portable.swiss.suite.algorithm.transcode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface Transcoder {
    String encode(String plain, Charset charset);

    String decode(String base64, Charset charset);

    default String encode(String plain) {
        return encode(plain, StandardCharsets.UTF_8);
    }

    default String decode(String base64) {
        return decode(base64, StandardCharsets.UTF_8);
    }

}
