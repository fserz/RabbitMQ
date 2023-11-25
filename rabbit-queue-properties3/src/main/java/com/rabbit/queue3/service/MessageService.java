package com.rabbit.queue3.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${my.exchangeName}")
    private String exchangeName;
    public void sendMsg(){
        for (int i = 0; i < 500; i++) {
            String strMsg = i +" queue properties test!!! " ;
            Message message = MessageBuilder.withBody(strMsg.getBytes()).build();
            rabbitTemplate.convertAndSend(exchangeName, "info", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
    }
}
