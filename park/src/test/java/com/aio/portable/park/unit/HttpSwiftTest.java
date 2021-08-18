package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.bean.serializer.SerializerConverters;
import com.aio.portable.swiss.suite.net.tcp.http.HttpSwift;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@TestComponent
public class HttpSwiftTest {
    static class People {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }

    @Test
    public void foobar() throws IOException {
        People people = new People();
        people.setName("John");
        String url = "http://www.baidu.com";
        HttpHost httpProxy = new HttpHost("127.0.0.1", 8888, "http");
        RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
        HttpSwift.setSerializer(new SerializerConverters.JacksonConverter());
        StringEntity entity = HttpSwift.buildJsonObjectEntity(people, StandardCharsets.UTF_8);
        Header[] headers = newHeaders("sign");

        HttpPost httpPost = HttpSwift.buildPost(url, config, entity, headers);
        CloseableHttpClient client;
        {
            client = HttpClientBuilder.create().build();
            client = HttpClients.createDefault();
        }
        CloseableHttpResponse response = client.execute(httpPost);
        String result = HttpSwift.getResult(response);
        System.out.println(result);
    }

    private static Header[] newHeaders(String sign) {
        List<BasicHeader> headers = new ArrayList<>();
        BasicHeader header2 = new BasicHeader("sign", sign);
        BasicHeader header1 = new BasicHeader("Accept-Language", "zh-cn");
        headers.add(header1);
        headers.add(header2);
        return headers.toArray(new BasicHeader[0]);
    }
}
