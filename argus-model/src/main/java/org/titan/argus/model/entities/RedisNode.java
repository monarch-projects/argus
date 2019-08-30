package org.titan.argus.model.entities;

import lombok.Builder;
import lombok.Data;


/**
 * @author starboyate
 */
@Data
@Builder
public class RedisNode {
	private String host;

	private Integer port;
}
