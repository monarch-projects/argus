package org.titan.argus.example.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * 复制同步过程中网络的具体信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbReplNetworkInfo {
	/**
	 * 从复制同步源读取的数据总量
	 */
	private Long bytes;

	private MongodbReplicationNetworkGetMoreInfo mongodbReplicationNetworkGetMoreInfo;
	/**
	 * 从复制源读取的操作总数
	 */
	private Long ops;

	/**
	 * 创建的oplog查询进程总数
	 */
	private Long readersCreated;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class MongodbReplicationNetworkGetMoreInfo {
		/**
		 * 复制同步getMore操作总数
		 */
		private Long num;

		/**
		 * 复制同步中getmore操作中收集数据所需的总时间
		 */
		private Long totalMillis;
	}
}
