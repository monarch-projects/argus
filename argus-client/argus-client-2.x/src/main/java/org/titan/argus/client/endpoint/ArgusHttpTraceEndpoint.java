/*
 * Copyright 2012-2018 the original author or authors.
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

package org.titan.argus.client.endpoint;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.titan.argus.client.web.trace.http.ArgusHttpTrace;
import org.titan.argus.client.web.trace.http.ArgusHttpTraceRepository;
import org.titan.argus.util.PageUtil;

import java.util.List;


@RestControllerEndpoint(id = "traces")
public class ArgusHttpTraceEndpoint {

	private final ArgusHttpTraceRepository repository;

	/**
	 * Create a new {@link ArgusHttpTraceEndpoint} instance.
	 * @param repository the trace repository
	 */
	public ArgusHttpTraceEndpoint(ArgusHttpTraceRepository repository) {
		Assert.notNull(repository, "Repository must not be null");
		this.repository = repository;
	}

	@GetMapping
	public HttpTraceDescriptor traces(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		return new HttpTraceDescriptor(PageUtil.page(page, size, this.repository.findAll()));
	}

	/**
	 * A description of an application's {@link HttpTrace} entries. Primarily intended for
	 * serialization to JSON.
	 */
	public static final class HttpTraceDescriptor {

		private final List<ArgusHttpTrace> traces;

		private HttpTraceDescriptor(List<ArgusHttpTrace> traces) {
			this.traces = traces;
		}

		public List<ArgusHttpTrace> getTraces() {
			return this.traces;
		}

	}

}
