package org.titan.argus.client.entities;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 * jvm thread使用情况
 */
@Data
@Builder
public class ArgusThreadInfo {
	/**
	 * 活动线程数量
	 */
	private Integer threadCount;

	/**
	 * java虚拟机启动或峰值重置以来峰值活动线程计数
	 */
	private Integer peakThreadCount;

	/**
	 * 当前线程的总CPU时间
	 */
	private Long currentThreadCpuTime;

	/**
	 * 活动守护线程数量
	 */
	private Integer daemonThreadCount;

	/**
	 * 当前线程在用户模式中执行的CPU时间
	 */
	private Long currentThreadUserTime;
}
