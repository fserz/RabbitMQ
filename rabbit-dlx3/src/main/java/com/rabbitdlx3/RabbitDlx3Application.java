package com.rabbitdlx3;

import com.rabbitdlx3.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitDlx3Application implements ApplicationRunner {
	@Autowired
	private MessageService messageService;
	public static void main(String[] args) {
		SpringApplication.run(RabbitDlx3Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		messageService.sendMsg();
	}
}
