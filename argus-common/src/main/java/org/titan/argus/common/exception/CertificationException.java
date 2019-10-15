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
public class CertificationException extends RuntimeException {
	private int code;

	private String message;

	public CertificationException(String message) {
		this.code = 401;
		this.message = message;
	}
}
