package org.titan.argus.plugin.fallback.hystrix.core;

import com.netflix.hystrix.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import com.netflix.hystrix.contrib.javanica.exception.HystrixPropertyException;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

import static com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager.initializeCollapserProperties;

/**
 * @author starboyate
 */
public class ArgusSetterBuilder {
	private String groupKey;
	private String commandKey;
	private String threadPoolKey;
	private String collapserKey;
	private HystrixCollapser.Scope scope;
	private List<HystrixProperty> commandProperties = Collections.emptyList();
	private List<HystrixProperty> collapserProperties = Collections.emptyList();
	private List<HystrixProperty> threadPoolProperties = Collections.emptyList();
	private ArgusHystrixProperties properties;

	public ArgusSetterBuilder(ArgusSetterBuilder.Builder builder) {
		this.groupKey = builder.groupKey;
		this.commandKey = builder.commandKey;
		this.threadPoolKey = builder.threadPoolKey;
		this.collapserKey = builder.collapserKey;
		this.scope = builder.scope;
		this.commandProperties = builder.commandProperties;
		this.collapserProperties = builder.collapserProperties;
		this.threadPoolProperties = builder.threadPoolProperties;
		this.properties = builder.properties;
	}

	public static ArgusSetterBuilder.Builder builder(){
		return new ArgusSetterBuilder.Builder();
	}

	public void setProperties(ArgusHystrixProperties properties) {
		this.properties = properties;
	}

	public ArgusHystrixProperties getProperties() {
		return this.properties;
	}


	/**
	 * Creates instance of {@link HystrixCommand.Setter}.
	 *
	 * @return the instance of {@link HystrixCommand.Setter}
	 */
	public HystrixCommand.Setter build() throws HystrixPropertyException {
		HystrixCommand.Setter setter = HystrixCommand.Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
				.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
		if (StringUtils.isNotBlank(threadPoolKey)) {
			setter.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(threadPoolKey));
		}
		try {
			setter.andThreadPoolPropertiesDefaults(HystrixPropertiesManager.initializeThreadPoolProperties(threadPoolProperties));
			if (this.properties != null) {
				setter.andThreadPoolPropertiesDefaults(ArgusHystrixCommandConvert.convertToHystrixThreadPoolProperties(this.properties));
				ArgusHystrixCacheUtil.threadPoolsCacheRemove(this.commandKey);
			}
		} catch (IllegalArgumentException e) {
			throw new HystrixPropertyException("Failed to set Thread Pool properties. " + getInfo(), e);
		}
		try {
			setter.andCommandPropertiesDefaults(HystrixPropertiesManager.initializeCommandProperties(commandProperties));
			if (this.properties != null) {
				setter.andCommandPropertiesDefaults(ArgusHystrixCommandConvert.convertToHystrixCommandProperties(this.properties));
				ArgusHystrixCacheUtil.propertiesCacheReset();
				ArgusHystrixCacheUtil.circuitBreakerCacheReset();
			}
		} catch (IllegalArgumentException e) {
			throw new HystrixPropertyException("Failed to set Command properties. " + getInfo(), e);
		}
		return setter;
	}

	// todo dmgcodevil: it would be better to reuse the code from build() method
	public HystrixObservableCommand.Setter buildObservableCommandSetter() {
		HystrixObservableCommand.Setter setter = HystrixObservableCommand.Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
				.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
		try {
			setter.andCommandPropertiesDefaults(HystrixPropertiesManager.initializeCommandProperties(commandProperties));
		} catch (IllegalArgumentException e) {
			throw new HystrixPropertyException("Failed to set Command properties. " + getInfo(), e);
		}
		return setter;
	}

	public HystrixCollapser.Setter buildCollapserCommandSetter(){
		HystrixCollapserProperties.Setter propSetter = initializeCollapserProperties(collapserProperties);
		return HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey(collapserKey)).andScope(scope)
				.andCollapserPropertiesDefaults(propSetter);
	}

	private String getInfo() {
		return "groupKey: '" + groupKey + "', commandKey: '" + commandKey + "', threadPoolKey: '" + threadPoolKey + "'";
	}


	public static class Builder {
		private String groupKey;
		private String commandKey;
		private String threadPoolKey;
		private String collapserKey;
		private HystrixCollapser.Scope scope;
		private List<HystrixProperty> commandProperties = Collections.emptyList();
		private List<HystrixProperty> collapserProperties = Collections.emptyList();
		private List<HystrixProperty> threadPoolProperties = Collections.emptyList();
		private ArgusHystrixProperties properties;

		public ArgusSetterBuilder.Builder groupKey(String pGroupKey) {
			this.groupKey = pGroupKey;
			return this;
		}

		public ArgusSetterBuilder.Builder groupKey(String pGroupKey, String def) {
			this.groupKey = StringUtils.isNotEmpty(pGroupKey) ? pGroupKey : def;
			return this;
		}

		public ArgusSetterBuilder.Builder commandKey(String pCommandKey) {
			this.commandKey = pCommandKey;
			return this;
		}

		@Deprecated
		public ArgusSetterBuilder.Builder commandKey(String pCommandKey, String def) {
			this.commandKey = StringUtils.isNotEmpty(pCommandKey) ? pCommandKey : def;
			return this;
		}

		public ArgusSetterBuilder.Builder collapserKey(String pCollapserKey) {
			this.collapserKey = pCollapserKey;
			return this;
		}

		public ArgusSetterBuilder.Builder scope(HystrixCollapser.Scope pScope) {
			this.scope = pScope;
			return this;
		}

		public ArgusSetterBuilder.Builder collapserProperties(List<HystrixProperty> properties) {
			collapserProperties = properties;
			return this;
		}

		public ArgusSetterBuilder.Builder commandProperties(List<HystrixProperty> properties) {
			commandProperties = properties;
			return this;
		}


		public ArgusSetterBuilder.Builder threadPoolProperties(List<HystrixProperty> properties) {
			threadPoolProperties = properties;
			return this;
		}

		public ArgusSetterBuilder.Builder threadPoolKey(String pThreadPoolKey) {
			this.threadPoolKey = pThreadPoolKey;
			return this;
		}

		public ArgusSetterBuilder.Builder argusHystrixProperties(ArgusHystrixProperties properties) {
			this.properties = properties;
			return this;
		}

		public ArgusSetterBuilder build(){
			return new ArgusSetterBuilder(this);
		}

	}
}
