package com.rabbit.exchange;

import com.rabbit.exchange.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitAlternateExchangeApplication implements ApplicationRunner {
    @Autowired
    private MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(RabbitAlternateExchangeApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args){
        messageService.sendMsg();
    }
}
