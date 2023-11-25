package com.rabbit.queue2.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueName}")
    private String queueName;
    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchangeName)
                .build();
    }
    @Bean
    public Queue queue(){
        return QueueBuilder
                .durable(queueName)
                .overflow(QueueBuilder.Overflow.rejectPublish)//队列溢出行为，拒绝发布
                .maxLength(5)//队列最大长度
                .autoDelete()
                .build();
//        return new Queue(queueName, true, false, true);
    }
    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
