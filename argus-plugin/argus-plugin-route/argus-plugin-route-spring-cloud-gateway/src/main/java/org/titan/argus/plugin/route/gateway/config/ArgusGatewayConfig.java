package org.titan.argus.plugin.route.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.plugin.route.core.ArgusRefreshRouteEventHolder;
import org.titan.argus.plugin.route.core.ArgusRouteRepository;
import org.titan.argus.plugin.route.gateway.repository.ArgusGatewayRouteRepository;

/**
 * @author starboyate
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ArgusGatewayConfig {
	@Bean
	public ArgusRefreshRouteEventHolder<RefreshRoutesEvent> holder() {
		return new ArgusRefreshRouteEventHolder<>();
	}

	@Bean
	public ArgusRouteRepository repository() {
		return new ArgusGatewayRouteRepository();
	}
}
