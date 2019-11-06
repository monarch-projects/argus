package org.titan.argus.server.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.titan.argus.common.handler.ArgusAccessDeniedHandler;
import org.titan.argus.common.handler.ArgusAuthExceptionEntryPoint;

/**
 * @author starboyate
 */
@Configuration
@EnableResourceServer
public class ArgusServerResourceServerConfigure extends ResourceServerConfigurerAdapter {
	@Autowired
	private ArgusAccessDeniedHandler accessDeniedHandler;
	@Autowired
	private ArgusAuthExceptionEntryPoint exceptionEntryPoint;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens("/img/**,/json/**,/css/**,/test/**,/api/v1/auth/**,/actuator/**,/swagger-ui.html,/webjars/**,/swagger-resources/**,/v2/api-docs/**,/,/csrf,/v2/**", ",");

		http.csrf().disable()
				.requestMatchers().antMatchers("/**")
				.and()
				.authorizeRequests()
				.antMatchers(anonUrls).permitAll()
				.antMatchers("/**").authenticated();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.authenticationEntryPoint(exceptionEntryPoint).accessDeniedHandler(accessDeniedHandler);
	}
}
