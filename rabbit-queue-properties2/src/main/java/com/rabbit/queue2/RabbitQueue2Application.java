package com.rabbit.queue2;


import com.rabbit.queue2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitQueue2Application implements ApplicationRunner {
    @Autowired
    private MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(RabbitQueue2Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        messageService.sendMsg();
    }
}
