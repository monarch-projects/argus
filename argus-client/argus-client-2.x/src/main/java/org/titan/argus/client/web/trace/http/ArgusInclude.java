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

package org.titan.argus.client.web.trace.http;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Include options for HTTP tracing.
 *
 * @author Wallace Wadge
 * @since 2.0.0
 */
public enum ArgusInclude {

	/**
	 * Include request headers.
	 */
	REQUEST_HEADERS,

	/**
	 * Include response headers.
	 */
	RESPONSE_HEADERS,

	/**
	 * Include "Cookie" header (if any) in request headers and "Set-Cookie" (if any) in
	 * response headers.
	 */
	COOKIE_HEADERS,

	/**
	 * Include authorization header (if any).
	 */
	AUTHORIZATION_HEADER,

	/**
	 * Include the principal.
	 */
	PRINCIPAL,

	/**
	 * Include the remote address.
	 */
	REMOTE_ADDRESS,

	/**
	 * Include the session ID.
	 */
	SESSION_ID,

	/**
	 * Include the time taken to service the request in milliseconds.
	 */
	TIME_TAKEN;

	private static final Set<ArgusInclude> DEFAULT_INCLUDES;

	static {
		Set<ArgusInclude> defaultIncludes = new LinkedHashSet<>();
		defaultIncludes.add(ArgusInclude.REQUEST_HEADERS);
		defaultIncludes.add(ArgusInclude.RESPONSE_HEADERS);
		defaultIncludes.add(ArgusInclude.COOKIE_HEADERS);
		defaultIncludes.add(ArgusInclude.TIME_TAKEN);
		DEFAULT_INCLUDES = Collections.unmodifiableSet(defaultIncludes);
	}

	/**
	 * Return the default {@link ArgusInclude}.
	 * @return the default include.
	 */
	public static Set<ArgusInclude> defaultIncludes() {
		return DEFAULT_INCLUDES;
	}

}
