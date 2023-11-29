package com.idempotent.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idempotent.vo.Orders;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ReceiveMessage {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @RabbitListener(queues = {"queue.idempotent"})
    public void receiveMsg(Message message, Channel channel) throws IOException {
        //获取消息唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String strOrder = new String(message.getBody());
        //使用objectMapper把字节数组反序列化成对象
        Orders orders = objectMapper.readValue(message.getBody(), Orders.class);

        try {
            log.info("对象为：{}", orders.toString());
            log.info("接收到的消息为：{}", strOrder);
            Boolean setResult = stringRedisTemplate.opsForValue()
                    .setIfAbsent("idempotent_" + orders.getId(), String.valueOf(orders.getId()));
            if (setResult) {
                log.info("向数据库插入订单");
            }
            //模拟报错
//            int a = 10/0;
            //手动确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("消息处理出现问题");
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
