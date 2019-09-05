package org.titan.argus.example.redis;

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
