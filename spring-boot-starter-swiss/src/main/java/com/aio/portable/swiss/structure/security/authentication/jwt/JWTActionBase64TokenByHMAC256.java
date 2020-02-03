package com.aio.portable.swiss.structure.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.structure.net.protocol.http.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTActionBase64TokenByHMAC256 implements JWTAction {
    @Autowired
    private JWTProperties jwtProperties;

    public JWTProperties getJwtProperties() {
        return jwtProperties;
    }

    @Override
    public String token(String userName) {
        JWTCreator.Builder builder = JWTSugar.createJWTBuilderWithIssuer(jwtProperties, userName);
        return JWTSugar.Classic.signForBase64TokenByHMAC256(builder, jwtProperties.getSecret());
    }

    @Override
    public Boolean verify(String token) {
        return JWTSugar.Classic.verifyByHMAC(token, jwtProperties.getSecret());
    }

    @Override
    public DecodedJWT getJWT(String token) {
        DecodedJWT parse;
        try {
            parse = JWTSugar.Classic.parseBase64TokenByHMAC256(token, jwtProperties.getSecret());
        } catch (Exception e) {
            throw new BizException(BizStatusEnum.UNAUTHORIZED.getCode(), BizStatusEnum.UNAUTHORIZED.getMessage());
        }

        return parse;
    }
}
