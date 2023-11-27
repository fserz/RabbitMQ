package com.reliability.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(
                (correlationData, ack, cause) -> {
                    if (!ack) {
                        log.error("消息没有到达交换机，原因是：{}", cause);
                        //TODO 重发消息或者记录错误日志
                    }
                }
        );
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.error("消息没有从交换机正确的投递(路由)到队列，原因为：{}", returnedMessage.getReplyText());
            //TODO 记录错误日志，给程序员发短信或者邮件通知
                }
        );
    }

    public void sendMsg() {
        MessageProperties messageProperties = new MessageProperties();
        //设置单条消息持久化，默认就是持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = MessageBuilder.withBody("reliability test~~~ ack!!!".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.reliability", "info", message);
        log.info("消息发送完毕，发送时间为：{}", new Date());
    }

}

