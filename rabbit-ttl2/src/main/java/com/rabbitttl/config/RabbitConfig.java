package com.rabbitttl.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueName}")
    private String queueName;
    @Bean(name = "directExchangeB")
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queue(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 38000);
        //1.new
//        return new Queue(queueName,true, false, false, arguments);
//        Queue queue = new Queue(queueName,true, false, false, arguments);
//        return queue;
        //2.建造者
        return QueueBuilder.durable(queueName).withArguments(arguments).build();
    }
    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
