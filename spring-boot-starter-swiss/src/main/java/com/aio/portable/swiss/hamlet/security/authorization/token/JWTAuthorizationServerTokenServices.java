package com.aio.portable.swiss.hamlet.security.authorization.token;

import com.aio.portable.swiss.autoconfigure.properties.JWTClaims;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTAction;
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
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Date;

/**
 * JWTAuthorizationServerTokenServices
 * Method "configure(AuthorizationServerEndpointsConfigurer endpoints)" In AuthorizationServerConfigurerAdapter.class
 */
public class JWTAuthorizationServerTokenServices implements AuthorizationServerTokenServices {
    @Autowired
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
    @Autowired
    private TokenStore tokenStore = jwtTokenStore();

    private JwtTokenStore jwtTokenStore() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");
        return new JwtTokenStore(converter);
    }


    private String create(JWTClaims jwtClaims, String issuer) {
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(issuer);
        builder.withClaim("r", System.currentTimeMillis());
        builder.withIssuedAt(jwtClaims.getIssuedAt());
        builder.withExpiresAt(jwtClaims.getExpiresAt());

        String tokenWord = jwtAction.sign(builder);
        return tokenWord;
    }

    private DefaultOAuth2AccessToken createAccessToken(JWTClaims jwtClaims, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        String value = create(jwtClaims, issuer);
        Date expiresAt = jwtClaims.getExpiresAt();
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(value);
        accessToken.setExpiration(expiresAt);
        accessToken.setScope(authentication.getOAuth2Request().getScope());
        return accessToken;
    }

    private DefaultExpiringOAuth2RefreshToken createRefreshToken(JWTClaims jwtClaims, OAuth2Authentication authentication) {
        String issuer = authentication.getUserAuthentication().getName();
        String value = create(jwtClaims, issuer);
        Date expiresAt = jwtClaims.getExpiresAt();
        DefaultExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(value, expiresAt);

        return refreshToken;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        JWTClaims accessTokenJWTClaims = jwtAction.toJWTClaims();
        DefaultOAuth2AccessToken accessToken = createAccessToken(accessTokenJWTClaims, authentication);
        JWTClaims refreshTokenJWTClaims = jwtAction.toJWTClaims();
        DefaultExpiringOAuth2RefreshToken refreshToken = createRefreshToken(refreshTokenJWTClaims, authentication);

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
