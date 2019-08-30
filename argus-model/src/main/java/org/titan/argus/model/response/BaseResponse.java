package org.titan.argus.model.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author starboyate
 * 统一返回信息
 */
@Builder
@Data
public class BaseResponse {
	private Integer code;

	private Object data;

	private String message;

	public static BaseResponse success(Object data) {
		return BaseResponse.builder()
				.code(HttpStatus.OK.value())
				.data(data)
				.message(HttpStatus.OK.getReasonPhrase())
				.build();
	}

	public static BaseResponse notFount(Object data) {
		return BaseResponse.builder()
				.code(HttpStatus.NOT_FOUND.value())
				.data(data)
				.message(HttpStatus.NOT_FOUND.getReasonPhrase())
				.build();
	}
	public static BaseResponse error(Object data, String message) {
		return BaseResponse.builder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.data(data)
				.message(message)
				.build();
	}
	public static BaseResponse error(Object data) {
		return error(data, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}

	public static BaseResponse error(String message) {
		return error(null, message);
	}
}
