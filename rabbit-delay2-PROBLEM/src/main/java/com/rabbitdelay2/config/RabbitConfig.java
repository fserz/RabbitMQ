package com.rabbitdelay2.config;

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
    @Value("${my.queueNormalName}")
    private String queueNormalName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    @Bean(name = "directExchangeB")
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queueNormal() {
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-message-ttl", 25000);
        //1.new
//        return new Queue(queueName,true, false, false, arguments);
//        Queue queue = new Queue(queueName,true, false, false, arguments);
//        return queue;
        //2.建造者
        return QueueBuilder.durable(queueNormalName)
                .ttl(25000)
                .deadLetterExchange(exchangeName)//设置相同的交换机
                .deadLetterRoutingKey("error")//设置死信路由key
                .build();
    }

    @Bean
    public Binding bindingNormal(Queue queueNormal, DirectExchange directExchange) {
        return BindingBuilder.bind(queueNormal).to(directExchange).with("order");
    }

    @Bean
    public Queue queueDlx() {
        return QueueBuilder.durable(queueDlxName).build();
    }

    @Bean
    public Binding BindingDlx(Queue queueDlx, DirectExchange directExchange) {
        return BindingBuilder.bind(queueDlx).to(directExchange).with("error");
    }
}
