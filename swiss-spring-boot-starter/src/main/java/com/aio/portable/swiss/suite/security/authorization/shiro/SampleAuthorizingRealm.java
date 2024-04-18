package com.aio.portable.swiss.suite.security.authorization.shiro;

import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.aio.portable.swiss.suite.security.authorization.shiro.realm.impl.BearerTokenRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

//@Component
public class SampleAuthorizingRealm extends BearerTokenRealm {
    @Autowired
    JWTTemplate jwtTemplate;

    public SampleAuthorizingRealm(CredentialsMatcher credentialsMatcher) {
        super(credentialsMatcher);
    }




//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println("-------授权方法doGetAuthorizationInfo--------");
//        return super.doGetAuthorizationInfo(principalCollection);
//    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> persSet = new HashSet<>();
        persSet.add("user:show");
        persSet.add("user:admin");
        persSet.add("user:update");
        persSet.add("account:permissions");
        info.setStringPermissions(persSet);

        Set<String> rolesSet = new HashSet<>();
        rolesSet.add("teacher");
        info.setRoles(rolesSet);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        System.out.println("-------身份认证方法doGetAuthenticationInfo--------");
        return super.doGetAuthenticationInfo(authenticationToken);
    }

    @Override
    protected String getPrincipal(String token) {
        return jwtTemplate.parse(token).getIssuer();
    }


    @Override
    protected Object getStoreCredentials(Object principal) {
        return principal + "p";
    }

    @Override
    public void validate(String token) {
        jwtTemplate.validate(token);
    }
}
