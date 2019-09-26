package com.aio.portable.swiss.assist.ldap;

import com.aio.portable.swiss.algorithm.AlgorithmWorld;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.directory.DirContext;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public abstract class LDAPWorld {
    public final static Boolean authentication(LdapTemplate ldapTemplate, String userName, String password) {
        DirContext ctx = null;
        Boolean b;
        try {
            ctx = ldapTemplate.getContextSource().getContext(userName, password);
            b = true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            b = false;
        } finally {
            if (ctx != null)
                LdapUtils.closeContext(ctx);
        }
        return b;
    }
}
