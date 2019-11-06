package org.titan.argus.common.response;

import lombok.Data;

/**
 * @author starboyate
 */
@Data
public final class AuthResponse {
	private int code;

	private String msg;

	private Object data;

	private AuthResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private AuthResponse(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static AuthResponse certification(String msg) {
		return new AuthResponse(401, msg);
	}

	public static AuthResponse forbidden(String msg) {
		return new AuthResponse(403, msg);
	}

	public static AuthResponse success(Object data) {
		return new AuthResponse(200, "ok", data);
	}

	public static AuthResponse success(String msg) {
		return new AuthResponse(200, msg);
	}

}
