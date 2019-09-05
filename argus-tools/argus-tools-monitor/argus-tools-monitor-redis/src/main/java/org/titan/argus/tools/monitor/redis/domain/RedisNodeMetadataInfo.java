package org.titan.argus.tools.monitor.redis.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author starboyate
 */
@Data
@Accessors(chain = true)
@Builder
public class RedisNodeMetadataInfo {
	private String host;

	private Integer port;

	private String role;

	private String slot;

}
