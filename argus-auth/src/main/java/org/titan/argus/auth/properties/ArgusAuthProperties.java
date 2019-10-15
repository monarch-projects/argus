package org.titan.argus.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author starboyate
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:argus-auth.properties"})
@ConfigurationProperties(prefix = "argus.auth")
public class ArgusAuthProperties {
	private ArgusClientsProperties[] clients = {};
	private int accessTokenValiditySeconds = 60 * 60 * 24;
	private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;
}
