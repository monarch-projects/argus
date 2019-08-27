package org.titan.argus.model.entities;

import lombok.*;

import java.util.Map;

/**
 * @author starboyate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"isGateway", "groupId", "artifactId", "version", "isUsedRedis", "isUsedRedis"})
public class InstanceMetadata {
	private String id;

	private String appName;

	private String ip;

	private Integer port;

	private String status;

	private Map<String, String> eventMap;

	private Boolean isGateway;

	private String groupId;

	private String artifactId;

	private String version;

	private Boolean isUsedRedis;

	private Boolean isUsedRabbitMQ;
}
