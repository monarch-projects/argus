package org.titan.argus.client.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public enum  ArgusOsEnum {
	/**
	 * windows
	 */
	WINDOWS("windows"),

	/**
	 * linux
	 */
	LINUX("linux");

	private String name;

	ArgusOsEnum(String name) {
		this.name = name;
	}
}
