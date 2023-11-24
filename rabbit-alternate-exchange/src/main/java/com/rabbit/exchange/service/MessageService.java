package com.rabbit.exchange.service;

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
    @Value("${my.exchangeNormalName}")
    private String exchangeName;
    public void sendMsg(){
        Message message = MessageBuilder.withBody("exchange alternate test222!".getBytes()).build();
        rabbitTemplate.convertAndSend(exchangeName, "info", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }
}
