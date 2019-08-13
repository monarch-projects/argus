package org.titan.argus.discovery.common.disruptor.handler;

import com.lmax.disruptor.ExceptionHandler;

/**
 * @author starboyate
 */
public class InstanceExceptionHandler implements ExceptionHandler<Object> {
	@Override
	public void handleEventException(Throwable throwable, long l, Object o) {
		throwable.printStackTrace();
	}

	@Override
	public void handleOnStartException(Throwable throwable) {

	}

	@Override
	public void handleOnShutdownException(Throwable throwable) {

	}
}
