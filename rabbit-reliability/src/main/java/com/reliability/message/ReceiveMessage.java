package com.reliability.message;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ReceiveMessage {
    @RabbitListener(queues = {"queue.reliability"})
    public void receiveMsg(Message message, Channel channel){
        //获取消息唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("接收到的消息为：{}", new String(message.getBody()));
            //TODO 插入订单
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
