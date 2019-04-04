package com.york.portable.park.config.mq.biz;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "receiver1")
public class RabbitReceiver2 {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("Receiver : " + msg);
    }
}
