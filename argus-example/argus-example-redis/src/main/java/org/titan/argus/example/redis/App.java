package org.titan.argus.example.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@EventListener
	public void onApplicationEvent(ApplicationReadyEvent event) {
		RedisNodeInfoHolder.add(RedisNodeInfo.builder().host("192.168.1.222").port(16379).build());
		RedisNodeInfoHolder.add(RedisNodeInfo.builder().host("192.168.1.223").port(6381).build());
	}
}
