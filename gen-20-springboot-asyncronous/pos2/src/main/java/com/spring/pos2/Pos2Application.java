package com.spring.pos2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Pos2Application {

	public static void main(String[] args) {
		SpringApplication.run(Pos2Application.class, args);
	}

}
