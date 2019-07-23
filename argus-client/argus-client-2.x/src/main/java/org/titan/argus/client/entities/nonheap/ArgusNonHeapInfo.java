package org.titan.argus.client.entities.nonheap;

import lombok.Getter;
import lombok.Setter;
import org.titan.argus.client.entities.BaseMemoryInfo;

/**
 * @author starboyate
 */
@Getter
@Setter
public class ArgusNonHeapInfo extends BaseMemoryInfo {
	private ArgusMetaSpaceInfo argusMetaSpaceInfo;

	private ArgusCompressedClassSpaceInfo argusCompressedClassSpaceInfo;

	private ArgusCodeCacheInfo argusCodeCacheInfo;

	public ArgusNonHeapInfo(long initSize, long maxSize, long usedSize, long actualSize, ArgusMetaSpaceInfo argusMetaSpaceInfo, ArgusCompressedClassSpaceInfo argusCompressedClassSpaceInfo, ArgusCodeCacheInfo argusCodeCacheInfo) {
		super(initSize, maxSize, usedSize, actualSize);
		this.argusMetaSpaceInfo = argusMetaSpaceInfo;
		this.argusCompressedClassSpaceInfo = argusCompressedClassSpaceInfo;
		this.argusCodeCacheInfo = argusCodeCacheInfo;
	}
}
