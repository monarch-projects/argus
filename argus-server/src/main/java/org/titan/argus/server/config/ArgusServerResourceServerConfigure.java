package org.titan.argus.server.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author starboyate
 */
public class ArgusServerResourceServerConfigure extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.requestMatchers().antMatchers("/**")
				.and()
				.authorizeRequests()
				.antMatchers("/**").authenticated();
	}
}
