package org.titan.argus.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
	private Long id;

	private String name;

	private String description;

	private List<Long> permissions;
}
