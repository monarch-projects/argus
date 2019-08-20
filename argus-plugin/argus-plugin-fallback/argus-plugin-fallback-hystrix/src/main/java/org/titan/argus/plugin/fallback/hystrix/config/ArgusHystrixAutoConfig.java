package org.titan.argus.plugin.fallback.hystrix.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.titan.argus.plugin.fallback.hystrix.core.ArgusHystrixCommandConvert;
import org.titan.argus.plugin.fallback.hystrix.repository.mvc.ArgusHystrixUrlMappingsRepository;
import org.titan.argus.plugin.fallback.hystrix.repository.reactive.ArgusHystrixReactiveUrlMappingsRepository;
import org.titan.argus.plugin.fallback.hystrix.web.reactive.ArgusHystrixCommandReactiveAspect;
import org.titan.argus.plugin.fallback.hystrix.web.reactive.ArgusReactiveFilter;
import org.titan.argus.plugin.fallback.hystrix.web.servlet.ArgusFilter;
import org.titan.argus.plugin.fallback.hystrix.web.servlet.ArgusHystrixCommandAspect;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * @author starboyate
 */
@Configuration
public class ArgusHystrixAutoConfig {


	@Configuration
	@ConditionalOnWebApplication(type = SERVLET)
	protected static class HystrixServletAutoConfiguration {

		@Bean(initMethod = "init")
		public ArgusHystrixUrlMappingsRepository hystrixUrlMappingsRepository() {
			return new ArgusHystrixUrlMappingsRepository();
		}

		@Bean
		public ArgusFilter argusFilter() {
			return new ArgusFilter();
		}


		@Bean
		public ArgusHystrixCommandAspect argusHystrixCommandAspect(){
			return new ArgusHystrixCommandAspect();
		}

	}

	@Configuration
	@ConditionalOnWebApplication(type = REACTIVE)
	protected static class HystrixReactiveAutoConfiguration {

		@Bean(initMethod = "init")
		public ArgusHystrixReactiveUrlMappingsRepository argusHystrixReactiveUrlMappingsRepository() {
			return new ArgusHystrixReactiveUrlMappingsRepository();
		}

		@Bean
		public ArgusReactiveFilter argusReactiveFilter() {
			return new ArgusReactiveFilter();
		}


		@Bean
		public ArgusHystrixCommandReactiveAspect argusHystrixCommandReactiveAspect(){
			return new ArgusHystrixCommandReactiveAspect();
		}

	}


}
