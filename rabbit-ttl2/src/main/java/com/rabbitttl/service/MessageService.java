package com.rabbitttl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendMsg(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("35000");
        Message message = MessageBuilder.withBody("ttl test!!!".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.ttl.a", "info", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }
}
