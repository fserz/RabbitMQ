package com.rabbit.queue4.config;

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
//                .maxLengthBytes(200)
                .maxPriority(10)//1到255之间，不设的话默认为0
//                .autoDelete()
                .build();
    }
    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
