package org.titan.argus.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.titan.argus.auth.service.impl.ArgusUserDetailService;

/**
 * @author starboyate
 *  安全配置类,用于处理/oauth 开头的请求，Spring Cloud OAuth内部定义的获取令牌，刷新令牌的请求地址都是以/oauth/开头的，
 *  也就是说ArgusSecurityConfigure用于处理和令牌相关的请求
 */
@Order(1)
@EnableWebSecurity
public class ArgusSecurityConfigure extends WebSecurityConfigurerAdapter {
	@Autowired
	private ArgusUserDetailService userDetailService;

	/**
	 * 密码加密校验工具类
	 * BCryptPasswordEncoder 特点：
	 * 对于一个相同的密码，每次加密出来的加密串都不同
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
				.antMatchers("/oauth/**")
				.and()
				.authorizeRequests()
				.antMatchers("/oauth/**").authenticated()
				.and()
				.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}
}
