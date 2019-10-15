package org.titan.argus.auth.handler;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.titan.argus.auth.response.AuthResponse;
import org.titan.argus.auth.util.ArgusResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author starboyate
 */
@Component
public class ArgusAuthExceptionEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		ArgusResponseUtil.makeResponse(httpServletResponse,
				MediaType.APPLICATION_JSON_UTF8_VALUE,
				HttpServletResponse.SC_UNAUTHORIZED,
				AuthResponse.certification("token无效"));
	}
}
