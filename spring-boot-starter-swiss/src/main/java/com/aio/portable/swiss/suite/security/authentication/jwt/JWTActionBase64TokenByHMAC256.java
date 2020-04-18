package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.suite.algorithm.cipher.CipherSugar;

import java.nio.charset.StandardCharsets;

public abstract class JWTActionBase64TokenByHMAC256 extends JWTActionTokenByHMAC256 {
    protected String tokenEncode(String token) {
        return CipherSugar.JavaUtil.encodeBase64(token, StandardCharsets.UTF_8);
    }

    protected String tokenDecode(String token) {
        return CipherSugar.JavaUtil.decodeBase64(token, StandardCharsets.UTF_8);
    }
}
