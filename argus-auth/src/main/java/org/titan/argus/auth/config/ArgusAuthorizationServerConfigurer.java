package org.titan.argus.auth.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.titan.argus.auth.translator.ArgusAuthWebResponseExceptionTranslator;
import org.titan.argus.auth.properties.ArgusAuthProperties;
import org.titan.argus.auth.properties.ArgusClientsProperties;
import org.titan.argus.auth.service.impl.ArgusUserDetailService;

/**
 * @author starboyate
 *  认证服务器安全配置类
 */
@Configuration
@EnableAuthorizationServer
public class ArgusAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	@Autowired
	private ArgusUserDetailService userDetailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ArgusAuthProperties argusAuthProperties;

	@Autowired
	private ArgusAuthWebResponseExceptionTranslator exceptionTranslator;

	/**
	 * 1. 客户端从认证服务器获取令牌的时候，必须使用client_id为argus，client_secret为123456的标识来获取
	 * 2. 该client_id支持password模式获取令牌，并且可以通过refresh_token来获取新的令牌
	 * 3. 在获取client_id为argus的令牌的时候，scope只能指定为all，否则将获取失败
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		ArgusClientsProperties[] clientArray =argusAuthProperties.getClients();
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		if (ArrayUtils.isNotEmpty(clientArray)) {
			for (ArgusClientsProperties properties : clientArray) {
				if (StringUtils.isBlank(properties.getClient())) {
					throw new Exception("client不能为空");
				}
				if (StringUtils.isBlank(properties.getSecret())) {
					throw new Exception("secret不能为空");
				}
				String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getGrantType(), ",");
				builder.withClient(properties.getClient())
						.secret(passwordEncoder.encode(properties.getSecret()))
						.authorizedGrantTypes(grantTypes)
						.scopes(properties.getScope());
			}
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore())
				.userDetailsService(userDetailService)
				.authenticationManager(authenticationManager)
				.tokenServices(defaultTokenServices())
				.exceptionTranslator(exceptionTranslator);
	}

	/**
	 * 使用redis存储token
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}

	@Primary
	@Bean
	public DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		// 令牌有效时间
		tokenServices.setAccessTokenValiditySeconds(argusAuthProperties.getAccessTokenValiditySeconds());
		// 令牌刷新时间
		tokenServices.setRefreshTokenValiditySeconds(argusAuthProperties.getRefreshTokenValiditySeconds());
		return tokenServices;
	}

}
