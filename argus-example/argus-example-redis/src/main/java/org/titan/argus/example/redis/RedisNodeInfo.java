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
public class RedisNodeInfo {
	private String id;

	private String host;

	private Integer port;

	private String password;
}
