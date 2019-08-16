package org.titan.argus.plugin.fallback.hystrix.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.plugin.fallback.hystrix.core.ArgusHystrixCommandConvert;
import org.titan.argus.plugin.fallback.hystrix.repository.mvc.ArgusHystrixUrlMappingsRepository;
import org.titan.argus.plugin.fallback.hystrix.web.servlet.ArgusFilter;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * @author starboyate
 */
@Configuration
@AutoConfigureAfter({HystrixCircuitBreakerConfiguration.class})
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


//		@Bean
//		@Order(Integer.MIN_VALUE)
//		public ArgusHystrixCommandAspect argusHystrixCommandAspect(){
//			return new ArgusHystrixCommandAspect();
//		}

	}


}
