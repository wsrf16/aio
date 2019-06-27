package com.york.portable.park.config.mq.biz;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
@RabbitListener(queues = "receiver2")
public class RabbitReceiver2 {
    @RabbitHandler
    public void process(String msg, Channel channel, Message message) throws IOException {
        System.out.println("Receiver : " + msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
            //丢弃这条消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        }
    }
}
