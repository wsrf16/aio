package com.aio.portable.swiss.suite.net.tcp.http.factory.component;

import com.aio.portable.swiss.suite.net.tcp.http.factory.HttpRequestFactoryConfig;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class HttpComponentsClientHttpRequestFactoryBuilder {
    public static final HttpComponentsClientHttpRequestFactory build(HttpRequestFactoryConfig config) {
        CloseableHttpClient httpClient = httpClient(config);
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return factory;
    }

//    public static final HttpComponentsClientHttpRequestFactory buildProxyFactory(String host, int port, String username, String password) {
//        HttpRequestFactoryConfig config = new HttpRequestFactoryConfig();
//        config.setHost(host);
//        config.setPort(port);
//        config.setUsername(username);
//        config.setPassword(password);
//
//        return build(config);
//    }

//    public static final HttpComponentsClientHttpRequestFactory buildSkipSSLFactory() {
//        HttpRequestFactoryConfig config = new HttpRequestFactoryConfig();
//        config.setSkipSSL(true);
//
//        return build(config);
//    }

    private static final CloseableHttpClient httpClient(HttpRequestFactoryConfig config) {
        HttpHost proxy = null;
        if (config.getProxyHost() != null && config.getProxyPort() != null) {
            String host = config.getProxyHost();
            Integer port = config.getProxyPort();
            proxy = new HttpHost(host, port);
        }


        AuthScope authScope = null;
        if (config.getHost() != null && config.getPort() != null) {
            String host = config.getHost();
            Integer port = config.getPort();
            authScope = new AuthScope(host, port);
        }
        Credentials credentials = null;
        if (config.getUsername() != null && config.getPassword() != null) {
            String username = config.getUsername();
            String password = config.getPassword();
            credentials = new UsernamePasswordCredentials(username, password);
        }

        CredentialsProvider credentialsProvider = null;
        if (authScope != null && credentials != null) {
            credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                    authScope,
                    credentials);
        }



        HttpClientBuilder builder = HttpClients.custom();
        if (proxy != null)
            builder.setProxy(proxy);
        if (credentialsProvider != null)
            builder.setDefaultCredentialsProvider(credentialsProvider);
//        if (config.getSkipSSL() == true)
//            builder.setSSLSocketFactory(createSSlConnectionSocketFactory());
        if (config.getRetryCount() > 0)
            builder.setRetryHandler(new DefaultHttpRequestRetryHandler(config.getRetryCount(),true));

        PoolingHttpClientConnectionManager connectionManager = createConnectionManager(config.getMaxTotal(), config.getDefaultMaxPerRoute(), config.getSkipSSL());
        CloseableHttpClient httpClient =
                builder.setDefaultRequestConfig(createRequestConfig(config.getSocketTimeout(),
                        config.getConnectTimeout(),
                        config.getConnectionRequestTimeout()))
                        .setConnectionManager(connectionManager)
//                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
//                        .setSSLContext(sslContext)
                        .build();
        return httpClient;
    }

    private static final RequestConfig createRequestConfig(Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return requestConfig;
    }

    private static final SSLContext createSkipSSLContext() {
        try {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    private static final SSLConnectionSocketFactory createSkipSSlConnectionSocketFactory() {
        SSLContext sslContext = createSkipSSLContext();
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        return connectionSocketFactory;
    }

    private static PoolingHttpClientConnectionManager createConnectionManager(Integer maxTotal, Integer defaultMaxPerRoute, Boolean skipSSL) {
        RegistryBuilder<ConnectionSocketFactory> builder = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory());
        if (skipSSL == true)
            builder.register("https", createSkipSSlConnectionSocketFactory());
        else
            builder.register("https", SSLConnectionSocketFactory.getSocketFactory());
        Registry<ConnectionSocketFactory> registry = builder.build();

//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", sslConnectionSocketFactory)
//                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        if (maxTotal != null)
            connectionManager.setMaxTotal(maxTotal);
        if (defaultMaxPerRoute != null)
            connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return connectionManager;
    }

}
