package org.titan.argus.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.model.Role;
import org.titan.argus.auth.model.TimeQuery;
import org.titan.argus.auth.model.User;
import org.titan.argus.auth.request.AddUserRequest;
import org.titan.argus.auth.request.UpdateUserRequest;
import org.titan.argus.auth.request.QueryUserRequest;
import org.titan.argus.auth.service.UserService;

import org.titan.argus.common.response.ObjectCollectionResponse;
import org.titan.argus.common.response.ObjectDataResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('user-view') or hasAnyAuthority('*')")
	public ObjectCollectionResponse findAllUsers(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			QueryUserRequest userRequest) {
		ModelMapper modelMapper = new ModelMapper();
		User user = new User();
		modelMapper.map(userRequest, user);
		List<Role> roleList = userRequest.getRoles().stream().map(item -> Role.builder().name(item).build())
				.collect(Collectors.toList());
		List<Dept> deptList = userRequest.getDepts().stream().map(item -> Dept.builder().name(item).build())
				.collect(Collectors.toList());
		user.setDeptList(deptList);
		user.setRoleList(roleList);
		TimeQuery timeQuery = new TimeQuery();
		modelMapper.map(userRequest, timeQuery);
		IPage<User> userIPage = this.userService.pageAllUser(user, timeQuery, page, size);
		return new ObjectCollectionResponse<>(userIPage.getRecords());
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('user-update') or hasAnyAuthority('*')")
	public ObjectDataResponse modifyUser(@RequestBody UpdateUserRequest updateUserRequest) {
		this.userService.updateUser(updateUserRequest);
		return new ObjectDataResponse();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('user-add') or hasAnyAuthority('*')")
	public ObjectDataResponse addUser(@RequestBody AddUserRequest addUserRequest) {
		this.userService.addUser(addUserRequest);
		return new ObjectDataResponse();
	}

}
