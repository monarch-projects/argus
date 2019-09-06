package org.titan.argus.client.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.client.mongodb.MongodbRepository;

@Configuration
@ConditionalOnClass(name = {"org.springframework.data.mongodb.core.MongoTemplate"})
public class ArgusMongodbAutoConfiguration {

	@Bean
	public MongodbRepository mongodbRepository(MongoProperties mongoProperties) {
		return new MongodbRepository(mongoProperties);
	}
}
