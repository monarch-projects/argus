package org.titan.argus.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	private String username;

	private String password;
}
