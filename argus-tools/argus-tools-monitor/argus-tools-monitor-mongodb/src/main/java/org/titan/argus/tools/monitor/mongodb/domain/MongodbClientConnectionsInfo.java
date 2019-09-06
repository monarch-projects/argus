package org.titan.argus.tools.monitor.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author starboyate
 * 客户端连接数指标
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MongodbClientConnectionsInfo {
	/**
	 * 当前连接到本机处于活动状态的连接数
	 */
	private Integer current;

	/**
	 * 剩余可供连接的数量
	 */
	private Integer available;

	/**
	 * 总共创建的连接数
	 */
	private Integer totalCreated;

	/**
	 * 当前存活的连接数
	 */
	private Integer active;

	/**
	 * 处于活动状态的客户端中有多少是在执行read操作
	 */
	private Integer activeClientsReaders;

	/**
	 * 处于活动状态的客户端中有多少是在执行write操作
	 */
	private Integer activeClientsWriters;
}
