package org.titan.argus.plugin.fallback.hystrix.web.reactive;

import org.springframework.http.server.reactive.ServerHttpRequest;

public final class ReactiveRequestContext {
	private static final ThreadLocal<ServerHttpRequest> context = new InheritableThreadLocal<>();

	public static void set(ServerHttpRequest request) {
		context.set(request);
	}

	public static ServerHttpRequest getRequest() {
		return context.get();
	}
}
