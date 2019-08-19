package com.york.portable.park.unit.swiss;

import com.york.portable.swiss.bean.serializer.SerializerEnum;
import com.york.portable.swiss.net.http.HttpSwift;
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
import java.util.ArrayList;
import java.util.List;

@TestComponent
public class HttpClientUtilTest {

    @Test
    public void sample() throws IOException {
        class People {
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private String name;
        }

        People people = new People();
        people.setName("John");
        String url = "http://www.baidu.com";
        HttpHost httpProxy = new HttpHost("127.0.0.1", 8888, "http");
        RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
        HttpSwift.getSerializer().setSerializer(SerializerEnum.SERIALIZE_JACKXML);
        StringEntity entity = HttpSwift.buildJsonObjectEntity(people, "utf-8");
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
