package com.aio.portable.swiss.suite.algorithm.encode;

import org.springframework.security.crypto.codec.Hex;


public abstract class HexConvert {

    public static String encode(byte[] bytes) {
        return new String(Hex.encode(bytes));
    }

    public static byte[] decode(CharSequence s) {
        return Hex.decode(s);
    }
    
}
