package com.aio.portable.park.config.mq.biz;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Component
@RabbitListener(queues = "application-log-queue")
public class RabbitReceiver1 {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("Receiver  : " + msg);
    }
}
