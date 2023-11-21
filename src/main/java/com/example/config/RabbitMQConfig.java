package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("exchange.fanout");
    }
    @Bean
    public Queue queueA(){
        return new Queue("queue.fanout.a");
    }
    @Bean
    public Queue queueB(){
        return new Queue("queue.fanout.b");
    }
    @Bean
    public Binding bindingA(FanoutExchange fanoutExchange, Queue queueA){
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }
    @Bean
    public Binding bindingB(FanoutExchange fanoutExchange, Queue queueB){
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
}
