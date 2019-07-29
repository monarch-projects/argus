package org.titan.argus.client.entities.args;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 */
@Builder
@Data
public class ArgusJvmArgsInfo {
	private String name;

	private String value;
}
