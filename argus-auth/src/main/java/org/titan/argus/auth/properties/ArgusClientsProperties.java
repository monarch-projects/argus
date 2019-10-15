package org.titan.argus.auth.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArgusClientsProperties {
	private String client;

	private String secret;

	private String grantType = "password,authorization_code,refresh_token";

	private String scope = "all";
}
