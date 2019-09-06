package org.titan.argus.tools.monitor.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 *  已连接客户端数量以及这些客户端执行的读取和写入操作的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongodbGlobalLockActiveClientsInfo {
	/**
	 * 内部客户端连接总数,包含readers数和writers数
	 */
	private Integer total;

	/**
	 * 读取操作的活动客户端连接数
	 */
	private Integer readers;

	/**
	 * 写入操作的活动客户端连接数
	 */
	private Integer writers;
}
