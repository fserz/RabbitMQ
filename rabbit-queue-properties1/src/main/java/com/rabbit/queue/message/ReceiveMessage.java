package com.rabbit.queue.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceiveMessage {
    public static final String queueName = "queue.queue.1";
    @RabbitListener(queues = {queueName})
    public void receiveMsg(Message message){
        log.info("接收到的信息为：{}", new String(message.getBody()));
    }

}
