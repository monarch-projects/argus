package org.titan.argus.tools.monitor.mongodb.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * 全局锁信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbGlobalLockInfo {
	/**
	 * 自实例启动全局锁创建以来到现在多长时间，单位微秒
	 */
	private Long totalTime;

	private MongodbGlobalLockCurrentQueueInfo mongodbGlobalLockCurrentQueueInfo;

	private MongodbGlobalLockActiveClientsInfo mongodbGlobalLockActiveClientsInfo;
	
}
