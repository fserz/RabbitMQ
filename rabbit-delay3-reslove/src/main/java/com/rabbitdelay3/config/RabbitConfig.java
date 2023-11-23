package com.rabbitdelay3.config;

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
    @Value("${my.queueOrderName}")
    private String queueOrderName;
    @Value("${my.queuePayName}")
    private String queuePayName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    @Bean(name = "directExchangeB")
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queueOrderNormal() {
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-message-ttl", 25000);
        //1.new
//        return new Queue(queueName,true, false, false, arguments);
//        Queue queue = new Queue(queueName,true, false, false, arguments);
//        return queue;
        //2.建造者
        return QueueBuilder.durable(queueOrderName)
                .deadLetterExchange(exchangeName)//设置相同的交换机
                .deadLetterRoutingKey("error")//设置死信路由key
                .build();
    }

    @Bean
    public Binding bindingOrderNormal(Queue queueOrderNormal, DirectExchange directExchange) {
        return BindingBuilder
                .bind(queueOrderNormal)
                .to(directExchange)
                .with("order");
    }

    @Bean
    public Queue queuePayNormal() {
        return QueueBuilder.durable(queuePayName)
                .deadLetterExchange(exchangeName)//设置相同的交换机
                .deadLetterRoutingKey("error")//设置死信路由key
                .build();
    }

    @Bean
    public Binding bindingPayNormal(Queue queuePayNormal, DirectExchange directExchange) {
        return BindingBuilder
                .bind(queuePayNormal)
                .to(directExchange)
                .with("pay");
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
