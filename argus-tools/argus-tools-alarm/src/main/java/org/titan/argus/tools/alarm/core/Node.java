package org.titan.argus.tools.alarm.core;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 */
@Data
@Builder
public class Node {
	private String host;

	private Integer port;

	private String userName;

	private String password;
}
