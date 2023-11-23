package com.rabbitdelay4;




import com.rabbitdelay4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitTTLApplication4 implements ApplicationRunner {
    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitTTLApplication4.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}
