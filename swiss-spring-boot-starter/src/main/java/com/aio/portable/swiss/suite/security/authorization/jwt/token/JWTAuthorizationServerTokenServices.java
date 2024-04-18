package com.aio.portable.swiss.suite.security.authorization.jwt.token;

import com.aio.portable.swiss.suite.algorithm.adapter.Transcoder;
import com.aio.portable.swiss.suite.algorithm.adapter.classic.TranscoderBase64;
import com.aio.portable.swiss.suite.algorithm.adapter.Transcodable;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.auth0.jwt.interfaces.DecodedJWT;
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
public class JWTAuthorizationServerTokenServices implements AuthorizationServerTokenServices, Transcodable {
    private JWTTemplate jwtTemplate;

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

    private Transcoder transcoder = new TranscoderBase64();

    public void setTranscoder(Transcoder transcoder) {
        this.transcoder = transcoder;
    }

    @Override
    public Transcoder getTranscoder() {
        return transcoder;
    }

    public JWTAuthorizationServerTokenServices(JWTTemplate jwtTemplate, TokenStore tokenStore) {
        this.jwtTemplate = jwtTemplate;
        this.tokenStore = tokenStore;
    }

    static class TokenWrapper {
        private String token;
        private Date expiresAt;

        public TokenWrapper(String token, Date expiresAt) {
            this.token = token;
            this.expiresAt = expiresAt;
        }

        public String getToken() {
            return token;
        }

        public Date getExpiresAt() {
            return expiresAt;
        }
    }

    private static final TokenWrapper createToken(JWTTemplate jwtTemplate, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        String token = jwtTemplate.sign(issuer);
        DecodedJWT parse = jwtTemplate.parse(token);
        Date expiresAt = parse.getExpiresAt();
        return new TokenWrapper(token, expiresAt);
    }

    private static final DefaultOAuth2AccessToken createAccessToken(JWTTemplate jwtManager, OAuth2Authentication authentication) {
        TokenWrapper tokenWrapper = createToken(jwtManager, authentication);

        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(tokenWrapper.getToken());
        accessToken.setExpiration(tokenWrapper.getExpiresAt());
        accessToken.setScope(authentication.getOAuth2Request().getScope());
        return accessToken;
    }

    private static final DefaultExpiringOAuth2RefreshToken createRefreshToken(JWTTemplate jwtManager, OAuth2Authentication authentication) {
        TokenWrapper tokenWrapper = createToken(jwtManager, authentication);

        DefaultExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(tokenWrapper.getToken(), tokenWrapper.getExpiresAt());
        return refreshToken;
    }

    private static final DefaultOAuth2AccessToken createAccessTokenAndBindRefreshToken(JWTTemplate jwtManager, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken accessToken = createAccessToken(jwtManager, authentication);
        DefaultExpiringOAuth2RefreshToken refreshToken = createRefreshToken(jwtManager, authentication);
        accessToken.setRefreshToken(refreshToken);
        return accessToken;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        DefaultOAuth2AccessToken accessToken = createAccessTokenAndBindRefreshToken(jwtTemplate, authentication);

        tokenStore.storeAccessToken(accessToken, authentication);
        tokenStore.storeRefreshToken(accessToken.getRefreshToken(), authentication);
        return accessToken;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest) throws AuthenticationException {
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
