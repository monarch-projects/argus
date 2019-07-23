package org.titan.argus.client.enums;

import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public enum ArgusJvmEnum {
	/**
	 * Eden name
	 */
	EDEN("PS Eden Space"),

	/**
	 * survivor name
	 */
	SURVIVOR("PS Survivor Space"),

	/**
	 * old gen name
	 */
	OLD("PS Old Gen"),

	/**
	 * meta space name
	 */
	META("Metaspace"),

	/**
	 * compressed class space name
	 */
	COMPRESSED("Compressed Class Space"),

	/**
	 * code cache name
	 */
	CODE("Code Cache");

	private String name;

	ArgusJvmEnum(String name) {
		this.name = name;
	}
}
