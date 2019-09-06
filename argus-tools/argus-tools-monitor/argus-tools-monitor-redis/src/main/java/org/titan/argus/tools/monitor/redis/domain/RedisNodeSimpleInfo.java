package org.titan.argus.tools.monitor.redis.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author starboyate
 */
@Data
@Accessors(chain = true)
public class RedisNodeSimpleInfo {
	List<RedisNode> nodes;

	private String mode;

	private String version;

	private Integer nodeSize;
}
