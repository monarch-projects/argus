package org.titan.argus.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author starboyate
 */
public final class ArgusVisitsUtil {
	private static  final AtomicLong COUNT = new AtomicLong(0);

	public static long incr() {
		return COUNT.incrementAndGet();
	}

	public static long get() {
		return COUNT.get();
	}

}
