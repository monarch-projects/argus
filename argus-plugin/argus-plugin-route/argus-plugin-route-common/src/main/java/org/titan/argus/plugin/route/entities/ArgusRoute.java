package org.titan.argus.plugin.route.entities;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

/**
 * @author starboyate
 */
@Data
@Builder
public class ArgusRoute {
	private String id;

	private String stripPrefix;

	private String path;

	private String uri;
}
