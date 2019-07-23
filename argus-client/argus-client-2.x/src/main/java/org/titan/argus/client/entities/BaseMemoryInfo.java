package org.titan.argus.client.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseMemoryInfo {
	/**
	 * 初始化大小
	 */
	private Long initSize;

	/**
	 * 最大大小
	 */
	private Long maxSize;

	/**
	 * 已使用大小
	 */
	private Long usedSize;

	/**
	 * 实际大小（jvm实际向操作系统分配的大小）
	 */
	private Long actualSize;
}
