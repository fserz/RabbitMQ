package com.rabbitdlx4.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration

public class RabbitConfig {
    @Value("${my.exchangeNormalName}")
    private String exchangeNormalName;
    @Value("${my.queueNormalName}")
    private String queueNormalName;
    @Value("${my.exchangeDlxName}")
    private String exchangeDxlName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    @Bean
    public DirectExchange normalExchange(){
        return ExchangeBuilder.directExchange(exchangeNormalName).build();
    }

    /**
     * 正常队列
     * @return
     */
    @Bean
    public Queue normalQueue(){
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-max-length", 5)
//        arguments.put("x-message-ttl", 20000);//设置过期时间
//        arguments.put("x-dead-letter-exchange", exchangeDxlName);//设置队列死信交换机
//        arguments.put("x-dead-letter-routing-key", "error");//设置死信路由key需要和死信交换机和死信队列绑定绑定的一致
        return QueueBuilder.durable(queueNormalName)
                .deadLetterExchange(exchangeDxlName).maxLength(5)
                .deadLetterRoutingKey("error").ttl(15000)
//                .withArguments(arguments)
                .build();
    }

    /**
     * NormalExchange和正常队列绑定
     * @param normalExchange
     * @param normalQueue
     * @return
     */
    @Bean
    public Binding bindingNormal(DirectExchange normalExchange, Queue normalQueue){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("order");
    }
    @Bean
    public DirectExchange dlxExchange(){
        return ExchangeBuilder.directExchange(exchangeDxlName).build();
    }
    @Bean
    public Queue dlxQueue(){
        return QueueBuilder.durable(queueDlxName).build();
    }

    /**
     * 死信交换机和死信队列绑定
     * @param dlxExchange
     * @param dlxQueue
     * @return
     */
    @Bean
    public Binding bindingDlx(DirectExchange dlxExchange, Queue dlxQueue){
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("error");
    }
}