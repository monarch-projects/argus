package org.titan.argus.tools.monitor.redis.domain;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author starboyate
 */
@Getter
public enum  RedisModeEnum {
	STAND_ALONE("standalone", 0),
	SENTINEL("sentinel", 1),
	CLUSTER("cluster", 2);
	private String name;
	private Integer code;
	RedisModeEnum(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	public static Integer getCodeByName(String name) {
		return Arrays.stream(RedisModeEnum.values()).filter(item -> item.name.equals(name)).findFirst().orElse(null).getCode();
	}
}
