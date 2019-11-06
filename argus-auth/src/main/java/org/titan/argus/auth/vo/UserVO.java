package org.titan.argus.auth.vo;

import lombok.Data;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.model.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author starboytae
 */
@Data
public class UserVO implements Serializable {
	private String username;

	private String email;

	private Date lastLoginTime;

	private Integer status;

	private List<RoleVO> roleList;

	private List<DeptVO> deptList;
}
