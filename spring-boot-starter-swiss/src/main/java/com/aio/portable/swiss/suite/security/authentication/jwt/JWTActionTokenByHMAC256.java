package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTClaims;
import com.aio.portable.swiss.autoconfigure.properties.JWTClaimsProperties;
import com.aio.portable.swiss.suite.algorithm.cipher.CipherSugar;
import com.aio.portable.swiss.suite.net.protocol.http.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public abstract class JWTActionTokenByHMAC256 implements JWTAction {
    @Autowired
    private JWTClaimsProperties jwtClaimsProperties;

    protected String tokenEncode(String token) {
        return token;
    }

    protected String tokenDecode(String token) {
        return token;
    }

    @Override
    public JWTClaims toJWTClaims() {
        return jwtClaimsProperties.toJWTClaims();
    }

    @Override
    public String sign(JWTCreator.Builder builder) {
        String token = JWTSugar.Classic.signForTokenByHMAC256(builder, jwtClaimsProperties.getSecret());
        String encodeToken = tokenEncode(token);
        return encodeToken;
    }

    @Override
    public Boolean verify(String base64Token) {
        String decodeToken = tokenDecode(base64Token);
        return JWTSugar.Classic.verifyByHMAC256(decodeToken, jwtClaimsProperties.getSecret());
    }

    @Override
    public DecodedJWT parse(String base64Token) {
        String decodeToken = tokenDecode(base64Token);
        DecodedJWT parse = JWTSugar.Classic.parseTokenByHMAC256(decodeToken, jwtClaimsProperties.getSecret());
        return parse;
    }
}
