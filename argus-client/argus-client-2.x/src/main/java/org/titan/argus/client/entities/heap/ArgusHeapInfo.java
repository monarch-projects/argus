package org.titan.argus.client.entities.heap;


import lombok.Getter;
import lombok.Setter;
import org.titan.argus.client.entities.BaseMemoryInfo;

/**
 * @author starboyate
 * jvm heap 信息
 */
@Getter
@Setter
public class ArgusHeapInfo extends BaseMemoryInfo {

	private ArgusEdenSpaceInfo argusEdenSpaceInfo;

	private ArgusSurvivorSpaceInfo argusSurvivorSpaceInfo;

	private ArgusOldGenInfo argusOldGenInfo;

	public ArgusHeapInfo(long initSize, long maxSize, long usedSize, long actualSize, ArgusEdenSpaceInfo argusEdenSpaceInfo, ArgusSurvivorSpaceInfo argusSurvivorSpaceInfo, ArgusOldGenInfo argusOldGenInfo) {
		super(initSize, maxSize, usedSize, actualSize);
		this.argusEdenSpaceInfo = argusEdenSpaceInfo;
		this.argusSurvivorSpaceInfo = argusSurvivorSpaceInfo;
		this.argusOldGenInfo = argusOldGenInfo;
	}
}
