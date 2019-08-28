package org.titan.argus.tools.alarm.enums;

/**
 * @author starboyate
 */
public enum  AlarmEventTypeEnum {
	REGISTER("服务注册上线"),
	OFFLINE("服务下线"),
	REDIS("redis异常"),
	MONGODB("mongodb异常"),
	UNKNOWN("未知");
	private String name;

	AlarmEventTypeEnum(String name) {
		this.name = name;
	}
}
