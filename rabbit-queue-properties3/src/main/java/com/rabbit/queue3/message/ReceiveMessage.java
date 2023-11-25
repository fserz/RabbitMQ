package com.rabbit.queue3.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceiveMessage {
    public static final String queueName = "queue.properties.3";
    @RabbitListener(queues = {queueName})
    public void receiveMsg1(Message message){
        log.info("1 接收到的信息为：{}", new String(message.getBody()));
    }
    @RabbitListener(queues = {queueName})
    public void receiveMsg2(Message message){
        log.info("2 接收到的信息为：{}", new String(message.getBody()));
    }
    @RabbitListener(queues = {queueName})
    public void receiveMsg3(Message message){
        log.info("3 接收到的信息为：{}", new String(message.getBody()));
    }


}
