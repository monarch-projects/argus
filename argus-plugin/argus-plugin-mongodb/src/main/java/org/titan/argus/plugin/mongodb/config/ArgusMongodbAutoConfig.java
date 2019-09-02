package org.titan.argus.plugin.mongodb.config;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.titan.argus.plugin.mongodb.core.MongodbService;

/**
 * @author starboyate
 */
@Configuration
@ConditionalOnClass({MongoClient.class})
public class ArgusMongodbAutoConfig {
	@Bean
	public MongodbService mongodbService(MongoClient client, MongoProperties properties, MongoTemplate template) {
		return new MongodbService(properties, client, template);
	}
}
