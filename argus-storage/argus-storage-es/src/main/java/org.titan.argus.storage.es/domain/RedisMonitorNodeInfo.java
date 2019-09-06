package org.titan.argus.storage.es.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starboyate
 */
@Data
@Document(indexName = "redis_monitor_info_index")
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RedisMonitorNodeInfo implements Serializable {
	@Id
	@Field(type = FieldType.Long)
	@NonNull
	private Long id;

	@Field(type = FieldType.Keyword)
	private String ip;

	/**
	 * 响应时间，单位毫秒
	 */
	@Field(type = FieldType.Long)
	@NonNull
	private Long responseTime;

	/**
	 * 已连接客户端数量
	 */
	@Field(type = FieldType.Long)
	private Long connectedClients;

	/**
	 * 阻塞客户端连接总数
	 */
	@Field(type = FieldType.Long)
	private Long blockedClients;

	@Field(type = FieldType.Long)
	private Long usedMemory;

	/**
	 * 操作系统分配给redis的总内存
	 */
	@Field(type = FieldType.Long)
	private long usedMemoryRss;

	/**
	 * 内存消耗峰值
	 */
	@Field(type = FieldType.Long)
	private long usedMemoryPeak;

	/**
	 * used_memory_rss和used_memory之间的比率
	 */
	@Field(type = FieldType.Long)
	private float memFragmentationRatio;

	/**
	 * 服务器接受的连接总数
	 */
	@Field(type = FieldType.Long)
	private long totalConnectionsReceived;

	/**
	 * 服务器处理的命令总数
	 */
	@Field(type = FieldType.Long)
	private long totalCommandsProcessed;

	/**
	 * 每秒处理的命令数
	 */
	@Field(type = FieldType.Long)
	private long instantaneousOpsPerSec;

	/**
	 * 从网络读取的总字节数
	 */
	@Field(type = FieldType.Long)
	private long totalNetInputBytes;

	/**
	 * 写入网络的总字节数
	 */
	@Field(type = FieldType.Long)
	private long totalNetOutputBytes;

	/**
	 * 网络的每秒读取速率，以KB /秒为单位
	 */
	@Field(type = FieldType.Float)
	private Float instantaneousInputKbps;

	/**
	 * 网络的每秒写入速率，以KB /秒为单位
	 */
	@Field(type = FieldType.Float)
	private Float instantaneousOutputKbps;

	/**
	 * 由于maxclients限制而拒绝的连接数
	 */
	@Field(type = FieldType.Long)
	private long rejectedConnections;

	/**
	 * Redis服务器消耗的系统CPU
	 */
	@Field(type = FieldType.Float)
	private Float usedCpuSys;

	/**
	 * Redis服务器消耗的用户CPU
	 */
	@Field(type = FieldType.Float)
	private Float usedCpuUser;

	/**
	 * 后台进程占用的系统CPU
	 */
	@Field(type = FieldType.Float)
	private Float usedCpuSysChildren;

	/**
	 * 后台进程占用的用户CPU
	 */
	@Field(type = FieldType.Float)
	private Float usedCpuUserChildren;

	@Field(type = FieldType.Integer)
	private Integer totalKeys;

	@Field(type = FieldType.Long)
	private Long createTime;
}
