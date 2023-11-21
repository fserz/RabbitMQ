package com.rabbitdlx2;

import com.rabbitdlx2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitDlx2Application implements ApplicationRunner {
	@Autowired
	private MessageService messageService;
	public static void main(String[] args) {
		SpringApplication.run(RabbitDlx2Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		messageService.sendMsg();
	}
}
