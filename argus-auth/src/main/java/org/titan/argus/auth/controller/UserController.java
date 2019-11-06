package org.titan.argus.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.model.Role;
import org.titan.argus.auth.model.TimeQuery;
import org.titan.argus.auth.model.User;
import org.titan.argus.auth.request.AddUserRequest;
import org.titan.argus.auth.request.UpdateUserRequest;
import org.titan.argus.auth.request.QueryUserRequest;
import org.titan.argus.auth.service.UserService;

import org.titan.argus.auth.vo.DeptVO;
import org.titan.argus.auth.vo.RoleVO;
import org.titan.argus.auth.vo.UserVO;
import org.titan.argus.common.exception.BusinessException;
import org.titan.argus.common.response.ObjectCollectionResponse;
import org.titan.argus.common.response.ObjectDataResponse;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(value = "用户信息管理接口", tags = {"用户信息管理接口"})
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@ApiOperation(value = "查询用户信息", notes = "查询所有用户信息分页显示")
	@GetMapping
	@PreAuthorize("hasAnyAuthority('user-view')")
	public ObjectCollectionResponse<User> findAllUsers(
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

	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@PutMapping
	@PreAuthorize("hasAnyAuthority('user-update')")
	public ObjectDataResponse modifyUser(@RequestBody UpdateUserRequest updateUserRequest) {
		this.userService.updateUser(updateUserRequest);
		return new ObjectDataResponse();
	}

	@ApiOperation(value = "添加用户", notes = "添加用户")
	@PostMapping
	@PreAuthorize("hasAnyAuthority('user-add')")
	public ObjectDataResponse addUser(@RequestBody AddUserRequest addUserRequest) {
		this.userService.addUser(addUserRequest);
		return new ObjectDataResponse();
	}

	@ApiOperation(value = "退出登录", notes = "退出登录")
	@DeleteMapping("/logout")
	public ObjectDataResponse signout(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String token = StringUtils.replace(authorization, "bearer ", "");
		if (!consumerTokenServices.revokeToken(token)) {
			throw new BusinessException("退出登录失败");
		}
		return new ObjectDataResponse<>("退出登录成功");
	}

	@ApiOperation(value = "查询当前登录用户具体信息", notes = "查询当前登录用户具体信息")
	@GetMapping("/detail")
	public ObjectDataResponse getUserDetail(Principal principal) {
		String name = principal.getName();
		User user = this.userService.findByName(name);
		ModelMapper modelMapper = new ModelMapper();
		UserVO userVO = modelMapper.map(user, UserVO.class);
		List<DeptVO> deptVOS = new ArrayList<>(user.getDeptList().size());
		List<RoleVO> roleVOList = user.getRoleList().stream().map(item -> modelMapper.map(item, RoleVO.class))
				.collect(Collectors.toList());
		List<DeptVO> deptVOList = user.getDeptList().stream().map(item -> modelMapper.map(item, DeptVO.class))
				.collect(Collectors.toList());
		userVO.setDeptList(deptVOList);
		userVO.setRoleList(roleVOList);
		return new ObjectDataResponse<>(userVO);
	}

}
