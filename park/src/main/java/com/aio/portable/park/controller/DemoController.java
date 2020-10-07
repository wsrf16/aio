package com.aio.portable.park.controller;

import com.aio.portable.park.common.BizStatusEnum;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapperUtils;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.storage.cache.RedisLock;
import com.aio.portable.swiss.suite.log.annotation.LogMarker;
import com.aio.portable.swiss.sugar.DateTimeSugar;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustStrategy;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("demo")
@LogMarker
public class DemoController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    private LogHub log = AppLogHubFactory.staticBuild();

    @GetMapping("log")
    public String log() {
        request = request;
        response = response;

        log.i("ok");
        return "ok";
    }

    @Autowired(required = false)
    RedisLock redisLock;

    @Autowired(required = false)
    AmqpTemplate amqpTemplate;

    @GetMapping("mqsend")
    public String mqsend() {
        amqpTemplate.convertAndSend("tc.exchange","taoche", "aaaaaaaaaaaaa");
        String msg = MessageFormat.format("现在的时间是{0}", DateTimeSugar.UnixTime.convertUnix2DateTime(DateTimeSugar.UnixTime.nowUnix()));
        amqpTemplate.convertAndSend("application-log-queue", msg);
        return msg;

    }

    @GetMapping("throwe")
    public void throwe() {
        if (new Random().nextBoolean())
            throw new RuntimeException("throw exception");
        else
            throw new BizException(BizStatusEnum.staticInvalid().getCode(), "throw bizexception");
    }

    @GetMapping("date")
    public Date date() {
        return new Date();
    }

    @GetMapping("ok")
    @LogMarker
    public ResponseWrapper<String> ok() {
        return ResponseWrapperUtils.build(0, "oookkk");
    }

    @GetMapping("lock")
    public String lock() {
        String identify;

        identify = redisLock.tryLock("robot", 60000, 5000);
        redisLock.releaseLock("robot");

        identify = redisLock.tryLock("robot", 10000L, 10000L);
        redisLock.releaseLock("robot");

        return MessageFormat.format("lock : {0}", identify);
    }


//    @Bean
//    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                .loadTrustMaterial(null, acceptingTrustStrategy)
//                .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(csf)
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                new HttpComponentsClientHttpRequestFactory();
//
//        requestFactory.setHttpClient(httpClient);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        return restTemplate;
//    }
}
