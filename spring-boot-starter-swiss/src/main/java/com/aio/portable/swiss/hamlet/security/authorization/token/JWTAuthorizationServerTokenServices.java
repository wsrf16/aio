package com.aio.portable.swiss.hamlet.security.authorization.token;

import com.aio.portable.swiss.suite.security.authentication.jwt.JWTFactory;
import com.aio.portable.swiss.suite.algorithm.transcode.Transcoder;
import com.aio.portable.swiss.suite.algorithm.transcode.TranscoderBase64;
import com.aio.portable.swiss.suite.algorithm.transcode.Transcoding;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTAction;
import com.auth0.jwt.JWTCreator;
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

/**
 * JWTAuthorizationServerTokenServices
 * Method "configure(AuthorizationServerEndpointsConfigurer endpoints)" In AuthorizationServerConfigurerAdapter.class
 */
public class JWTAuthorizationServerTokenServices implements AuthorizationServerTokenServices, Transcoding {
    private JWTAction jwtAction;

    /**
     * 配置AccessToken的存储方式:此处使用Redis存储
     * Token的可选存储方式
     * 1、InMemoryTokenStore
     * 2、JdbcTokenStore
     * 3、JwtTokenStore
     * 4、RedisTokenStore
     * 5、JwkTokenStore
     */
    private TokenStore tokenStore;

    private Transcoder transcode = new TranscoderBase64();

    @Override
    public Transcoder getTranscode() {
        return transcode;
    }

    @Override
    public void setTranscode(Transcoder transcode) {
        this.transcode = transcode;
    }



    public JWTAuthorizationServerTokenServices(JWTAction jwtAction, TokenStore tokenStore) {
        this.jwtAction = jwtAction;
        this.tokenStore = tokenStore;
    }





    private String create(JWTFactory jwt, String issuer) {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withIssuer(issuer);
        builder.withClaim("r", System.currentTimeMillis());
        builder.withIssuedAt(jwt.getIssuedAt());
        builder.withExpiresAt(jwt.getExpiresAt());

        String tokenWord = jwtAction.sign(builder);
        return tokenWord;
    }

    private DefaultOAuth2AccessToken createAccessToken(JWTFactory jwt, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        String value = create(jwt, issuer);
        Date expiresAt = jwt.getExpiresAt();
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(value);
        accessToken.setExpiration(expiresAt);
        accessToken.setScope(authentication.getOAuth2Request().getScope());
        return accessToken;
    }

    private DefaultExpiringOAuth2RefreshToken createRefreshToken(JWTFactory jwt, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        String value = create(jwt, issuer);
        Date expiresAt = jwt.getExpiresAt();
        DefaultExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(value, expiresAt);

        return refreshToken;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        JWTFactory accessTokenJWT = jwtAction.newFactory();
        DefaultOAuth2AccessToken accessToken = createAccessToken(accessTokenJWT, authentication);
        JWTFactory refreshTokenJWT = jwtAction.newFactory();
        DefaultExpiringOAuth2RefreshToken refreshToken = createRefreshToken(refreshTokenJWT, authentication);

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
