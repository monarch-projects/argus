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

package org.titan.argus.client.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.client.web.reactive.ArgusHttpTraceWebReactiveFilter;
import org.titan.argus.client.web.servlet.ArgusHttpTraceWebMvcFilter;
import org.titan.argus.client.web.trace.http.ArgusHttpExchangeTracer;
import org.titan.argus.client.web.trace.http.ArgusHttpTraceRepository;
import org.titan.argus.client.web.trace.http.ArgusInMemoryHttpTraceRepository;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(ArgusHttpTraceProperties.class)
public class ArgusHttpTraceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(ArgusHttpTraceRepository.class)
	public ArgusInMemoryHttpTraceRepository argusInMemoryHttpTraceRepository() {
		return new ArgusInMemoryHttpTraceRepository();
	}

	@Bean
	@ConditionalOnMissingBean
	public ArgusHttpExchangeTracer argusHttpExchangeTracer(ArgusHttpTraceProperties traceProperties) {
		return new ArgusHttpExchangeTracer(traceProperties.getInclude());
	}

	@Configuration
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	static class ServletTraceFilterConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public ArgusHttpTraceWebMvcFilter argusHttpTraceWebMvcFilter(ArgusHttpTraceRepository repository, ArgusHttpExchangeTracer tracer) {
			return new ArgusHttpTraceWebMvcFilter(repository, tracer);
		}

	}

	@Configuration
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	static class ReactiveTraceFilterConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public ArgusHttpTraceWebReactiveFilter argusHttpTraceWebReactiveFilter(ArgusHttpTraceRepository repository, ArgusHttpExchangeTracer tracer,
				ArgusHttpTraceProperties traceProperties) {
			return new ArgusHttpTraceWebReactiveFilter(repository, tracer, traceProperties.getInclude());
		}

	}

}
