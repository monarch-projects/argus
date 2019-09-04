package org.titan.argus.client.entities.metadata;

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
public class ArgusClientMetadataInfo {
	private Boolean isGateway = Boolean.FALSE;

	private String groupId;

	private String artifactId;

	private String version;

	private Boolean isUsedRedis = Boolean.FALSE;

	private Boolean isUsedMongodb = Boolean.FALSE;
}
