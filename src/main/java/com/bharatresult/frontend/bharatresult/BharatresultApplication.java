package com.bharatresult.frontend.bharatresult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BharatresultApplication {

	public static void main(String[] args) {
		SpringApplication.run(BharatresultApplication.class, args);
		System.out.println("started....");

	}

}
