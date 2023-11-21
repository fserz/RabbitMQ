package com.receiverdlx4.message;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class MessageReceive {
    @RabbitListener(queues = {"queue.normal.5"})
    public void receiveMsg(Message message, Channel channel){
        //获取信息属性
        MessageProperties messageProperties = message.getMessageProperties();
        //获取消息唯一标识
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            byte[] body = message.getBody();
            String str = new String(body);
            log.info("接收到的消息为：{}，时间：{}", str, new Date());
            //模拟异常
            int a = 1/0;
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("接受者出现问题:{}", e.getMessage());
            try {
                //消费者手动不确认，重新入队
//                channel.basicNack(deliveryTag, false, true);
//                //不重新入队，变成死信
//                channel.basicNack(deliveryTag, false, false);
                //拒绝消息，只能处理单条消息
                channel.basicReject(deliveryTag, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

}
