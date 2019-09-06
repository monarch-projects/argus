package org.titan.argus.example.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * 全局锁排队的操作数的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbGlobalLockCurrentQueueInfo {
	/**
	 * 当前等待全局锁的数量
	 */
	private Integer total;

	/**
	 * 当前等待读锁的数量
	 */
	private Integer readers;

	/**
	 * 当前等待写锁的数量
	 */
	private Integer writers;
}
