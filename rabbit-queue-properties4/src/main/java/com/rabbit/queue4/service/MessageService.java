package com.rabbit.queue4.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
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
        for (int i = 0; i < 5; i++) {
            String strMsg = i +" queue properties test!!! " ;
            Message message = MessageBuilder.withBody(strMsg.getBytes()).build();
            rabbitTemplate.convertAndSend(exchangeName, "info", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setPriority(6);
            String strMsg = "priority 6 queue properties test!!! " ;
            Message message = MessageBuilder.withBody(strMsg.getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend(exchangeName, "info", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setPriority(8);
            String strMsg = "priority 8 queue properties test!!! " ;
            Message message = MessageBuilder.withBody(strMsg.getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend(exchangeName, "info", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setPriority(20);
            String strMsg = "priority 8 queue properties test!!! " ;
            Message message = MessageBuilder.withBody(strMsg.getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend(exchangeName, "info", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
    }
}
