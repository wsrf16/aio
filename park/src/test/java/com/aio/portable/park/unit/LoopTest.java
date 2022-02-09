package com.aio.portable.park.unit;

import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.sugar.Looper;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@TestComponent
public class LoopTest {

    @Test
    public void foobar() {
        Looper looper = new Looper(() -> System.out.println("map"), 5, TimeUnit.SECONDS);
        try {
            looper.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
}
