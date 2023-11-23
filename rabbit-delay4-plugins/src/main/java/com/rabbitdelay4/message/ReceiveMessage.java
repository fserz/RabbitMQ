package com.rabbitdelay4.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ReceiveMessage {
    // 延迟队列接受死信队列的消息
    @RabbitListener(queues = "queue.delay4")
    public void receiveMsg(Message message){
        String body = new String(message.getBody());
        log.info("接收的信息为：{},时间为：{}", body, new Date());
    }
}
