package com.aio.portable.swiss.suite.security.authentication.shiro.realm;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthorizingRealm;

public abstract class TokenAuthorizingRealm extends AuthorizingRealm {
    protected String SALT = "__salt__";

    protected String salt() {
        return SALT;
    }

    public TokenAuthorizingRealm(CredentialsMatcher credentialsMatcher) {
        this.setCredentialsMatcher(credentialsMatcher);
        this.setAuthenticationTokenClass(AuthenticationToken.class);
    }

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


//    public abstract void valid(Object principal, Object credentials);
    /**
     * 获取即将需要认证的信息
     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
////        SecurityUtils.getSubject().getSession().setAttribute("user", "user");
////        System.out.println("-------身份认证方法--------");
//        SimpleAuthenticationInfo info;
//        Object principal;
//        Object credentials;
//        String name = this.getName();
//        ByteSource salt;
//        if (authenticationToken instanceof BearerToken) {
//            BearerToken bearerToken = (BearerToken) authenticationToken;
//            String token = bearerToken.getToken();
//            principal = token;
//            credentials = token;
//
//            valid(token, token);
//        } else if (authenticationToken instanceof UsernamePasswordToken) {
//            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
//            principal = usernamePasswordToken.getUsername();
//            credentials = new String(usernamePasswordToken.getPassword());
//
//            valid(principal, credentials);
//        } else {
//            principal = authenticationToken.getPrincipal();
//            credentials = authenticationToken.getCredentials();
//
//            valid(principal, credentials);
//        }
//
//        salt = ByteSource.Util.bytes(principal.hashCode() + salt());
//        info = new SimpleAuthenticationInfo(principal,
//                credentials,
//                salt,
//                name);
//        return info;
//    }



//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        SimpleAuthenticationInfo info;
//        Object principal;
//        Object storeCredentials;
//        String name = this.getName();
//        ByteSource salt;
//
//        principal = authenticationToken.getPrincipal();
//        storeCredentials = getStoreCredentials(principal);
//
//        valid(principal, storeCredentials);
//
//        salt = ByteSource.Util.bytes(principal.hashCode() + salt());
//        info = new SimpleAuthenticationInfo(principal,
//                storeCredentials,
//                salt,
//                name);
//        return info;
//    }

    protected abstract Object getStoreCredentials(Object principal);





}