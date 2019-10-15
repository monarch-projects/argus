package org.titan.argus.auth.enums;

import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public enum  PermissionStatus {
	MENU(0, "菜单"),
	POINT(1, "权限点");

	private Integer code;

	private String name;
	PermissionStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
