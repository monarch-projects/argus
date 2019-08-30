package org.titan.argus.client.endpoint;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.client.entities.ArgusJvmInfo;
import org.titan.argus.client.entities.ArgusThreadInfo;
import org.titan.argus.client.entities.BaseMemoryInfo;
import org.titan.argus.client.entities.args.ArgusJvmArgsInfo;
import org.titan.argus.client.entities.heap.ArgusEdenSpaceInfo;
import org.titan.argus.client.entities.heap.ArgusHeapInfo;
import org.titan.argus.client.entities.heap.ArgusOldGenInfo;
import org.titan.argus.client.entities.heap.ArgusSurvivorSpaceInfo;
import org.titan.argus.client.entities.nonheap.ArgusCodeCacheInfo;
import org.titan.argus.client.entities.nonheap.ArgusCompressedClassSpaceInfo;
import org.titan.argus.client.entities.nonheap.ArgusMetaSpaceInfo;
import org.titan.argus.client.entities.nonheap.ArgusNonHeapInfo;
import org.titan.argus.client.enums.ArgusJvmEnum;
import sun.management.HotSpotDiagnostic;

import java.lang.management.*;
import java.util.*;


/**
 * @author starboyate
 *	jvm endpoint
 */
@RestControllerEndpoint(id = "jvm")
public class ArgusJvmEndpoint {
	private Logger logger = LoggerFactory.getLogger(ArgusJvmEndpoint.class);

	private MemoryMXBean memoryMXBean;

	private List<MemoryPoolMXBean> memoryPoolMXBeans;

	private ThreadMXBean threadMXBean;

	private HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean;

	public void init() {
		this.memoryMXBean = ManagementFactory.getMemoryMXBean();
		this.memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
		this.threadMXBean = ManagementFactory.getThreadMXBean();
		this.hotSpotDiagnosticMXBean = new HotSpotDiagnostic();
	}

	@GetMapping
	public ArgusJvmInfo getJvmInfo() {

		return ArgusJvmInfo.builder()
				.argusHeapInfo(getHeapInfo())
				.argusThreadInfo(getThreadInfo())
				.argusNonHeapInfo(getNonHeapInfo())
				.argusJvmArgsInfos(getDiagnosticOptions())
				.build();
	}

	@PutMapping("/args")
	public Map updateJvmOption(@RequestBody Map<String, String> map) {
		Map<String, String> responseMap = new HashMap<>(map);
		map.forEach((k, v) -> {
			try {
				this.hotSpotDiagnosticMXBean.setVMOption(k, v);
			} catch (Exception ex) {
				logger.error("Dynamic modification of jvm argus failed：{}", ex.getMessage());
				responseMap.remove(k);
			}
		});
		return responseMap;
	}


	private ArgusHeapInfo getHeapInfo() {
		ArgusEdenSpaceInfo argusEdenSpaceInfo = getT(new ArgusEdenSpaceInfo(), ArgusJvmEnum.EDEN.getName());
		ArgusSurvivorSpaceInfo argusSurvivorSpaceInfo = getT(new ArgusSurvivorSpaceInfo(), ArgusJvmEnum.SURVIVOR.getName());
		ArgusOldGenInfo argusOldGenInfo = getT(new ArgusOldGenInfo(), ArgusJvmEnum.OLD.getName());
		MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
		return new ArgusHeapInfo(heapMemoryUsage.getInit(), heapMemoryUsage.getMax(), heapMemoryUsage.getUsed(), heapMemoryUsage.getCommitted(), argusEdenSpaceInfo, argusSurvivorSpaceInfo, argusOldGenInfo);
	}

	private ArgusNonHeapInfo getNonHeapInfo() {
		ArgusMetaSpaceInfo argusMetaSpaceInfo = getT(new ArgusMetaSpaceInfo(), ArgusJvmEnum.META.getName());
		ArgusCompressedClassSpaceInfo argusCompressedClassSpaceInfo = getT(new ArgusCompressedClassSpaceInfo(), ArgusJvmEnum.COMPRESSED.getName());
		ArgusCodeCacheInfo argusCodeCacheInfo = getT(new ArgusCodeCacheInfo(), ArgusJvmEnum.CODE.getName());
		MemoryUsage nonHeapMemoryUsage  = memoryMXBean.getNonHeapMemoryUsage();
		return new ArgusNonHeapInfo(nonHeapMemoryUsage.getInit(), nonHeapMemoryUsage.getMax(), nonHeapMemoryUsage.getUsed(), nonHeapMemoryUsage.getCommitted(), argusMetaSpaceInfo, argusCompressedClassSpaceInfo, argusCodeCacheInfo);
	}

	private ArgusThreadInfo getThreadInfo() {
		return ArgusThreadInfo.builder()
				.currentThreadCpuTime(threadMXBean.getCurrentThreadCpuTime())
				.currentThreadUserTime(threadMXBean.getCurrentThreadUserTime())
				.daemonThreadCount(threadMXBean.getDaemonThreadCount())
				.peakThreadCount(threadMXBean.getPeakThreadCount())
				.threadCount(threadMXBean.getThreadCount())
				.build();
	}

	private List<ArgusJvmArgsInfo> getDiagnosticOptions() {
		List<VMOption> diagnosticOptions = this.hotSpotDiagnosticMXBean.getDiagnosticOptions();
		List<ArgusJvmArgsInfo> argusJvmArgsInfos = new ArrayList<>(diagnosticOptions.size());
		diagnosticOptions.forEach(item -> {
			argusJvmArgsInfos.add(ArgusJvmArgsInfo.builder().name(item.getName()).value(item.getValue()).build());
		});
		return argusJvmArgsInfos;
	}


	private <T extends BaseMemoryInfo>T getT(T t, String name) {
		for (MemoryPoolMXBean poolMXBean : memoryPoolMXBeans) {
			if (poolMXBean.getName().equals(name)) {
				MemoryUsage usage = poolMXBean.getUsage();
				t.setInitSize(usage.getInit());
				t.setMaxSize(usage.getMax());
				t.setUsedSize(usage.getUsed());
				t.setActualSize(usage.getCommitted());
			}
		}
		return t;
	}





}


