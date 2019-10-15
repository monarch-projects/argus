package org.titan.argus.auth.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryUserRequest {
	private String username;

	private String email;

	private Integer status;

	private List<String> roles = new ArrayList<>();

	private List<String> depts = new ArrayList<>();

	private Date startTime;

	private Date endTime;

}
