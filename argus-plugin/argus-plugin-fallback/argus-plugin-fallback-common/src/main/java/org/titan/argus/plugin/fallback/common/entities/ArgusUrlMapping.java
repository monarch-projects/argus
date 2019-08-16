package org.titan.argus.plugin.fallback.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArgusUrlMapping<T> {
	private String methodName;

	private String className;

	private String type;

	private String url;

	private Boolean isFallback;

	private T fallbackProperties;
}
