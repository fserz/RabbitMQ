package com.rabbitttl.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void sendMsg(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("10000");
        Message message = MessageBuilder.withBody("test ttl ccc233".getBytes()).andProperties(messageProperties).build();
        Message message2 = MessageBuilder.withBody("test ttl bbb233".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.ttl.b", "info", message);
        rabbitTemplate.convertAndSend("exchange.ttl.b", "info", message2);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }
}
