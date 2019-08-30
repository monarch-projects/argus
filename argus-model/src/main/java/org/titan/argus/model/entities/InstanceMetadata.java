package org.titan.argus.model.entities;

import lombok.*;
import org.titan.argus.model.vo.InstanceMetadataVO;

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

	public InstanceMetadataVO convertToInstanceMetadataVO() {
		return InstanceMetadataVO.builder()
				.appName(this.appName)
				.artifactId(this.artifactId)
				.eventMap(this.eventMap)
				.groupId(this.groupId)
				.id(this.id)
				.ip(this.ip)
				.isGateway(this.isGateway)
				.isUsedRabbitMQ(this.isUsedRabbitMQ)
				.isUsedRedis(this.isUsedRedis)
				.port(this.port)
				.status(this.status)
				.version(this.version)
				.build();
	}
}
