package com.rabbitdelay4.service;

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
        {
            MessageProperties messageProperties = new MessageProperties();
//          messageProperties.setExpiration("25000");
            messageProperties.setHeader("x-delay", 25000);
            Message message = MessageBuilder.withBody("test delay4~111~".getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.delay4", "plugin", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
        {
            MessageProperties messageProperties = new MessageProperties();
//            messageProperties.setExpiration("15000");
            messageProperties.setHeader("x-delay", 15000);
            Message message = MessageBuilder.withBody("test delay4~222~".getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.delay4", "plugin", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
    }
}
