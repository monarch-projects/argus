package org.titan.argus.tools.monitor.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * redis监控指标
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisMetricInfo {
	/**
	 * redis分配的内存
	 */
	private Long usedMemory;

	/**
	 * 操作系统分配redis的内存
	 */
	private Long usedMemoryRss;

	/**
	 * redis消耗内存的峰值
	 */
	private Long usedMemoryPeak;

	/**
	 * redis内存碎片率
	 */
	private Float memFragmentationRatio;

	/**
	 * 已连接的客户端总数
	 */
	private Long connectedClients;

	/**
	 * 阻塞的连接数
	 */
	private Long blockedClients;

	/**
	 * 服务器接受的连接总数
	 */
	private Long totalConnectionsReceived;

	/**
	 * 服务器处理的命令总数
	 */
	private Long totalCommandsProcessed;

	/**
	 * 每秒处理的命令数
	 */
	private Long instantaneousOpsPerSec;

	/**
	 * 由于maxclients限制而拒绝的连接数
	 */
	private Long rejectedConnections;

	/**
	 * 网络的每秒读取速率，以KB /秒为单位
	 */
	private Float instantaneousInputKbps;

	/**
	 * 网络的每秒写入速率，以KB /秒为单位
	 */
	private Float instantaneousOutputKbps;

	/**
	 * Redis服务器消耗的系统CPU
	 */
	private Float usedCpuSys;

	/**
	 * Redis服务器消耗的用户CPU
	 */
	private Float usedCpuUser;

	/**
	 * 后台进程占用的系统CPU
	 */
	private Float usedCpuSysChildren;

	/**
	 * 后台进程占用的用户CPU
	 */
	private Float usedCpuUserChildren;

	/**
	 * 响应时间
	 */
	private Long responseTime;

	private Integer totalKeys;
}
