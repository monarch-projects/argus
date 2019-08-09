package org.argus.example.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class ArgusEurekaClientApp {
	public static void main(String[] args) {
		SpringApplication.run(ArgusEurekaClientApp.class, args);
	}

}
