package com.example.rabbitmqreceive.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReceiveMessage {
    @RabbitListener(queues = {"queue.fanout.a", "queue.fanout.b"})
    public void receiveMsg(Message msg){
        byte[] body = msg.getBody();
        String msgstr = new String(body);
        log.info("接受的消息为：{}", msgstr);
    }
}
