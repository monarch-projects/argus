package org.titan.argus.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.server.config.RandomServerPortPropertySource;
import org.titan.argus.server.listener.RandomServerPortListener;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.Map;

@SpringBootApplication
@ComponentScan("org.titan.argus.*")
public class ArgusServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ArgusServerApplication.class);
        application.addListeners(new RandomServerPortListener());
        application.run(args);

	}



}
