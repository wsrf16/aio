package com.aio.portable.swiss.suite.algorithm.transcode;

import com.aio.portable.swiss.suite.algorithm.cipher.CipherSugar;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TranscoderBase64 implements Transcoder {
    @Override
    public String encode(String plain, Charset charset) {
        return CipherSugar.JavaUtil.encodeBase64(plain, charset);
    }

    @Override
    public String decode(String base64, Charset charset) {
        return CipherSugar.JavaUtil.decodeBase64(base64, charset);
    }




}
