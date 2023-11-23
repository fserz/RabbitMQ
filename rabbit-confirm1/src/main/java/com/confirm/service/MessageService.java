package com.confirm.service;

import com.confirm.config.MyConfirmCallback;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MyConfirmCallback confirmCallback;
    @PostConstruct//构造方法后执行，相当于初始化
    public void init(){
        rabbitTemplate.setConfirmCallback(confirmCallback);
    }
    public void sendMsg(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("35000");
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("order_123456");
        Message message = MessageBuilder.withBody("confirm ~~~!!!".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.confirm.1", "info", message, correlationData);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }
}
