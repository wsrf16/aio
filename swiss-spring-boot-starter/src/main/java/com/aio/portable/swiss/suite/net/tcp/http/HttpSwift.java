package com.aio.portable.swiss.suite.net.tcp.http;

import com.aio.portable.swiss.suite.bean.serializer.SerializerAdapterFactory;
import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;

public class HttpSwift {
    public CookieStore cookieStore = new BasicCookieStore();
    public CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    // 配置超时时间
    RequestConfig requestConfig = RequestConfig
            .custom()
            .setConnectTimeout(1000)
            .setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000)
            .setRedirectsEnabled(true)
            .build();

//    public static final HttpClientBuilder buildHttpClient() {
//        HttpClientBuilder factory = HttpClients.custom();
//        return factory;
//    }
//
//    public static final HttpClientBuilder setCookieStore(HttpClientBuilder factory, CookieStore cookieStore) {
//        factory = factory.setDefaultCookieStore(cookieStore);
//        return factory;
//    }
//
//    public static final HttpClientBuilder setProxy(HttpClientBuilder factory, HttpHost httpHost) {
//        factory = factory.setProxy(httpHost);
//        return factory;
//    }
//
//    public static final HttpClientBuilder setProxy(HttpClientBuilder factory, String hostname, int port, String scheme) {
//        HttpHost httpHost = new HttpHost(hostname, port, scheme);
//        factory = factory.setProxy(httpHost);
//        return factory;
//    }
//
//    public static final HttpHost buildHostProxy(String hostname, int port, String scheme) {
//        HttpHost proxy = new HttpHost(hostname, port, scheme);
//        return proxy;
//    }


    protected static StringSerializerAdapter stringSerializerAdapter;

    public static final StringSerializerAdapter getSerializerAdapter() {
        return stringSerializerAdapter;
    }

    public static final void setSerializerAdapter(StringSerializerAdapter stringSerializerAdapter) {
        HttpSwift.stringSerializerAdapter = stringSerializerAdapter;
    }

    static {
//        serializer = new SerializerSelector()::serialize;
        stringSerializerAdapter = SerializerAdapterFactory.buildJackson();
    }

    public static final UrlEncodedFormEntity buildUrlEncodedFormEntity(List<BasicNameValuePair> list, String charset) throws UnsupportedEncodingException {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
        return entity;
    }

    public static final StringEntity buildJsonEntity(String json, Charset charset) {
        StringEntity entity = new StringEntity(json, charset);
        entity.setContentType("application/json");
        return entity;
    }

    public static final StringEntity buildJsonObjectEntity(Object body, Charset charset) {
        StringEntity entity = new StringEntity(stringSerializerAdapter.serialize(body), charset);
        entity.setContentType("application/json");
        return entity;
    }

    public static final StringEntity buildStringEntity(String body, Charset charset) {
        StringEntity entity = new StringEntity(body, charset);
        // entity.setContentEncoding("UTF-8");
        return entity;
    }

    public static final HttpPost buildPost(String url, RequestConfig config, AbstractHttpEntity entity) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        if (entity != null)
            httpPost.setEntity(entity);
        return httpPost;
    }

    public static final HttpPost buildPost(String url, RequestConfig config, AbstractHttpEntity entity, Header[] headers) {
        HttpPost httpPost = new HttpPost(url);
        if (config != null)
            httpPost.setConfig(config);
        if (entity != null)
            httpPost.setEntity(entity);
        if (headers != null)
            httpPost.setHeaders(headers);
        return httpPost;
    }

    public static final HttpGet buildGet(String url) throws URISyntaxException {
        HttpGet httpGet = new HttpGet(url);
        return httpGet;
    }

    public static final HttpGet buildGet(String url, RequestConfig config) throws URISyntaxException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        return httpGet;
    }

    public static final HttpGet buildGet(String url, RequestConfig config, List<BasicNameValuePair> params, String charset) throws URISyntaxException {
        String query = URLEncodedUtils.format(params, charset);
        url = MessageFormat.format("{0}?{1}", url, query);
        URI uri = new URI(url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        return httpGet;
    }

    public static final String getResult(HttpResponse response, Charset charset) throws IOException {
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(entity, charset);
        } else {
            return EntityUtils.toString(entity, charset);
        }
    }

    public static final String getResult(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(entity);
        } else {
            return EntityUtils.toString(entity);
        }
    }





}