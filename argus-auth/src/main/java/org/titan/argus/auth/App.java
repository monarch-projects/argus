package org.titan.argus.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("org.titan.argus.*")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}


}
