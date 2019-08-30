package org.titan.argus.plugin.redis.entities;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author starboyate
 */
@Data
@Accessors(chain = true)
public class RedisNodeInfo {
	private Boolean isCluster = false;

	private Boolean isSentinel = false;

	private List<RedisNode> nodeList;
}
