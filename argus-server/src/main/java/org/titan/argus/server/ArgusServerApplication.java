package org.titan.argus.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.titan.argus.server.config.RandomServerPortPropertySource;
import org.titan.argus.server.listener.RandomServerPortListener;

@SpringBootApplication
public class ArgusServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ArgusServerApplication.class);
        application.addListeners(new RandomServerPortListener());
        application.run(args);
    }
}
