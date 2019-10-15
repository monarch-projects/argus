package org.titan.argus.auth.enums;

import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public enum UserStatus {
	DISABLE(0, "禁用"),
	AVAILABLE(1, "正常");

	private Integer code;

	private String name;
	UserStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
