package org.titan.argus.plugin.route.core;

import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public class ArgusRefreshRouteEventHolder<T> {

	public T getEvent() {
		return (T) new Object();
	}
}
