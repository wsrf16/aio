package com.aio.portable.swiss.suite.security.authorization.shiro;

import com.aio.portable.swiss.suite.security.authorization.shiro.credential.AbstractSimpleCredentialsMatcher;
import org.apache.shiro.util.ByteSource;

import java.util.Objects;

//@Component
public class SampleSimpleCredentialsMatcher extends AbstractSimpleCredentialsMatcher {
    @Override
    public boolean match(Object enterCredentials, Object storeCredentials, ByteSource credentialsSalt) {
        boolean match = Objects.equals(enterCredentials, storeCredentials);
        match = true;
        return match;
    }
}
