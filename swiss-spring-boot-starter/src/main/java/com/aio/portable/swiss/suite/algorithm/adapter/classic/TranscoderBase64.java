package com.aio.portable.swiss.suite.algorithm.adapter.classic;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.algorithm.adapter.Transcoder;

import java.nio.charset.Charset;

public class TranscoderBase64 implements Transcoder {
    @Override
    public String encode(String plain, Charset charset) {
        return JDKBase64Convert.encodeToString(plain.getBytes(charset));
    }

    @Override
    public String decode(String cipher, Charset charset) {
        return new String(JDKBase64Convert.decode(cipher.getBytes(charset)), charset);
    }

}
