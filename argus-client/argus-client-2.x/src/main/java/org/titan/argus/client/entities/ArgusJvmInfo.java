package org.titan.argus.client.entities;

import com.sun.management.VMOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.titan.argus.client.entities.args.ArgusJvmArgsInfo;
import org.titan.argus.client.entities.heap.ArgusHeapInfo;
import org.titan.argus.client.entities.nonheap.ArgusNonHeapInfo;

import java.util.List;

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

	private List<ArgusJvmArgsInfo> argusJvmArgsInfos;

}
