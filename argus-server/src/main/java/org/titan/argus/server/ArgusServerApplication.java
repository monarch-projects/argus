package org.titan.argus.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("org.titan.argus.*")
@MapperScan("org.titan.argus.storage.mysql")
@EnableScheduling
public class ArgusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArgusServerApplication.class, args);

    }


}
