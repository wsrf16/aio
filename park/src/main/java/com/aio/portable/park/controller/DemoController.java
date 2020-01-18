package com.aio.portable.park.controller;

import com.aio.portable.park.config.LogFactory;
import com.aio.portable.swiss.hamlet.model.ResponseWrapper;
import com.aio.portable.swiss.structure.cache.RedisLock;
import com.aio.portable.swiss.structure.log.annotation.LogMarker;
import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

//    LogHub log = LogFactory.singletonInstance().build();
    @GetMapping("log")
    public String log() {
        request = request;
        response = response;

        LogFactory.singletonInstance().build(this.getClass()).d("this is debug.");
        return "ok";
    }

    @Autowired(required = false)
    RedisLock redisLock;

    @Autowired(required = false)
    AmqpTemplate amqpTemplate;
    @GetMapping("mqsend")
    public String mqsend() {
        amqpTemplate.convertAndSend("tc.exchange","taoche", "aaaaaaaaaaaaa");
        amqpTemplate.convertAndSend("tc.exchange", "taoche", "bbbbbbbb");

        String msg = MessageFormat.format("现在的时间是{0}", DateTimeSugar.UnixTime.convertUnix2DateTime(DateTimeSugar.UnixTime.nowUnix()));
        amqpTemplate.convertAndSend("application-log-queue", msg);
        return msg;
    }

    @GetMapping("date")
    public Date date() {
        return new Date();
    }

    @GetMapping("ok")
    @LogMarker
    public ResponseWrapper<String> ok() {
        return ResponseWrapper.build(0, "oookkk");
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

}
