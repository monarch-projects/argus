package org.titan.argus.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author starboyate
 */
public final class ProcessUtil {

	private ProcessUtil(){}

	/**
	 * 获取当前进程ID
	 * @return
	 */
	public static Integer getProcessId() {
		RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
		return Integer.valueOf(mxBean.getName().split("@")[0]);
	}

}
