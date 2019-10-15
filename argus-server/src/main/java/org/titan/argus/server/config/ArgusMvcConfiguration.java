package org.titan.argus.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.titan.argus.common.utils.ArgusVisitsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: ArgusMvcConfiguration
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Configuration
@Slf4j
public class ArgusMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                log.info("接收到http请求, method:{}  uri:{}", request.getMethod(), request.getRequestURI());

                return true;
            }
        }).addPathPatterns("/**");
        registry.addInterceptor(new HandlerInterceptor() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				ArgusVisitsUtil.incr();
				return true;
			}
		}).addPathPatterns("/**");
    }
}
