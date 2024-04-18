package com.aio.portable.swiss.suite.algorithm.adapter.classic;

import com.aio.portable.swiss.suite.algorithm.encode.UnicodeConvert;
import com.aio.portable.swiss.suite.algorithm.adapter.Transcoder;

import java.nio.charset.Charset;

public class TranscoderUnicode implements Transcoder {
    @Override
    public String encode(String plain, Charset charset) {
        return UnicodeConvert.encode(plain);
    }

    @Override
    public String decode(String cipher, Charset charset) {
        return UnicodeConvert.decode(cipher);
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
