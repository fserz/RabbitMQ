package com.rabbitdlx4;

import com.rabbitdlx4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitDlx4Application implements ApplicationRunner {
	@Autowired
	private MessageService messageService;
	public static void main(String[] args) {
		SpringApplication.run(RabbitDlx4Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		messageService.sendMsg();
	}
}
