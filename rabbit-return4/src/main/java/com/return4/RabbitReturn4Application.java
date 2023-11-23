package com.return4;



import com.return4.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitReturn4Application implements ApplicationRunner {
    @Resource
    private MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(RabbitReturn4Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args)  {
        messageService.sendMsg();
    }
}
