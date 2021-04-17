package com.aio.portable.swiss.suite.security.authentication.jwt;

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

        JWTFactory jwt = jwtAction.newFactory();
//        String secret = jwtClaims.getSecret();
        JWTCreator.Builder builder = jwt.createJWTBuilder();
        String token = jwtAction.sign(builder);
        httpServletResponse.setHeader(AUTHORIZATION_HEAD, token);
    }
}
