package org.titan.argus.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.titan.argus.auth.handler.ArgusAccessDeniedHandler;
import org.titan.argus.auth.handler.ArgusAuthExceptionEntryPoint;

/**
 * @author starboyate
 * 资源服务器的配置类
 * 用于处理非/oauth/开头的请求，其主要用于资源的保护，客户端只能通过OAuth2协议发放的令牌来从资源服务器中获取受保护的资源
 */
@Configuration
@EnableResourceServer
public class ArgusResourceServerConfigure extends ResourceServerConfigurerAdapter {

	@Autowired
	private ArgusAccessDeniedHandler accessDeniedHandler;
	@Autowired
	private ArgusAuthExceptionEntryPoint exceptionEntryPoint;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.authenticationEntryPoint(exceptionEntryPoint).accessDeniedHandler(accessDeniedHandler);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.requestMatchers().antMatchers("/**")
				.and()
				.authorizeRequests()
				.antMatchers("/actuator/**").permitAll()
				.antMatchers("/**")
				.authenticated();
	}
}
