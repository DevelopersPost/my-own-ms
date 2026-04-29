package com.example.x;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XApplication {

	public static void main(String[] args) {
		SpringApplication.run(XApplication.class, args);
	}

}
