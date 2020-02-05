package com.aio.portable.swiss.structure.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.structure.net.protocol.http.JWTSugar;
import com.auth0.jwt.JWTCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    //    private final static String ISSURE = "ladder";
    private final static String AUTHORIZATION_HEAD = JWTAction.AUTHORIZATION_HEAD;

    @Autowired
    private JWTAction jwtAction;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        JWTProperties jwtProperties = jwtAction.toJwtProperties();

        String secret = jwtProperties.getSecret();
        JWTCreator.Builder builder = JWTSugar.createJWTBuilder(jwtProperties);
        String token = JWTSugar.Classic.signForBase64TokenByHMAC256(builder, secret);
        httpServletResponse.setHeader(AUTHORIZATION_HEAD, token);
    }
}
