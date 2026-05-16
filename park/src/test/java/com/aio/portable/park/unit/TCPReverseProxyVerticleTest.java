package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.net.tcp.verticle.TCPReverseProxyVerticle;
import io.vertx.core.Future;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class TCPReverseProxyVerticleTest {
    @Test
    public void foobar() {
        TCPReverseProxyVerticle verticle = new TCPReverseProxyVerticle(33333,
                String.join(".", "10", "125", "144", "176"), 32451);
        Future<String> deploy = TCPReverseProxyVerticle.deploy(verticle);
        deploy.onSuccess(c -> verticle.close());

    }
}
