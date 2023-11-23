package com.return1.service;

import com.return1.config.MyReturnCallBack;
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
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MyReturnCallBack myReturnCallBack;


    @PostConstruct//构造方法后执行，相当于初始化
    public void init() {
        rabbitTemplate.setReturnsCallback(myReturnCallBack);
    }

    public void sendMsg() {
        MessageProperties messageProperties = new MessageProperties();
        Message message = MessageBuilder.withBody("return1 test~~~!!!".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.return.1", "info", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }

}
