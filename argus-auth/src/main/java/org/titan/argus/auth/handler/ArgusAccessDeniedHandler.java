package org.titan.argus.auth.handler;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class ArgusAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException, ServletException {
		ArgusResponseUtil.makeResponse(httpServletResponse,
				MediaType.APPLICATION_JSON_UTF8_VALUE,
				HttpServletResponse.SC_FORBIDDEN,
				AuthResponse.certification("无权访问该资源"));
	}
}
