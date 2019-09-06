package org.titan.argus.example.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author starboyate
 * 提供有关服务器会话缓存的指标
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbLogicalSessionRecordCacheInfo {
	/**
	 * 上次刷新周期以来由mongod或mongos实例在内存中缓存的所有活动本地会话的数量
	 */
	private Integer activeSessionsCount;

	/**
	 * 刷新进程在config.system.sessions集合上运行的次数的数字
	 */
	private Integer sessionsCollectionJobCount;

	/**
	 * 上次刷新的长度（以毫秒为单位）
	 */
	private Long lastSessionsCollectionJobDurationMillis;

	/**
	 * 上次刷新的时间
	 */
	private Date lastSessionsCollectionJobTimestamp;

	/**
	 * 上次刷新期间刷新的会话数
	 */
	private Integer lastSessionsCollectionJobEntriesRefreshed;

	/**
	 * 上次config.system.sessions收集刷新期间关闭的游标数
	 */
	private Integer lastSessionsCollectionJobEntriesEnded;

	/**
	 * 上次config.system.sessions收集刷新期间关闭的游标数
	 */
	private Integer lastSessionsCollectionJobCursorsClosed;

	/**
	 * 事务记录清理过程在config.transactions 集合上运行的次数的数字
	 */
	private Integer transactionReaperJobCount;

	/**
	 * 上次事务记录清理的长度（以毫秒为单位）
	 */
	private Integer lastTransactionReaperJobDurationMillis;

	/**
	 * 最后一次交易记录清理的时间
	 */
	private Date lastTransactionReaperJobTimestamp;

	/**
	 * config.transactions在上次事务记录清理期间删除的集合中的条目数
	 */
	private Integer lastTransactionReaperJobEntriesCleanedUp;
}
