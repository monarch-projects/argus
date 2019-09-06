package org.titan.argus.example.redis.copy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author starboyate
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MongodbMonitorNodeInfoCopy {
	/**
	 * 唯一标识
	 */
	private String id;

	/**
	 * host
	 */
	private String host;

	/**
	 * mongodb版本
	 */
	private String version;

	/**
	 * 实例启动经过的时间
	 */
	private Long uptime;

	/**
	 * 响应时间
	 */
	private Long responseTime;

	/**
	 * 当前连接到本机处于活动状态的连接数
	 */
	private Integer clientConnectionsCurrent;

	/**
	 * 剩余可供连接的数量
	 */
	private Integer clientConnectionsAvailable;

	/**
	 * 总共创建的连接数
	 */
	private Integer clientConnectionsTotalCreated;

	/**
	 * 当前存活的连接数
	 */
	private Integer clientConnectionsActive;

	/**
	 * 处于活动状态的客户端中有多少是在执行read操作
	 */
	private Integer clientConnectionsActiveClientsReaders;

	/**
	 * 处于活动状态的客户端中有多少是在执行write操作
	 */
	private Integer clientConnectionsActiveClientsWriters;


	/**
	 * 自实例启动全局锁创建以来到现在多长时间，单位微秒
	 */
	private Long globalLockTotalTime;

	/**
	 * 当前等待全局锁的数量
	 */
	private Integer globalLockTotal;

	/**
	 * 当前等待读锁的数量
	 */
	private Integer globalLockReaders;

	/**
	 * 当前等待写锁的数量
	 */
	private Integer globalLockWriters;

	/**
	 * 自上次刷新周期以来由mongod或mongos实例在内存中缓存的所有活动本地会话的数量
	 */
	private Integer logicalSessionRecordCacheActiveSessionsCount;

	/**
	 * 刷新进程在config.system.sessions集合上运行的次数的数字
	 */
	private Integer logicalSessionRecordCacheSessionsCollectionJobCount;

	/**
	 * 上次刷新的长度（以毫秒为单位）
	 */
	private Long logicalSessionRecordCacheLastSessionsCollectionJobDurationMillis;

	/**
	 * 上次刷新的时间
	 */
	private Date logicalSessionRecordCacheLastSessionsCollectionJobTimestamp;

	/**
	 * 上次刷新期间刷新的会话数
	 */
	private Integer logicalSessionRecordCacheLastSessionsCollectionJobEntriesRefreshed;

	/**
	 * 上次刷新期间结束的会话数
	 */
	private Integer logicalSessionRecordCacheLastSessionsCollectionJobEntriesEnded;

	/**
	 * 上次config.system.sessions收集刷新期间关闭的游标数
	 */
	private Integer logicalSessionRecordCacheLastSessionsCollectionJobCursorsClosed;

	/**
	 * 跟踪事务记录清理过程在config.transactions 集合上运行的次数的数字
	 */
	private Integer logicalSessionRecordCacheTransactionReaperJobCount;

	/**
	 * 上次事务记录清理的长度（以毫秒为单位）
	 */
	private Integer logicalSessionRecordCacheLastTransactionReaperJobDurationMillis;

	/**
	 * 最后一次交易记录清理的时间
	 */
	private Date logicalSessionRecordCacheLastTransactionReaperJobTimestamp;

	/**
	 * config.transactions在上次事务记录清理期间删除的集合中的条目数
	 */
	private Integer logicalSessionRecordCacheLastTransactionReaperJobEntriesCleanedUp;

	/**
	 * 数据库网络流量的接收量的字节数
	 */
	private Long networkBytesIn;

	/**
	 * 数据库发送的网络流量的字节数
	 */
	private Long networkBytesOut;

	/**
	 * 服务器已收到的不同请求的总数
	 */
	private Long networkNumRequests;

	/**
	 * 自mongod上次启动实例以来收到的插入操作总数
	 */
	private Integer opcountersInsert;

	/**
	 * 自mongod 上次启动实例以来收到的查询总数
	 */
	private Integer opcountersQuery;

	/**
	 * 自mongod上次启动实例以来收到的更新操作总数
	 */
	private Integer opcountersUpdate;

	/**
	 * 自mongod 上次启动实例以来的删除操作总数
	 */
	private Integer opcountersDelete;

	/**
	 * 自mongod 上次启动实例以来“getmore”操作的总数。即使查询计数较低，此计数器也可能很高。辅助节点getMore作为复制过程的一部分发送操作
	 */
	private Integer opcountersGetMore;

	/**
	 * 自mongod上次启动实例以来向数据库发出的命令总数
	 */
	private Integer opcountersCommand;

	/**
	 * 当前存储引擎的名称
	 */
	private String storageEngineName;

	/**
	 * 指示存储引擎是否支持 读取问题
	 */
	private Boolean storageEngineSupportsCommittedReads;

	/**
	 * 指示存储引擎是否将数据持久保存到磁盘
	 */
	private Boolean storageEnginePersistent;


	/**
	 * 目前为止总共使用的物理内存,单位是MB
	 */
	private Long memResident;

	/**
	 * 显示mongod进程使用的虚拟内存的数量（以兆字节（MB）为单位）
	 */
	private Long memVirtual;

	/**
	 * 应用的oplog操作总数
	 */
	private Long replApplyBatchSize;

	/**
	 * 所有数据库中应用的批次总数
	 */
	private Integer replApplyBatchesNum;

	/**
	 * mongod从oplog应用操作所花费的总时间（以毫秒为单位）
	 */
	private Integer replApplyBatchesTotalMillis;

	/**
	 * oplog缓冲区中的当前操作数
	 */
	private Long replBufferCount;

	/**
	 * 缓冲区的最大大小。此值是一个常量设置，mongod不可配置
	 */
	private Long replBufferMaxSizeBytes;

	/**
	 * oplog缓冲区内容的当前大小
	 */
	private Long replBufferSizeBytes;


	/**
	 * 从复制同步源读取的数据总量
	 */
	private Long replNetworkBytes;

	/**
	 * 复制同步getMore操作总数
	 */
	private Long replNetworkGetMoresNum;

	/**
	 * 复制同步中getmore操作中收集数据所需的总时间
	 */
	private Long replNetworkGetMoresTotalMillis;

	/**
	 * 从复制源读取的操作总数
	 */
	private Long replNetworkOps;

	/**
	 * 创建的oplog查询进程总数
	 */
	private Long replNetworkReadersCreated;
}
