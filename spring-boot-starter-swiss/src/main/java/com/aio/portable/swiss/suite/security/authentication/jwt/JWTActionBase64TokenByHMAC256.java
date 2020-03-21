package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTClaims;
import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.suite.net.protocol.http.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JWTActionBase64TokenByHMAC256 implements JWTAction {
    @Autowired
    private JWTProperties jwtProperties;

    @Override
    public JWTClaims toJWTClaims() {
        return jwtProperties.toJWTClaims();
    }

//    @Override
//    public String token(String issuer) {
//        JWTCreator.Builder builder = JWTSugar.createJWTBuilderWithIssuer(jwtProperties, issuer);
//        return token(builder);
//    }

    @Override
    public String sign(JWTCreator.Builder builder) {
        return JWTSugar.Classic.signForBase64TokenByHMAC256(builder, jwtProperties.getSecret());
    }

    @Override
    public Boolean verify(String token) {
        return JWTSugar.Classic.verifyByHMAC(token, jwtProperties.getSecret());
    }

    @Override
    public DecodedJWT parse(String token) {
        DecodedJWT parse = JWTSugar.Classic.parseBase64TokenByHMAC256(token, jwtProperties.getSecret());
        return parse;
    }
}
