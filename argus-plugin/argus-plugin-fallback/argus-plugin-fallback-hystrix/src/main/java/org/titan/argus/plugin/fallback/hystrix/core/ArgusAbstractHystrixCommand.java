package org.titan.argus.plugin.fallback.hystrix.core;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.cache.CacheInvocationContext;
import com.netflix.hystrix.contrib.javanica.cache.HystrixCacheKeyGenerator;
import com.netflix.hystrix.contrib.javanica.cache.HystrixGeneratedCacheKey;
import com.netflix.hystrix.contrib.javanica.cache.HystrixRequestCacheManager;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.*;
import com.netflix.hystrix.contrib.javanica.exception.CommandActionExecutionException;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import java.util.Collection;
import java.util.List;

/**
 * @author starboyate
 * @param <T>
 */
public abstract class ArgusAbstractHystrixCommand<T> extends com.netflix.hystrix.HystrixCommand<T> {
	private final CommandActions commandActions;
	private final CacheInvocationContext<CacheResult> cacheResultInvocationContext;
	private final CacheInvocationContext<CacheRemove> cacheRemoveInvocationContext;
	private final Collection<HystrixCollapser.CollapsedRequest<Object, Object>> collapsedRequests;
	private final List<Class<? extends Throwable>> ignoreExceptions;
	private final ExecutionType executionType;
	private final HystrixCacheKeyGenerator defaultCacheKeyGenerator = HystrixCacheKeyGenerator.getInstance();

	protected ArgusAbstractHystrixCommand(ArgusHystrixCommandBuilder builder) {
		super(builder.getSetterBuilder().build());
		this.commandActions = builder.getCommandActions();
		this.collapsedRequests = builder.getCollapsedRequests();
		this.cacheResultInvocationContext = builder.getCacheResultInvocationContext();
		this.cacheRemoveInvocationContext = builder.getCacheRemoveInvocationContext();
		this.ignoreExceptions = builder.getIgnoreExceptions();
		this.executionType = builder.getExecutionType();
	}

	/**
	 * Gets command action.
	 *
	 * @return command action
	 */
	protected CommandAction getCommandAction() {
		return commandActions.getCommandAction();
	}

	/**
	 * Gets fallback action.
	 *
	 * @return fallback action
	 */
	protected CommandAction getFallbackAction() {
		return commandActions.getFallbackAction();
	}

	/**
	 * Gets collapsed requests.
	 *
	 * @return collapsed requests
	 */
	protected Collection<HystrixCollapser.CollapsedRequest<Object, Object>> getCollapsedRequests() {
		return collapsedRequests;
	}

	/**
	 * Gets exceptions types which should be ignored.
	 *
	 * @return exceptions types
	 */
	protected List<Class<? extends Throwable>> getIgnoreExceptions() {
		return ignoreExceptions;
	}

	protected ExecutionType getExecutionType() {
		return executionType;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	protected String getCacheKey() {
		String key = null;
		if (cacheResultInvocationContext != null) {
			HystrixGeneratedCacheKey hystrixGeneratedCacheKey =
					defaultCacheKeyGenerator.generateCacheKey(cacheResultInvocationContext);
			key = hystrixGeneratedCacheKey.getCacheKey();
		}
		return key;
	}

	/**
	 * Check whether triggered exception is ignorable.
	 *
	 * @param throwable the exception occurred during a command execution
	 * @return true if exception is ignorable, otherwise - false
	 */
	boolean isIgnorable(Throwable throwable) {
		if (ignoreExceptions == null || ignoreExceptions.isEmpty()) {
			return false;
		}
		for (Class<? extends Throwable> ignoreException : ignoreExceptions) {
			if (ignoreException.isAssignableFrom(throwable.getClass())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Executes an action. If an action has failed and an exception is ignorable then propagate it as HystrixBadRequestException
	 * otherwise propagate original exception to trigger fallback method.
	 * Note: If an exception occurred in a command directly extends {@link java.lang.Throwable} then this exception cannot be re-thrown
	 * as original exception because HystrixCommand.run() allows throw subclasses of {@link java.lang.Exception}.
	 * Thus we need to wrap cause in RuntimeException, anyway in this case the fallback logic will be triggered.
	 *
	 * @param action the action
	 * @return result of command action execution
	 */
	Object process(ArgusAbstractHystrixCommand.Action action) throws Exception {
		Object result;
		try {
			result = action.execute();
			flushCache();
		} catch (CommandActionExecutionException throwable) {
			Throwable cause = throwable.getCause();
			if (isIgnorable(cause)) {
				throw new HystrixBadRequestException(cause.getMessage(), cause);
			}
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			} else if (cause instanceof Exception) {
				throw (Exception) cause;
			} else {
				// instance of Throwable
				throw new CommandActionExecutionException(cause);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	protected abstract T run() throws Exception;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	protected T getFallback() {
		throw new RuntimeException("No fallback available.", getExecutionException());
	}

	/**
	 * Clears cache for the specified hystrix command.
	 */
	protected void flushCache() {
		if (cacheRemoveInvocationContext != null) {
			HystrixRequestCacheManager.getInstance().clearCache(cacheRemoveInvocationContext);
		}
	}

	/**
	 * Common action.
	 */
	abstract class Action {
		/**
		 * Each implementation of this method should wrap any exceptions in CommandActionExecutionException.
		 *
		 * @return execution result
		 * @throws CommandActionExecutionException
		 */
		abstract Object execute() throws CommandActionExecutionException;
	}


	/**
	 * Builder to create error message for failed fallback operation.
	 */
	static class FallbackErrorMessageBuilder {
		private StringBuilder builder = new StringBuilder("failed to processed fallback");

		static ArgusAbstractHystrixCommand.FallbackErrorMessageBuilder create() {
			return new ArgusAbstractHystrixCommand.FallbackErrorMessageBuilder();
		}

		public ArgusAbstractHystrixCommand.FallbackErrorMessageBuilder append(CommandAction action, Throwable throwable) {
			return commandAction(action).exception(throwable);
		}

		private ArgusAbstractHystrixCommand.FallbackErrorMessageBuilder commandAction(CommandAction action) {
			if (action instanceof CommandExecutionAction || action instanceof LazyCommandExecutionAction) {
				builder.append(": '").append(action.getActionName()).append("'. ")
						.append(action.getActionName()).append(" fallback is a hystrix command. ");
			} else if (action instanceof MethodExecutionAction) {
				builder.append(" is the method: '").append(action.getActionName()).append("'. ");
			}
			return this;
		}

		private ArgusAbstractHystrixCommand.FallbackErrorMessageBuilder exception(Throwable throwable) {
			if (throwable instanceof HystrixBadRequestException) {
				builder.append("exception: '").append(throwable.getCause().getClass())
						.append("' occurred in fallback was ignored and wrapped to HystrixBadRequestException.\n");
			} else if (throwable instanceof HystrixRuntimeException) {
				builder.append("exception: '").append(throwable.getCause().getClass())
						.append("' occurred in fallback wasn't ignored.\n");
			}
			return this;
		}

		public String build() {
			return builder.toString();
		}
	}
}
