package com.rabbitdelay4.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueDelayName}")
    private String queueDelayName;


    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-message", "direct");
        return new CustomExchange(exchangeName, "x-delayed-message"
                    , true, false, args);
    }
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueDelayName)
                .build();
    }

    @Bean
    public Binding binding(Queue queue, CustomExchange customExchange) {
        return BindingBuilder
                .bind(queue)
                .to(customExchange)
                .with("plugin").noargs();
    }
}
