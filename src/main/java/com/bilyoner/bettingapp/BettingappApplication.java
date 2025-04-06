package com.bilyoner.bettingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BettingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettingappApplication.class, args);
	}

}
