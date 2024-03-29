package com.aio.portable.swiss.suite.algorithm.adapter.classic;

import com.aio.portable.swiss.suite.algorithm.adapter.Transcoder;

import java.nio.charset.Charset;

public class TranscoderPlain implements Transcoder {
    @Override
    public String encode(String plain, Charset charset) {
        return plain;
    }

    @Override
    public String decode(String cipher, Charset charset) {
        return cipher;
    }

    @Override
    public String encode(String plain) {
        return plain;
    }

    @Override
    public String decode(String cipher) {
        return cipher;
    }



}
