package org.titan.argus.example.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * mongdb复制信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbReplInfo {
	/**
	 * 复制oplog的具体信息
	 */
	private MongodbReplApplyInfo mongodbRelicationApplyInfo;

	/**
	 * 复制同步过程中缓冲区的具体信息
	 */
	private MongodbReplBufferInfo mongodbRelicationBufferInfo;

	/**
	 * 复制同步过程中网络的具体信息
	 */
	private MongodbReplNetworkInfo mongodbReplicationNetworkInfo;
}
