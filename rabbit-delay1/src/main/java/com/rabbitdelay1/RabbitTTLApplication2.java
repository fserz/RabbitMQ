package com.rabbitdelay1;


import com.rabbitdelay1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitTTLApplication2 implements ApplicationRunner {
    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitTTLApplication2.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}
