package com.aio.portable.park.controller;

//import com.aio.portable.park.common.log.InjectedBaseLogger;
import com.aio.portable.park.config.LogFactory;
import com.aio.portable.swiss.structure.cache.RedisLock;
import com.aio.portable.swiss.hamlet.model.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

@RestController
@RequestMapping("demo")
public class DemoController {// extends InjectedBaseLogger {
    @GetMapping("loganno")
    public String loganno() {
        LogFactory.singletonInstance().build(this.getClass()).d("debug");
        return "ok";
    }

    @Autowired(required = false)
    RedisLock redisLock;

//    @GetMapping("foo")
//    public ResponseWrapper foo() {
//        logger.info("log1", "some information");
//        slf4jHubFactory.build(this.getClass()).info("log2", "some information");
//        return ResponseWrapper.build(BizStatusEnum.VERIFICATION.getCode(), "foo接口未进行授权");
//    }

//    @Autowired
//    AmqpTemplate amqpTemplate;
//    @GetMapping("mqsend")
//    public void mqsend() {
//        amqpTemplate.convertAndSend("tc.exchange","", "aaaaaaaaaaaaa");
//        amqpTemplate.convertAndSend("tc.exchange", "taoche", "bbbbbbbb");
//    }

    @GetMapping("date")
    public Date date() {
        return new Date();
    }

    @GetMapping("ok")
    public ResponseWrapper<String> ok() {
        return ResponseWrapper.build(0, "oookkk");
    }

//    @GetMapping("log")
//    public String log() {
//        String msg = MessageFormat.format("现在的时间是{0}", DateTimeUtils.UnixTime.convertUnix2DateTime(DateTimeUtils.UnixTime.nowUnix()));
//        logger.info("log", msg);
//        return msg;
//    }

//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    @GetMapping("mq")
//    public String mq() {
//        String msg = MessageFormat.format("现在的时间是{0}", DateTimeUtils.UnixTime.convertUnix2DateTime(DateTimeUtils.UnixTime.nowUnix()));
//        rabbitTemplate.convertAndSend("application-log-queue", msg);
//        return msg;
//    }

    @GetMapping("lock")
    public String lock() {
        String identify;

        identify = redisLock.lock("robot", 60000, 5000);
        redisLock.releaseLock("robot");

        identify = redisLock.lock("robot", 10000L, 10000L);
        redisLock.releaseLock("robot");

        return MessageFormat.format("lock : {0}", identify);
    }



    @PostMapping("/upload2data")
    @ResponseBody
    public String uploadapp(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        int location = StringUtils.lastIndexOf(fileName, "-");
        String proj = fileName.substring(0, location);
//        String env = fileName.substring(location + 1).replace(".tar.gz", "");

        String target = MessageFormat.format("/data1/service/{0}/{1}" , proj , fileName);
//        String cmd = MessageFormat.format("/data1/service/{0}/qpublish.sh {1}" , proj , env);
        file.transferTo(new File(target));
        return target;
    }

}
