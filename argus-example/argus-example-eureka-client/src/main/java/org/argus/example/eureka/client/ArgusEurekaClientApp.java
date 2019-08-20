package org.argus.example.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.titan.argus.plugin.fallback.hystrix.web.servlet.ArgusHystrixCommandAspect;

@SpringBootApplication
@EnableCircuitBreaker
public class ArgusEurekaClientApp {
	public static void main(String[] args) {
		SpringApplication.run(ArgusEurekaClientApp.class, args);
	}

//	@Bean
//	public ArgusHystrixCommandAspect argusHystrixCommandAspect() {
//		return new ArgusHystrixCommandAspect();
//	}
}
