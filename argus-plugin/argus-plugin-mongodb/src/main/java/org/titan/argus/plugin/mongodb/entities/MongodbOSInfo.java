package org.titan.argus.plugin.mongodb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongodbOSInfo {
	private String name;

	private String version;

	private Integer cpuCoreSize;

	private Long memorySize;
}
