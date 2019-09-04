package org.titan.argus.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author starboyate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisNode {
	private String host;

	private Integer port;
}
