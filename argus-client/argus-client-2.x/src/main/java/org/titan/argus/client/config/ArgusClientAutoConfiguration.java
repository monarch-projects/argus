package org.titan.argus.client.config;



import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.titan.argus.client.endpoint.*;
import org.titan.argus.plugin.mongodb.config.ArgusMongodbAutoConfig;
import org.titan.argus.plugin.mongodb.core.MongodbService;
import org.titan.argus.plugin.redis.config.ArgusRedisAutoConfig;
import org.titan.argus.plugin.redis.core.RedisService;
import org.titan.argus.plugin.route.core.ArgusRouteRepository;
import org.titan.argus.plugin.route.gateway.config.ArgusGatewayConfig;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * @author starboyate
 * Do not use @ConditionalOnClass  annotation’value attribute
 * Why use @ConditionalOnClass annotation’s name attribute？
 * {@see jdkbug: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-7183985}
 * {@see spring boot issues: https://github.com/spring-projects/spring-boot/issues/17282}
 */
@Configuration
@AutoConfigureAfter({ArgusGatewayConfig.class, HttpTraceAutoConfiguration.class, ArgusRedisAutoConfig.class, ArgusMongodbAutoConfig.class})
@PropertySource("classpath:/client/client.properties")
public class ArgusClientAutoConfiguration {

	@Bean
	public ArgusDependencyEndpoint argusDependencyEndpoint() {return new ArgusDependencyEndpoint();}

	@Bean(initMethod = "init")
	public ArgusJvmEndpoint argusJvmEndpoint() {return new ArgusJvmEndpoint();}

	@Bean
	@ConditionalOnClass(name = "com.netflix.hystrix.HystrixCommand")
	public ArgusHystrixFallbackEndpoint argusFallbackEndpoint() {
		return new ArgusHystrixFallbackEndpoint();
	}

	@ConditionalOnBean({ArgusRedisAutoConfig.class})
	@Bean
	public ArgusRedisEndpoint argusRedisEndpoint(RedisService redisService) {
		return new ArgusRedisEndpoint(redisService);
	}

	@ConditionalOnBean({ArgusMongodbAutoConfig.class})
	@Bean
	public ArgusMongodbEndpoint mongodbEndpoint(MongodbService service) {
		return new ArgusMongodbEndpoint(service);
	}

	@ConditionalOnBean({ArgusRouteRepository.class})
	@Bean
	public ArgusRouteEndpoint argusRouteEndpoint() {
		return new ArgusRouteEndpoint();
	}


	@Configuration
	@ConditionalOnWebApplication(type = SERVLET)
	protected static class ArgusClientServletAutoConfiguration {
		@Bean
		public ArgusUrlMappingEndpoint urlMappingEndpoint() {
			return new ArgusUrlMappingEndpoint();
		}

		@Bean
		public ArgusMetaInfoMvcEndpoint argusMetaInfoMvcEndpoint() {
			return new ArgusMetaInfoMvcEndpoint();
		}

	}


	@Configuration
	@ConditionalOnWebApplication(type = REACTIVE)
	protected static class ArgusClientReactiveAutoConfiguration {

		@Bean
		public ArgusReactiveUrlMappingEndpoint argusReactiveUrlMappingEndpoint() {
			return new ArgusReactiveUrlMappingEndpoint();
		}


		@Bean
		public ArgusMetaInfoReactiveEndpoint argusMetaInfoReactiveEndpoint() {
			return new ArgusMetaInfoReactiveEndpoint();
		}

	}


}
