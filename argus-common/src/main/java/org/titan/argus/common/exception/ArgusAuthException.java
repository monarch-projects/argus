package org.titan.argus.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArgusAuthException extends RuntimeException {
	private int code;
	private String message;

	public ArgusAuthException(String message) {
		this.code = 403;
		this.message = message;
	}
}
