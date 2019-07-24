package org.titan.argus.model.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author starboyate
 */
@Getter
public enum  StatusCode {
	SUCCESS(200, "success!"),
	NOT_FOUNT(404, "not fount!"),
	ERROR(500, "error!");
	private Integer code;

	private String message;

	StatusCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
