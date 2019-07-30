package org.titan.argus.client.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.titan.argus.client.endpoint.ArgusDependencyEndpoint;
import org.titan.argus.client.endpoint.ArgusJvmEndpoint;
import org.titan.argus.client.event.ArgusClientEvent;
//import org.titan.argus.client.endpoint.ArgusMetaDataEndpoint;

/**
 * @author starboyate
 */
@Configuration
@PropertySource("classpath:/client/client.properties")
public class ArgusClientAutoConfiguration {

	@Bean
	public ArgusDependencyEndpoint argusDependencyEndpoint() {return new ArgusDependencyEndpoint();}

	@Bean(initMethod = "init")
	public ArgusJvmEndpoint argusJvmEndpoint() {return new ArgusJvmEndpoint();}

	@Bean
	public ArgusClientEvent argusClientEvent() {
		return new ArgusClientEvent();
	}
}
