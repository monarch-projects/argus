package org.titan.argus.client.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.titan.argus.client.endpoint.ArgusJvmEndpoint;
import org.titan.argus.client.endpoint.ArgusVersionEndpoint;

/**
 * @author starboyate
 */
@Configuration
@PropertySource("classpath:/client/client.properties")
public class ArgusClientAutoConfiguration {

	@Bean
	public ArgusVersionEndpoint argusVersionEndpoint() {
		return new ArgusVersionEndpoint();
	}

	@Bean(initMethod = "init")
	public ArgusJvmEndpoint argusJvmEndpoint() {return new ArgusJvmEndpoint();}
}
