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
public class MongodbReplBufferInfo {
	/**
	 * oplog缓冲区中的当前操作数
	 */
	private Long count;

	/**
	 * 缓冲区的最大大小。此值是一个常量设置，mongod不可配置
	 */
	private Long maxSizeBytes;

	/**
	 * oplog缓冲区内容的当前大小
	 */
	private Long sizeBytes;
}
