package com.aio.portable.park.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Component
// 名称根据实际监听的队列修改
@RabbitListener(queues = "application-log-queue")
public class RabbitReceiver {
    @RabbitHandler
    public void process(String msg) {
//        System.out.println("Receiver  : " + msg);
    }
}
