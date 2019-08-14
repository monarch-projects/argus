package org.titan.argus.tools.alarm.enums;


import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public enum  AlarmTypeEnum {
	EMAIL("email"),
	SMS("sms");

	private String name;
	AlarmTypeEnum(String name) {
		this.name = name;
	}
}
