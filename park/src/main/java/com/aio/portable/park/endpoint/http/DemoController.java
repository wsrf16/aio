package com.aio.portable.park.endpoint.http;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.common.BizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogRecord;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.security.authorization.jwt.annotation.JWTAuth;
import com.aio.portable.swiss.suite.storage.cache.RedisLock;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("demo")
@LogRecord
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

    @GetMapping("sendMessage")
    public String sendMessage() {
        log.w("wwwww", new BizException(345, "summmm"));
        amqpTemplate.convertAndSend("tc.exchange", "chetao", "aaaaaaaaaaaaa");
        String msg = MessageFormat.format("现在的时间是{0}", DateTimeSugar.UnixTime.convertUnix2DateTime(DateTimeSugar.UnixTime.nowUnix()));
        amqpTemplate.convertAndSend("application-log-queue", msg);
        return msg;

    }

    @GetMapping("exception")
    public void exception() {
        int a = 1;
        if (a == 1) {
            try {
                throw new RuntimeException("1111111111");
            } catch (Exception e) {
                try {
                    throw new BizException(222, "2222222222", e);
                } catch (BizException ex) {
                    throw new BizException(333, "3333333333", ex);
                }
            }
        }

        if (new Random().nextBoolean()) {
            System.out.println("111111111");
            throw new RuntimeException("throw exception");
        } else {
            System.out.println("222222222");
            throw new BizException(BizStatusEnum.staticInvalid().getCode(), "throw bizexception");
        }
    }

    @GetMapping("toNumber")
    public int toNumber(@RequestParam String s) {
        return Integer.valueOf(s);
    }

    @GetMapping("date")
    public Date date() {
        return new Date();
    }

    @GetMapping("auth")
    @JWTAuth
    public Date auth() {
        return new Date();
    }

    @GetMapping("ok")
    @LogRecord
    public ResponseWrapper<Object> ok() {
        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 100000; i++) {
//            list.add("aaa");
//        }
        return ResponseWrappers.succeed(list);

//        return ResponseWrapperUtils.build(0, list);
    }

    @GetMapping("input")
    public String input(String value) {
        return value;
    }

    @GetMapping("echo")
    public ResponseWrapper<String> echo(String value) {
        return ResponseWrappers.succeed(value);
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

    @GetMapping("multiple")
    public String multiple(@ModelAttribute UserInfoEntity entity) {
        String identify;

        identify = redisLock.tryLock("robot", 60000, 5000);
        redisLock.releaseLock("robot");

        identify = redisLock.tryLock("robot", 10000L, 10000L);
        redisLock.releaseLock("robot");

        return MessageFormat.format("lock : {0}", identify);
    }

    @PostMapping("post")
    public String post(@RequestBody UserInfoEntity entity) {
        String identify = "aaa";

//        identify = redisLock.tryLock("robot", 60000, 5000);
//        redisLock.releaseLock("robot");
//
//        identify = redisLock.tryLock("robot", 10000L, 10000L);
//        redisLock.releaseLock("robot");

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
