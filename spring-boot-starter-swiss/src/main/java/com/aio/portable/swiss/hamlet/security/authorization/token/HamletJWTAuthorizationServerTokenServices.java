package com.aio.portable.swiss.hamlet.security.authorization.token;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.structure.security.authentication.jwt.JWTAction;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Date;

public class HamletJWTAuthorizationServerTokenServices implements AuthorizationServerTokenServices {
    @Autowired
    private JWTAction jwtAction;

    @Autowired
    private TokenStore tokenStore;


    private String create(JWTProperties jwtProperties, String issuer) {
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(issuer);
        builder.withClaim("r", System.currentTimeMillis());
        builder.withIssuedAt(jwtProperties.getIssuedAt());
        builder.withExpiresAt(jwtProperties.getExpiresAt());

        String tokenWord = jwtAction.token(builder);
        return tokenWord;
    }

    private DefaultExpiringOAuth2RefreshToken createRefreshToken(JWTProperties jwtProperties, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        String value = create(jwtProperties, issuer);
        Date expiresAt = jwtProperties.getExpiresAt();
        DefaultExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(value, expiresAt);
        return refreshToken;
    }

    private DefaultOAuth2AccessToken createAccessToken(JWTProperties jwtProperties, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        String value = create(jwtProperties, issuer);
        Date expiresAt = jwtProperties.getExpiresAt();
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(value);
        accessToken.setExpiration(expiresAt);
        accessToken.setScope(authentication.getOAuth2Request().getScope());
        return accessToken;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        JWTProperties refreshTokenJWTProperties = jwtAction.toJwtProperties();
        DefaultExpiringOAuth2RefreshToken refreshToken = createRefreshToken(refreshTokenJWTProperties, authentication);
        JWTProperties accessTokenJWTProperties = jwtAction.toJwtProperties();
        DefaultOAuth2AccessToken accessToken = createAccessToken(accessTokenJWTProperties, authentication);
        accessToken.setRefreshToken(refreshToken);

        tokenStore.storeAccessToken(accessToken, authentication);
        tokenStore.storeRefreshToken(refreshToken, authentication);
        return accessToken;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest) throws AuthenticationException {
//        throw new UnsupportedOperationException();
        OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(refreshTokenValue);
        if (refreshToken == null) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
        }

        OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(refreshToken);
        OAuth2AccessToken accessToken = createAccessToken(authentication);
        return accessToken;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return tokenStore.getAccessToken(authentication);
    }
}
