package org.titan.argus.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author starboyate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstanceMetadataVO {
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

	private Boolean isUsedMongodb;

}
