package org.titan.argus.model.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 */
@Data
@Builder
public class RouteRequest {
	private String id;

	private String stripPrefix;

	private String path;

	private String uri;
}
