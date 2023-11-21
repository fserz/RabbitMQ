package com.rabbitdlx4.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendMsg(){
        for (int i = 0; i < 7; i++) {
            String str = "hello world" + i;
            Message message = MessageBuilder.withBody(str.getBytes()).build();
            rabbitTemplate.convertAndSend("exchange.normal.4","order", message);
        }
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }
}
