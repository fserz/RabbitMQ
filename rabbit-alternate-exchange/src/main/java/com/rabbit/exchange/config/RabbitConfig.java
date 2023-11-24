package com.rabbit.exchange.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeNormalName}")
    private String exchangeNormalName;
    @Value("${my.queueAlternateName}")
    private String queueAlternateName;
    @Value("${my.exchangeAlternateName}")
    private String exchangeAlternateName;
    @Value("${my.queueNormalName}")
    private String queueNormalName;

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeNormalName)
                .alternate(exchangeAlternateName)
                .build();
    }

    @Bean
    public Queue queueNormal() {
        return QueueBuilder.durable(queueNormalName).build();

    }

    @Bean
    public Binding bindingNormal(Queue queueNormal, DirectExchange directExchange) {
        return BindingBuilder.bind(queueNormal).to(directExchange).with("info");
    }
    @Bean
    public FanoutExchange alternateExchange() {
        return ExchangeBuilder.fanoutExchange(exchangeAlternateName)
                .build();
    }

    @Bean
    public Queue queueAlternate() {
        return QueueBuilder.durable(queueAlternateName).build();

    }

    @Bean
    public Binding bindingAlternate(Queue queueAlternate, FanoutExchange fanoutExchangeName) {
        return BindingBuilder.bind(queueAlternate).to(fanoutExchangeName);
    }
}
