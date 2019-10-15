package org.titan.argus.auth.translator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.titan.argus.auth.response.AuthResponse;

/**
 * @author starboyate
 */
@Component
public class ArgusAuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator{
	@Override
	public ResponseEntity translate(Exception e) throws Exception {
		ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		String message = "认证失败";
		if (e instanceof UnsupportedGrantTypeException) {
			message = "不支持该认证类型";
			return status.body(AuthResponse.certification(message));
		}
		if (e instanceof InvalidGrantException) {
			if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
				message = "refresh token无效";
				return status.body(AuthResponse.certification(message));
			}
			if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
				message = "用户已被锁定，请联系管理员";
				return status.body(AuthResponse.certification(message));
			}
			message = "用户名或密码错误";
			return status.body(AuthResponse.certification(message));
		}
		return status.body(AuthResponse.certification(message));
	}
}
