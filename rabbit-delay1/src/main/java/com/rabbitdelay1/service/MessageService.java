package com.rabbitdelay1.service;

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
        Message message = MessageBuilder.withBody("test delay~~~!!!".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.delay1", "order", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }
}
