package org.titan.argus.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class RoleVO {
	private Long id;

	private String name;

	private String description;
}
