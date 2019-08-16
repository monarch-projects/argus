package org.titan.argus.plugin.fallback.hystrix.core;

import com.netflix.hystrix.contrib.javanica.command.*;
import com.netflix.hystrix.contrib.javanica.exception.FallbackInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.netflix.hystrix.contrib.javanica.exception.ExceptionUtils.unwrapCause;
import static com.netflix.hystrix.contrib.javanica.utils.CommonUtils.createArgsForFallback;


public class ArgusHystrixCommand extends ArgusAbstractHystrixCommand<Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericCommand.class);

	public ArgusHystrixCommand(ArgusHystrixCommandBuilder builder) {
		super(builder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object run() throws Exception {
		LOGGER.debug("execute command: {}", getCommandKey().name());
		return process(new ArgusAbstractHystrixCommand.Action() {
			@Override
			Object execute() {
				return getCommandAction().execute(getExecutionType());
			}
		});
	}

	/**
	 * The fallback is performed whenever a command execution fails.
	 * Also a fallback method will be invoked within separate command in the case if fallback method was annotated with
	 * HystrixCommand annotation, otherwise current implementation throws RuntimeException and leaves the caller to deal with it
	 * (see {@link super#getFallback()}).
	 * The getFallback() is always processed synchronously.
	 * Since getFallback() can throw only runtime exceptions thus any exceptions are thrown within getFallback() method
	 * are wrapped in {@link FallbackInvocationException}.
	 * A caller gets {@link com.netflix.hystrix.exception.HystrixRuntimeException}
	 * and should call getCause to get original exception that was thrown in getFallback().
	 *
	 * @return result of invocation of fallback method or RuntimeException
	 */
	@Override
	protected Object getFallback() {
		final CommandAction commandAction = getFallbackAction();
		if (commandAction != null) {
			try {
				return process(new ArgusAbstractHystrixCommand.Action() {
					@Override
					Object execute() {
						MetaHolder metaHolder = commandAction.getMetaHolder();
						Object[] args = createArgsForFallback(metaHolder, getExecutionException());
						return commandAction.executeWithArgs(metaHolder.getFallbackExecutionType(), args);
					}
				});
			} catch (Throwable e) {
				LOGGER.error(ArgusAbstractHystrixCommand.FallbackErrorMessageBuilder.create()
						.append(commandAction, e).build());
				throw new FallbackInvocationException(unwrapCause(e));
			}
		} else {
			return super.getFallback();
		}
	}
}
