package org.titan.argus.model.response;

import lombok.Builder;
import lombok.Data;
import org.titan.argus.model.enums.StatusCode;

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
				.code(StatusCode.SUCCESS.getCode())
				.data(data)
				.message(StatusCode.SUCCESS.getMessage())
				.build();
	}

	public static BaseResponse notFount(Object data) {
		return BaseResponse.builder()
				.code(StatusCode.NOT_FOUNT.getCode())
				.data(data)
				.message(StatusCode.NOT_FOUNT.getMessage())
				.build();
	}
	public static BaseResponse error(Object data, String message) {
		return BaseResponse.builder()
				.code(StatusCode.ERROR.getCode())
				.data(data)
				.message(message)
				.build();
	}
	public static BaseResponse error(Object data) {
		return error(data, StatusCode.ERROR.getMessage());
	}

	public static BaseResponse error(String message) {
		return error(null, message);
	}
}
