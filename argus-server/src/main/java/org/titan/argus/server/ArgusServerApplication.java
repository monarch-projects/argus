package org.titan.argus.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.server.config.RandomServerPortPropertySource;
import org.titan.argus.server.listener.RandomServerPortListener;

@SpringBootApplication
@RestController
public class ArgusServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ArgusServerApplication.class);
        application.addListeners(new RandomServerPortListener());
        application.run(args);
    }

    @GetMapping("/hello")
	public String hello() {
    	return "hello";
	}


}
