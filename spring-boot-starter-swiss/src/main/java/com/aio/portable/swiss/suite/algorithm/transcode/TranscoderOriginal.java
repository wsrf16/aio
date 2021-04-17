package com.aio.portable.swiss.suite.algorithm.transcode;

import java.nio.charset.Charset;

public class TranscoderOriginal implements Transcoder {
    @Override
    public String encode(String plain, Charset charset) {
        return plain;
    }

    @Override
    public String decode(String base64, Charset charset) {
        return base64;
    }

    @Override
    public String encode(String plain) {
        return plain;
    }

    @Override
    public String decode(String base64) {
        return base64;
    }



}
