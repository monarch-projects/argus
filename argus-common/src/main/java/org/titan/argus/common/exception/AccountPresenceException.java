package org.titan.argus.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountPresenceException extends RuntimeException {
	private int code;
	private String message;

	public AccountPresenceException(String message) {
		this.code = 501;
		this.message = message;
	}
}
