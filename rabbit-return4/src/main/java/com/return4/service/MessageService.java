package com.return4.service;

import jakarta.annotation.PostConstruct;
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
public class MessageService{
    @Resource
    private RabbitTemplate rabbitTemplate;


    @PostConstruct//构造方法后执行，相当于初始化
    public void init() {
        rabbitTemplate.setReturnsCallback(
                returned ->   log.error("消息从交换机没有正确到队列，原因是：{}", returned.getReplyText())
        );
    }

    public void sendMsg() {
        MessageProperties messageProperties = new MessageProperties();
        Message message = MessageBuilder.withBody("return4 test4~~~!!!".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.return.4", "info", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }

}
