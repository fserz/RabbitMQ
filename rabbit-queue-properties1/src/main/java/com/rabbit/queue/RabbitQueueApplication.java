package com.rabbit.queue;


import com.rabbit.queue.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitQueueApplication implements ApplicationRunner {
    @Autowired
    private MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(RabbitQueueApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        messageService.sendMsg();
    }
}
