package com.aio.portable.swiss.suite.net.tcp.http;

import com.aio.portable.swiss.suite.net.tcp.http.factory.HttpRequestFactoryConfig;
import com.aio.portable.swiss.suite.net.tcp.http.factory.component.HttpComponentsClientHttpRequestFactoryBuilder;
import com.aio.portable.swiss.suite.net.tcp.http.factory.simple.SkipSSLSimpleClientHttpRequestFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class HttpRequestFactory {
    public static final SimpleClientHttpRequestFactory buildProxySimpleClientHttpRequestFactory(String host, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setProxy(proxy);

        return factory;
    }

    public static final SkipSSLSimpleClientHttpRequestFactory buildSkipSSLSimpleClientHttpRequestFactory() {
        SkipSSLSimpleClientHttpRequestFactory factory = new SkipSSLSimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(10000);
        return factory;
    }




    // http://www.it1352.com/215149.html
    public static final HttpComponentsClientHttpRequestFactory buildProxyHttpComponentsClientHttpRequestFactory(String host, int port, String username, String password) {
        HttpRequestFactoryConfig config = new HttpRequestFactoryConfig();
        config.setHost(host);
        config.setPort(port);
        config.setUsername(username);
        config.setPassword(password);

        HttpComponentsClientHttpRequestFactory factory = HttpComponentsClientHttpRequestFactoryBuilder.build(config);
        return factory;
    }

    public static final HttpComponentsClientHttpRequestFactory buildHttpComponentsClientHttpRequestFactory() {
        HttpRequestFactoryConfig config = new HttpRequestFactoryConfig();

        HttpComponentsClientHttpRequestFactory factory = HttpComponentsClientHttpRequestFactoryBuilder.build(config);
        return factory;
    }

    public static final HttpComponentsClientHttpRequestFactory buildSkipSSLHttpComponentsClientHttpRequestFactory() {
        HttpRequestFactoryConfig config = new HttpRequestFactoryConfig();
        config.setSkipSSL(true);

        HttpComponentsClientHttpRequestFactory factory = HttpComponentsClientHttpRequestFactoryBuilder.build(config);
        return factory;
    }

    public static final HttpComponentsClientHttpRequestFactory buildHttpComponentsClientHttpRequestFactory(boolean skipSSL, String host, int port, String username, String password) {
        HttpRequestFactoryConfig config = new HttpRequestFactoryConfig();
        config.setHost(host);
        config.setPort(port);
        config.setUsername(username);
        config.setPassword(password);
        config.setSkipSSL(skipSSL);

        HttpComponentsClientHttpRequestFactory factory = HttpComponentsClientHttpRequestFactoryBuilder.build(config);
        return factory;
    }
}
