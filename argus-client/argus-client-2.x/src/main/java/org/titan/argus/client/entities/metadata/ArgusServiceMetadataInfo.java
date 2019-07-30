package org.titan.argus.client.entities.metadata;


import lombok.Data;

/**
 * @author starboyate
 */
@Data
public class ArgusServiceMetadataInfo {

	private String groupId;

	private String artifactId;

	private String version;

	private String springBootVersion;

	private String springCloudVersion;

}
