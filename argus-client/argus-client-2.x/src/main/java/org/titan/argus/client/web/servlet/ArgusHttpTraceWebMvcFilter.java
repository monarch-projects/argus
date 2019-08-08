/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.titan.argus.client.web.servlet;

import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.titan.argus.client.web.trace.http.ArgusHttpExchangeTracer;
import org.titan.argus.client.web.trace.http.ArgusHttpTrace;
import org.titan.argus.client.web.trace.http.ArgusHttpTraceRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class ArgusHttpTraceWebMvcFilter extends OncePerRequestFilter implements Ordered {

	private int order = Ordered.LOWEST_PRECEDENCE - 10;

	private final ArgusHttpTraceRepository repository;

	private final ArgusHttpExchangeTracer tracer;



	public ArgusHttpTraceWebMvcFilter(ArgusHttpTraceRepository repository, ArgusHttpExchangeTracer tracer) {
		this.repository = repository;
		this.tracer = tracer;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!isRequestValid(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		TraceableHttpServletRequest traceableRequest = new TraceableHttpServletRequest(request);
		ArgusHttpTrace trace = this.tracer.receivedRequest(traceableRequest);
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		try {
			filterChain.doFilter(request, response);
			status = response.getStatus();
		}
		finally {
			if (!traceableRequest.getUri().toString().contains("/actuator")) {
				TraceableHttpServletResponse traceableResponse = new TraceableHttpServletResponse(
						(status != response.getStatus()) ? new CustomStatusResponseWrapper(response, status) : response);
				this.tracer.sendingResponse(trace, traceableResponse, request::getUserPrincipal,
						() -> getSessionId(request));
				this.repository.add(trace);
			}
		}
	}

	private boolean isRequestValid(HttpServletRequest request) {
		try {
			new URI(request.getRequestURL().toString());
			return true;
		}
		catch (URISyntaxException ex) {
			return false;
		}
	}

	private String getSessionId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null) ? session.getId() : null;
	}

	private static final class CustomStatusResponseWrapper extends HttpServletResponseWrapper {

		private final int status;

		private CustomStatusResponseWrapper(HttpServletResponse response, int status) {
			super(response);
			this.status = status;
		}

		@Override
		public int getStatus() {
			return this.status;
		}

	}

}
