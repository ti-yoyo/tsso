package com.tinet.tsso.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tinet")
public class ApplicationStarter {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ApplicationStarter.class);
		application.run(args);
	}
}
