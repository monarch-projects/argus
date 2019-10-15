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
public class DeptRequest {
	private Long id;

	private Long parentId;

	private Integer order;

	private String name;
}
