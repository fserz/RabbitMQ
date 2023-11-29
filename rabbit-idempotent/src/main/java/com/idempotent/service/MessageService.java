package com.idempotent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idempotent.vo.Orders;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    //这个对象可以序列化和反序列化(json格式)
    @Resource
    private ObjectMapper objectMapper;

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

    public void sendMsg() throws JsonProcessingException {
        {
            //创建订单
            Orders orders1 = Orders.builder()
                    .id(100)
                    .orderName("apple smartphone")
                    .orderMoney(new BigDecimal(235453))
                    .orderTime(new Date())
                    .build();
            String strOrders1 = objectMapper.writeValueAsString(orders1);
            MessageProperties messageProperties = new MessageProperties();
            Message message = MessageBuilder.withBody(strOrders1.getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.idempotent", "info", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }
        {
            Orders orders2 = Orders.builder()
                    .id(100)
                    .orderName("apple ipad")
                    .orderMoney(new BigDecimal(1321332))
                    .orderTime(new Date())
                    .build();
            String strOrders2 = objectMapper.writeValueAsString(orders2);
            MessageProperties messageProperties = new MessageProperties();
            Message message = MessageBuilder.withBody(strOrders2.getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.idempotent", "info", message);
            log.info("消息发送完毕，发送时间为：{}", new Date());
        }

    }

}

