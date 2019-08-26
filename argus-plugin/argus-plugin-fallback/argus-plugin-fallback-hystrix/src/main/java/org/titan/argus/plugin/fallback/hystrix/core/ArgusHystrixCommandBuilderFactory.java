package org.titan.argus.plugin.fallback.hystrix.core;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.*;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import com.netflix.hystrix.contrib.javanica.utils.MethodProvider;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.netflix.hystrix.contrib.javanica.cache.CacheInvocationContextFactory.createCacheRemoveInvocationContext;
import static com.netflix.hystrix.contrib.javanica.cache.CacheInvocationContextFactory.createCacheResultInvocationContext;
import static com.netflix.hystrix.contrib.javanica.utils.EnvUtils.isCompileWeaving;
import static com.netflix.hystrix.contrib.javanica.utils.ajc.AjcUtils.getAjcMethodAroundAdvice;

/**
 * @author starboyate
 */
public class ArgusHystrixCommandBuilderFactory {
	private static final ConcurrentHashMap<String, ArgusHystrixCommandBuilder> cache = new ConcurrentHashMap<>();
	private static final ArgusHystrixCommandBuilderFactory INSTANCE = new ArgusHystrixCommandBuilderFactory();

	public static ArgusHystrixCommandBuilderFactory getInstance() {
		return INSTANCE;
	}

	private ArgusHystrixCommandBuilderFactory() {

	}

	public ArgusHystrixCommandBuilder create(MetaHolder metaHolder, ArgusHystrixProperties argusHystrixProperties) {
		ArgusHystrixCommandBuilder temp = cache.get(metaHolder.getCommandKey());
		if (Objects.nonNull(temp) && Objects.isNull(argusHystrixProperties)) {
			return temp;
		}
		ArgusHystrixCommandBuilder hystrixCommandBuilder = create(metaHolder,
				Collections.<HystrixCollapser.CollapsedRequest<Object, Object>>emptyList(), argusHystrixProperties);
		if (temp != null) {
			hystrixCommandBuilder.setSetterBuilder(temp.getSetterBuilder());
		}
		cache.put(metaHolder.getCommandKey(), hystrixCommandBuilder);
		return hystrixCommandBuilder;
	}

	public <ResponseType> ArgusHystrixCommandBuilder create(MetaHolder metaHolder, Collection<HystrixCollapser.CollapsedRequest<ResponseType, Object>> collapsedRequests, ArgusHystrixProperties properties) {
		validateMetaHolder(metaHolder);

		return ArgusHystrixCommandBuilder.builder()
				.setterBuilder(createArgusSetterBuilder(metaHolder, properties))
				.commandActions(createCommandActions(metaHolder))
				.collapsedRequests(collapsedRequests)
				.cacheResultInvocationContext(createCacheResultInvocationContext(metaHolder))
				.cacheRemoveInvocationContext(createCacheRemoveInvocationContext(metaHolder))
				.ignoreExceptions(metaHolder.getCommandIgnoreExceptions())
				.executionType(metaHolder.getExecutionType())
				.build();
	}

	private void validateMetaHolder(MetaHolder metaHolder) {
		Validate.notNull(metaHolder, "metaHolder is required parameter and cannot be null");
		Validate.isTrue(metaHolder.isCommandAnnotationPresent(), "hystrixCommand annotation is absent");
	}

	private ArgusSetterBuilder createArgusSetterBuilder(MetaHolder metaHolder, ArgusHystrixProperties properties) {
		ArgusSetterBuilder.Builder setterBuilder = ArgusSetterBuilder.builder()
				.groupKey(metaHolder.getCommandGroupKey())
				.threadPoolKey(metaHolder.getThreadPoolKey())
				.commandKey(metaHolder.getCommandKey())
				.collapserKey(metaHolder.getCollapserKey())
				.commandProperties(metaHolder.getCommandProperties())
				.threadPoolProperties(metaHolder.getThreadPoolProperties())
				.argusHystrixProperties(properties)
				.collapserProperties(metaHolder.getCollapserProperties());
		if (metaHolder.isCollapserAnnotationPresent()) {
			setterBuilder.scope(metaHolder.getHystrixCollapser().scope());
		}
		return setterBuilder.build();
	}

