package com.aio.portable.swiss.suite.security.authorization.jwt.token;

import com.aio.portable.swiss.suite.algorithm.adapter.Transcoder;
import com.aio.portable.swiss.suite.algorithm.adapter.classic.TranscoderBase64;
import com.aio.portable.swiss.suite.algorithm.adapter.Transcodable;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTExpiredDate;
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

    static class TokenCase {
        private String token;
        private JWTExpiredDate jwtExpiredDate;

        public String getToken() {
            return token;
        }

        public JWTExpiredDate getJwtExpiredDate() {
            return jwtExpiredDate;
        }

        public TokenCase(String token, JWTExpiredDate jwtExpiredDate) {
            this.token = token;
            this.jwtExpiredDate = jwtExpiredDate;
        }
    }

    private final static TokenCase createToken(JWTTemplate jwtManager, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        JWTExpiredDate validDate = jwtManager.getJwtConfig().createValidDate();
        Date issuedAt = validDate.getIssuedAt();
        Date expiresAt = validDate.getExpiresAt();

        JWTCreator.Builder builder = jwtManager.createBuilder();
        builder.withIssuer(issuer);
        if (validDate.isValid()) {
            builder.withIssuedAt(issuedAt);
            builder.withExpiresAt(expiresAt);
        }
        String token = jwtManager.sign(builder);
        return new TokenCase(token, validDate);
    }

    private final static DefaultOAuth2AccessToken createAccessToken(JWTTemplate jwtManager, OAuth2Authentication authentication) {
        TokenCase tokenCase = createToken(jwtManager, authentication);

        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(tokenCase.getToken());
        accessToken.setExpiration(tokenCase.getJwtExpiredDate().getExpiresAt());
        accessToken.setScope(authentication.getOAuth2Request().getScope());
        return accessToken;
    }

    private final static DefaultExpiringOAuth2RefreshToken createRefreshToken(JWTTemplate jwtManager, OAuth2Authentication authentication) {
        TokenCase tokenCase = createToken(jwtManager, authentication);

        DefaultExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(tokenCase.getToken(), tokenCase.getJwtExpiredDate().getExpiresAt());
        return refreshToken;
    }

    private final static DefaultOAuth2AccessToken createAccessTokenAndBindRefreshToken(JWTTemplate jwtManager, OAuth2Authentication authentication) {
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
