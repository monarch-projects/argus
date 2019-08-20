package org.titan.argus.plugin.fallback.hystrix.web.reactive;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public final class PartnerServerWebExchangeDecorator extends ServerWebExchangeDecorator {

	private final ServerHttpRequestDecorator requestDecorator;

	public PartnerServerWebExchangeDecorator(ServerWebExchange delegate) {
		super(delegate);
		this.requestDecorator = new PartnerServerHttpRequestDecorator(delegate.getRequest());
	}

	@Override
	public ServerHttpRequest getRequest() {
		return requestDecorator;
	}

}
