package org.titan.argus.plugin.fallback.common.entities;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 * @param <T>
 */
@Data
@Builder
public class ArgusUrlMapping<T> {
	private String methodName;

	private String className;

	private String type;

	private String url;

	private Boolean isFallback;

	private T fallbackProperties;
}