	private CommandActions createCommandActions(MetaHolder metaHolder) {
		CommandAction commandAction = createCommandAction(metaHolder);
		CommandAction fallbackAction = createFallbackAction(metaHolder);
		return CommandActions.builder().commandAction(commandAction)
				.fallbackAction(fallbackAction).build();
	}

	private CommandAction createCommandAction(MetaHolder metaHolder) {
		return new MethodExecutionAction(metaHolder.getObj(), metaHolder.getMethod(), metaHolder.getArgs(), metaHolder);
	}

	private CommandAction createFallbackAction(MetaHolder metaHolder) {

		FallbackMethod fallbackMethod = MethodProvider.getInstance().getFallbackMethod(metaHolder.getObj().getClass(),
				metaHolder.getMethod(), metaHolder.isExtendedFallback());
		fallbackMethod.validateReturnType(metaHolder.getMethod());
		CommandAction fallbackAction = null;
		if (fallbackMethod.isPresent()) {

			Method fMethod = fallbackMethod.getMethod();
			Object[] args = fallbackMethod.isDefault() ? new Object[0] : metaHolder.getArgs();
			if (fallbackMethod.isCommand()) {
				fMethod.setAccessible(true);
				HystrixCommand hystrixCommand = fMethod.getAnnotation(HystrixCommand.class);
				MetaHolder fmMetaHolder = MetaHolder.builder()
						.obj(metaHolder.getObj())
						.method(fMethod)
						.ajcMethod(getAjcMethod(metaHolder.getObj(), fMethod))
						.args(args)
						.fallback(true)
						.defaultFallback(fallbackMethod.isDefault())
						.defaultCollapserKey(metaHolder.getDefaultCollapserKey())
						.fallbackMethod(fMethod)
						.extendedFallback(fallbackMethod.isExtended())
						.fallbackExecutionType(fallbackMethod.getExecutionType())
						.extendedParentFallback(metaHolder.isExtendedFallback())
						.observable(ExecutionType.OBSERVABLE == fallbackMethod.getExecutionType())
						.defaultCommandKey(fMethod.getName())
						.defaultGroupKey(metaHolder.getDefaultGroupKey())
						.defaultThreadPoolKey(metaHolder.getDefaultThreadPoolKey())
						.defaultProperties(metaHolder.getDefaultProperties().orNull())
						.hystrixCollapser(metaHolder.getHystrixCollapser())
						.observableExecutionMode(hystrixCommand.observableExecutionMode())
						.hystrixCommand(hystrixCommand).build();
				fallbackAction = new LazyCommandExecutionAction(fmMetaHolder);
			} else {
				MetaHolder fmMetaHolder = MetaHolder.builder()
						.obj(metaHolder.getObj())
						.defaultFallback(fallbackMethod.isDefault())
						.method(fMethod)
						.fallbackExecutionType(ExecutionType.SYNCHRONOUS)
						.extendedFallback(fallbackMethod.isExtended())
						.extendedParentFallback(metaHolder.isExtendedFallback())
						.ajcMethod(null) // if fallback method isn't annotated with command annotation then we don't need to get ajc method for this
						.args(args).build();

				fallbackAction = new MethodExecutionAction(fmMetaHolder.getObj(), fMethod, fmMetaHolder.getArgs(), fmMetaHolder);
			}

		}
		return fallbackAction;
	}

	private Method getAjcMethod(Object target, Method fallback) {
		if (isCompileWeaving()) {
			return getAjcMethodAroundAdvice(target.getClass(), fallback);
		}
		return null;
	}
}