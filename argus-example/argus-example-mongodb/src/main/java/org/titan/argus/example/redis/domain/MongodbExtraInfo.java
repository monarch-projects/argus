package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbExtraInfo {
	/**
	 * 当前数据库实例使用的堆空间的总大小（以字节为单位）。仅适用于Unix / Linux系统
	 */
	private Long heapUsageBytes;

	/**
	 * 加载磁盘内容时发生页错误的次数
	 */
	private Integer pageFaults;
}
