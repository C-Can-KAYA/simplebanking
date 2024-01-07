package com.eteration.simplebanking;

import com.eteration.simplebanking.services.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	private final AccountService service;

	public DemoApplication(AccountService service) {
		this.service = service;
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public void dummyAccount() {
		service.addAccount("deneme","123");
	}

}
