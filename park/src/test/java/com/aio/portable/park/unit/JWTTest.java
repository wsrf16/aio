package com.aio.portable.park.unit;

import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTCache;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;

@TestComponent
public class JWTTest {
    @Autowired
    JWTTemplate jwtTemplate;

    @Test
    public void foobar() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("a", ResponseWrappers.succeed());
        }};
        JWTCache jwtCache = new JWTCache(jwtTemplate);
        String token = jwtCache.set(map);
        Map<String, Object> stringObjectMap = jwtCache.get(token);

    }
}
