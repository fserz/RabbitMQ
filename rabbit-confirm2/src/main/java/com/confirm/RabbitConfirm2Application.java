package com.confirm;


import com.confirm.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitConfirm2Application implements ApplicationRunner {
    @Resource
    private MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(RabbitConfirm2Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args)  {
        messageService.sendMsg();
    }
}
