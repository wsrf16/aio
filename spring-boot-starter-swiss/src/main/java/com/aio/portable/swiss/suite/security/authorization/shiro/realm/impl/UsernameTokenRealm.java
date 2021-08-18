package com.aio.portable.swiss.suite.security.authorization.shiro.realm.impl;

import com.aio.portable.swiss.suite.security.authorization.shiro.realm.TokenAuthorizingRealm;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.util.ByteSource;

public abstract class UsernameTokenRealm extends TokenAuthorizingRealm {
//    final static String AUTHORIZATION = JWTAction.AUTHORIZATION_HEAD;

    public UsernameTokenRealm(CredentialsMatcher credentialsMatcher) {
        super(credentialsMatcher);
        super.setAuthenticationTokenClass(UsernamePasswordToken.class);
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
//        System.out.println("执行了授权 doGetAuthorizationInfo");
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
        //将用户信息放在session
//        SecurityUtils.getSubject().getSession().setAttribute("user", "user");
//        System.out.println("-------身份认证方法--------");
        SimpleAuthenticationInfo info;
        Object principal;
        Object storeCredentials;
        String name = this.getName();
        ByteSource salt;

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        principal = usernamePasswordToken.getUsername();
//        storeCredentials = new String(usernamePasswordToken.getPassword());
        storeCredentials = getStoreCredentials(principal);;

        salt = ByteSource.Util.bytes(principal.hashCode() + salt());
        info = new SimpleAuthenticationInfo(principal,
                storeCredentials,
                salt,
                name);
        return info;
    }

//    protected abstract String getPassword(String principal);








}