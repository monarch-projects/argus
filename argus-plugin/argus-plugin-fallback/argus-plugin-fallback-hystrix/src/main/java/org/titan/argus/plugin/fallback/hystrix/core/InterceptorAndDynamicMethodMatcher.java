package org.titan.argus.plugin.fallback.hystrix.core;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.MethodMatcher;

public class InterceptorAndDynamicMethodMatcher {
	final MethodInterceptor interceptor;

	final MethodMatcher methodMatcher;

	public InterceptorAndDynamicMethodMatcher(MethodInterceptor interceptor, MethodMatcher methodMatcher) {
		this.interceptor = interceptor;
		this.methodMatcher = methodMatcher;
	}
}
