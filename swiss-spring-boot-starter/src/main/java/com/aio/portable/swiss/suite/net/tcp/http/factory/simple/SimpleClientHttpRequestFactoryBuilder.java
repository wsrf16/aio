//package com.aio.portable.swiss.suite.net.tcp.http.factory.simple;
//
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//
//import java.net.InetSocketAddress;
//import java.net.Proxy;
//
//public class SimpleClientHttpRequestFactoryBuilder {
//    public static final SimpleClientHttpRequestFactory buildProxySimpleClientHttpRequestFactory(String host, int port) {
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
//
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setProxy(proxy);
//
//        return factory;
//    }
//
//    public static final SkipSSLSimpleClientHttpRequestFactory buildSkipSSLClientHttpRequestFactory() {
//        SkipSSLSimpleClientHttpRequestFactory factory = new SkipSSLSimpleClientHttpRequestFactory();
//        factory.setReadTimeout(5000);
//        factory.setConnectTimeout(10000);
//        return factory;
//    }
//}
