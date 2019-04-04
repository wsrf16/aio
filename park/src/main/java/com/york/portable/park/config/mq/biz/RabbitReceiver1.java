package com.york.portable.park.config.mq.biz;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
@RabbitListener(queues = "application-log-queue")
public class RabbitReceiver1 {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("Receiver  : " + msg);
    }
}
