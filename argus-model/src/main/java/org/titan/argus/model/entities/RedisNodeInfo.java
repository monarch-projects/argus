package org.titan.argus.model.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author starboyate
 */
@Data
@Accessors(chain = true)
public class RedisNodeInfo {
	private String id;

	private Boolean isCluster = false;

	private Boolean isSentinel = false;

	private List<RedisNode> nodeList;
}
