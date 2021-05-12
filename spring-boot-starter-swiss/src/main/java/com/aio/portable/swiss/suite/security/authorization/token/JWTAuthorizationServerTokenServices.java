package com.aio.portable.swiss.suite.security.authorization.token;

import com.aio.portable.swiss.suite.algorithm.transcode.Transcoder;
import com.aio.portable.swiss.suite.algorithm.transcode.TranscoderBase64;
import com.aio.portable.swiss.suite.algorithm.transcode.Transcoding;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTAction;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTSession;
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
 * JWTAuthorizationServerTokenServices (DefaultTokenServices)
 * Method "configure(AuthorizationServerEndpointsConfigurer endpoints)" In AuthorizationServerConfigurerAdapter.class
 */
public class JWTAuthorizationServerTokenServices implements AuthorizationServerTokenServices, Transcoding {
    private JWTSession jwtSession;

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
    public void setTranscode(Transcoder transcode) {
        this.transcode = transcode;
    }



    public JWTAuthorizationServerTokenServices(JWTSession jwtSession, TokenStore tokenStore) {
        this.jwtSession = jwtSession;
        this.tokenStore = tokenStore;
    }


    private final static DefaultOAuth2AccessToken createAccessToken(JWTSession jwtSession, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        JWTSession.JWTDate jwtDate = jwtSession.generateDate();
        Date issuedAt = jwtDate.getIssuedAt();
        Date expiresAt = jwtDate.getExpiresAt();

        JWTCreator.Builder builder = jwtSession.createBuilder();
        builder.withIssuer(issuer);
        builder.withClaim("r", System.currentTimeMillis());
        if (jwtDate.isValid()) {
            builder.withIssuedAt(issuedAt);
            builder.withExpiresAt(expiresAt);
        }
        String token = jwtSession.sign(builder);

        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(token);
        accessToken.setExpiration(expiresAt);
        accessToken.setScope(authentication.getOAuth2Request().getScope());
        return accessToken;
    }

    private final static DefaultExpiringOAuth2RefreshToken createRefreshToken(JWTSession jwtSession, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        JWTSession.JWTDate jwtDate = jwtSession.generateDate();
        Date issuedAt = jwtDate.getIssuedAt();
        Date expiresAt = jwtDate.getExpiresAt();

        JWTCreator.Builder builder = jwtSession.createBuilder();
        builder.withIssuer(issuer);
        builder.withClaim("r", System.currentTimeMillis());
        if (jwtDate.isValid()) {
            builder.withIssuedAt(issuedAt);
            builder.withExpiresAt(expiresAt);
        }
        String token = jwtSession.sign(builder);

        DefaultExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(token, expiresAt);
        return refreshToken;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        DefaultOAuth2AccessToken accessToken = createAccessToken(jwtSession, authentication);
        DefaultExpiringOAuth2RefreshToken refreshToken = createRefreshToken(jwtSession, authentication);

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
