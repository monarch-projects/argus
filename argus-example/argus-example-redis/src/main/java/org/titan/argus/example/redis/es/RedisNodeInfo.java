package org.titan.argus.example.redis.es;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author starboyate
 */
@Data
@Document(indexName = "redis_monitor_index")
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RedisNodeInfo implements Serializable {
	@Id
	@Field(type = FieldType.Long)
	@NonNull
	private Long id;


	@Field(type = FieldType.Long)
	private Long connectedClients;

	@Field(type = FieldType.Long)
	private Long blockedClients;

	@Field(type = FieldType.Long)
	@NonNull
	private Long totalKeys;

	@Field(type = FieldType.Keyword)
	private String ip;

	@Field(type = FieldType.Long)
	@NonNull
	private Long responseTime;

	@Field(type = FieldType.Long)
	private long usedMemoryRss;

	@Field(type = FieldType.Long)
	private long usedMemoryPeak;

	@Field(type = FieldType.Long)
	private float memFragmentationRatio;

	@Field(type = FieldType.Long)
	private long aofEnabled;

	@Field(type = FieldType.Long)
	private long totalConnectionsReceived;

	@Field(type = FieldType.Long)
	private long totalCommandsProcessed;

	@Field(type = FieldType.Long)
	private long instantaneousOpsPerSec;

	@Field(type = FieldType.Long)
	private long totalNetInputBytes;

	@Field(type = FieldType.Long)
	private long totalNetOutputBytes;

	@Field(type = FieldType.Long)
	private float instantaneousInputKbps;

	@Field(type = FieldType.Long)
	private float instantaneousOutputKbps;

	@Field(type = FieldType.Long)
	private long rejectedConnections;

	@Field(type = FieldType.Long)
	private long syncFull;

	@Field(type = FieldType.Long)
	private long syncPartialOk;

	@Field(type = FieldType.Long)
	private long syncPartialErr;

	@Field(type = FieldType.Long)
	private long expiredKeys;

	@Field(type = FieldType.Long)
	private long evictedKeys;

	@Field(type = FieldType.Long)
	private long keyspaceHits;

	@Field(type = FieldType.Long)
	private long keyspaceMisses;

	@Field(type = FieldType.Long)
	private long pubsubChannels;

	@Field(type = FieldType.Long)
	private long pubsubPatterns;

	@Field(type = FieldType.Long)
	private long latestForkUsec;

	@Field(type = FieldType.Long)
	private long migrateCachedSockets;

	@Field(type = FieldType.Long)
	private float usedCpuSys;

	@Field(type = FieldType.Long)
	private float usedCpuUser;

	@Field(type = FieldType.Long)
	private float usedCpuSysChildren;

	@Field(type = FieldType.Long)
	private float usedCpuUserChildren;

	@Field(type = FieldType.Long)
	private long usedMemory;

	@Field(type = FieldType.Long)
	private long expires;

	@Field(type = FieldType.Long)
	private long avgTtl;

}
