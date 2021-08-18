package com.aio.portable.swiss.suite.net.tcp.proxy.annotation;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({NetworkProxyBean.class})
public @interface EnableNetworkProxy {
}
