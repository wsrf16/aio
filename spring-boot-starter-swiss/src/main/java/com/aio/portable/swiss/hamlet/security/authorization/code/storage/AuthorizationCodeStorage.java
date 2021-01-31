package com.aio.portable.swiss.hamlet.security.authorization.code.storage;

import com.aio.portable.swiss.hamlet.security.authorization.code.object.AuthorizationCodeObject;

public interface AuthorizationCodeStorage {
    void set(String code, AuthorizationCodeObject authorizationCode);

    AuthorizationCodeObject remove(String code);

    AuthorizationCodeObject get(String code);
}
