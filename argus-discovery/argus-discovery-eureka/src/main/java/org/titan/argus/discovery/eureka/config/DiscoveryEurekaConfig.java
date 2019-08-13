package org.titan.argus.discovery.eureka.config;


import org.titan.argus.discovery.common.listener.ArgusDiscoveryListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.discovery.eureka.repository.ArgusEurekaInstanceRepository;

@Configuration
public class DiscoveryEurekaConfig {


	@Bean
	public ArgusDiscoveryListener listener(@Autowired ArgusEurekaInstanceRepository repository) {
		return new ArgusDiscoveryListener(repository);
	}

}
