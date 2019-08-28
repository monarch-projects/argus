package org.titan.argus.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArgusUrlMappingVO<T> {
	private String methodName;

	private String className;

	private String type;

	private Class[] parameterTypes;

	private String url;

	private Boolean isFallback;

	private T fallbackProperties;
}
