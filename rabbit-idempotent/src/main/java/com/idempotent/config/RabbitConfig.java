package com.idempotent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueName}")
    private String queueName;

    @Bean
    public DirectExchange directExchange() {
        //交换机默认就是持久化的
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    @Bean
    public Queue queue() {
        //队列持久化
        return QueueBuilder.durable(queueName).build();

    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
