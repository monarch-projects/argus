package org.titan.argus.plugin.fallback.hystrix.web.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author starboyate
 *
 */
public class ArgusFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if(servletRequest instanceof HttpServletRequest) {
			requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
		}
		if(null == requestWrapper) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			filterChain.doFilter(requestWrapper, servletResponse);
		}
	}
}
