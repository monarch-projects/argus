package org.titan.argus.plugin.fallback.hystrix.web.reactive;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * @author starboyate
 */
public class ArgusReactiveFilter implements WebFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		PartnerServerWebExchangeDecorator exchangeDecorator = new PartnerServerWebExchangeDecorator(exchange);
		ServerHttpRequest request = exchangeDecorator.getRequest();
		ReactiveRequestContext.set(request);
		return chain.filter(exchangeDecorator);
	}
}
