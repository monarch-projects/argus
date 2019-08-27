package org.titan.argus.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 */
@Data
@Builder
public class RouteVO {
	private String id;

	private String stripPrefix;

	private String path;

	private String uri;
}
