package com.aio.portable.swiss.suite.security.authentication.shiro.realm.impl;

import com.aio.portable.swiss.suite.security.authentication.shiro.realm.TokenAuthorizingRealm;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.util.ByteSource;

public abstract class BearerTokenRealm extends TokenAuthorizingRealm {
//    final static String AUTHORIZATION = JWTAction.AUTHORIZATION_HEAD;

    public BearerTokenRealm(CredentialsMatcher credentialsMatcher) {
        super(credentialsMatcher);
        super.setAuthenticationTokenClass(BearerToken.class);
    }

//    public JWTRealm(CredentialsMatcher credentialsMatcher) {
//        this.setCredentialsMatcher(credentialsMatcher);
//    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && this.getAuthenticationTokenClass().isAssignableFrom(token.getClass());
    }

//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
////        System.out.println("执行了授权 doGetAuthorizationInfo");
//        String username = (String) SecurityUtils.getSubject().getPrincipal();
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        Set<String> persSet = new HashSet<>();
//        persSet.add("user:show");
//        persSet.add("user:admin");
//        persSet.add("user:update");
//        persSet.add("account:permissions");
//        info.setStringPermissions(persSet);
//
//        Set<String> rolesSet = new HashSet<>();
//        rolesSet.add("teacher");
//        info.setRoles(rolesSet);
//
//        return info;
//    }



    /**
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo info;
        Object principal;
        Object storeCredentials;
        String name = this.getName();
        ByteSource salt;


        BearerToken bearerToken = (BearerToken) authenticationToken;
        String token = bearerToken.getToken();
        principal = getPrincipal(token);
        storeCredentials = getStoreCredentials(principal);

        valid(token);

        salt = ByteSource.Util.bytes(principal.hashCode() + salt());
        info = new SimpleAuthenticationInfo(principal,
                storeCredentials,
                salt,
                name);
        return info;
    }

    protected abstract void valid(String token);

    protected abstract String getPrincipal(String token);







}