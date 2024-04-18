package com.aio.portable.swiss.suite.security.authorization.shiro.credential;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.ByteSource;

import java.util.Iterator;
import java.util.List;


/**
 * 凭证匹配 : 指定 加密算法,散列次数
 */
//@Component
public abstract class AbstractSimpleCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        boolean match = false;
        Iterator<Object> iterator = authenticationInfo.getPrincipals().iterator();
        List<Object> list = CollectionSugar.toList(iterator);
        Object primaryPrincipal = authenticationInfo.getPrincipals().getPrimaryPrincipal();
        Object principal = list.get(0);
        Object storeCredentials = authenticationInfo.getCredentials();
        ByteSource credentialsSalt = null;
        if (authenticationInfo instanceof SimpleAuthenticationInfo) {
            credentialsSalt = ((SimpleAuthenticationInfo) authenticationInfo).getCredentialsSalt();
        }

        if (authenticationToken instanceof BearerToken) {
            BearerToken bearerToken = (BearerToken) authenticationToken;
            String token = bearerToken.getToken();
            match = match(null, storeCredentials, credentialsSalt);
        } else if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
            String username = usernamePasswordToken.getUsername();
            String password = new String(usernamePasswordToken.getPassword());
            match = match(password, storeCredentials, credentialsSalt);
        } else {
            Object tokenPrincipal = authenticationToken.getPrincipal();
            Object tokenCredentials = authenticationToken.getCredentials();
            match = match(tokenCredentials, storeCredentials, credentialsSalt);
        }

        return match;
    }

    public abstract boolean match(Object enterCredentials, Object storeCredentials, ByteSource credentialsSalt);
}
