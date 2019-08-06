package org.titan.argus.discovery.common.enums;

import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public enum  DiscoveryEventEnum {

	REGISTER("服务注册上线"),
	OFFLINE("服务下线"),
	UNKNOWN("未知");

	private String name;

	DiscoveryEventEnum(String name) {
		this.name = name;
	}
}
