package com.aio.portable.park.unit;

import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.auth0.jwt.interfaces.DecodedJWT;
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
            put("response", ResponseWrappers.build(123, "操作完成"));
        }};
        String token = jwtTemplate.sign(map);
        DecodedJWT parse = jwtTemplate.parse(token);
        Map<String, Object> stringObjectMap = jwtTemplate.parseToMap(token);
        String i = jwtTemplate.parseTo(token, "response");

    }
}
