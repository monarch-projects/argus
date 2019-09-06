package org.titan.argus.tools.monitor.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * 复制同步发生oplog具体信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbReplApplyInfo {
	/**
	 * 应用的oplog操作总数
	 */
	private Long batchSize;

	private MongodbReplicationApplyBatchesInfo mongodbReplicationApplyBatchesInfo;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class MongodbReplicationApplyBatchesInfo {
		/**
		 * 所有数据库中应用的批次总数
		 */
		private Integer num;

		/**
		 * mongod从oplog应用操作所花费的总时间（以毫秒为单位）
		 */
		private Integer totalMillis;
	}
}
