package org.titan.argus.plugin.route.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.plugin.route.core.ArgusRouteRepository;
import org.titan.argus.plugin.route.gateway.repository.ArgusGatewayRouteRepository;

/**
 * @author starboyate
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnBean({GatewayAutoConfiguration.class})
public class ArgusGatewayConfig {
	@Bean
	public ArgusRouteRepository repository() {
		return new ArgusGatewayRouteRepository();
	}
}
