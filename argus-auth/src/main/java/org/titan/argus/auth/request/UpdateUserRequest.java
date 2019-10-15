package org.titan.argus.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UpdateUserRequest {
	private Long id;

	private String username;

	private String email;

	private List<Long> roles = new ArrayList<>();

	private List<Long> depts = new ArrayList<>();

	private Integer status;
}
