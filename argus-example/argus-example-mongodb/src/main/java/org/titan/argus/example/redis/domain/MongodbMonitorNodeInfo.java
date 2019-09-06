package org.titan.argus.example.redis.domain;

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
public class MongodbMonitorNodeInfo {
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
	 * 目前为止总共使用的物理内存,单位是MB
	 */
	private Long memResident;

	/**
	 * 显示mongod进程使用的虚拟内存的数量（以兆字节（MB）为单位）
	 */
	private Long memVirtual;
}
