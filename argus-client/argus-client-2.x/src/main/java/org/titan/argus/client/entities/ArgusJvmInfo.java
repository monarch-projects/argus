package org.titan.argus.client.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.titan.argus.client.entities.heap.ArgusHeapInfo;
import org.titan.argus.client.entities.nonheap.ArgusNonHeapInfo;

/**
 * @author starboyate
 * jvm信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArgusJvmInfo {
	private ArgusHeapInfo argusHeapInfo;

	private ArgusNonHeapInfo argusNonHeapInfo;

	private ArgusThreadInfo argusThreadInfo;

}
