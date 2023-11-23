package com.confirm.service;

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
public class MessageService implements RabbitTemplate.ConfirmCallback {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct//构造方法后执行，相当于初始化
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("35000");
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("order_123456");
        Message message = MessageBuilder.withBody("confirm2 ~~~!!!".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.confirm.2", "info", message, correlationData);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("关联ID为：{}", correlationData.getId());
        if (ack) {
            log.info("消息正确到达交换机");
            return;
        }
        log.error("消息没有到达交换机，原因是：{}", cause);
    }

}
