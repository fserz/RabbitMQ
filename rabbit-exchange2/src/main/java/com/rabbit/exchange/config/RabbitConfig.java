package com.rabbit.exchange.config;

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
    @Bean(name = "directExchangeA")
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchangeName)
                .autoDelete()//没有队列绑定时自动删除
                .build();
    }
    @Bean(name = "queueA")
    public Queue queue(){
        return QueueBuilder.durable(queueName).build();

    }
    @Bean(name = "bindingA")
    public Binding binding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
