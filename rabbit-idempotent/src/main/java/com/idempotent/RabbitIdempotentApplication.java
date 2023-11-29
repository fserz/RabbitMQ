package com.idempotent;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.idempotent.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitIdempotentApplication implements ApplicationRunner {
    @Resource
    private MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(RabbitIdempotentApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args)  {
        try {
            messageService.sendMsg();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
