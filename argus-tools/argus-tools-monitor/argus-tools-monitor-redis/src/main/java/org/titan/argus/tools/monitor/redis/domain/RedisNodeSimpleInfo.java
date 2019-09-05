package org.titan.argus.tools.monitor.redis.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author starboyate
 */
@Data
@Accessors(chain = true)
public class RedisNodeSimpleInfo {
	private String host;

	private Integer port;

	private String mode;

	private String version;

	private Integer nodeSize;

	private String role;
}
