package org.titan.argus.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.titan.argus.server.listener.RandomServerPortListener;

@SpringBootApplication
@ComponentScan("org.titan.argus.*")
public class ArgusServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ArgusServerApplication.class);
        application.addListeners(new RandomServerPortListener());
        application.run(args);

	}



}
