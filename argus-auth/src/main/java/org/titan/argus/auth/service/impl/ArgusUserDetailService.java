package org.titan.argus.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.titan.argus.auth.enums.UserStatus;
import org.titan.argus.auth.model.Role;
import org.titan.argus.auth.model.User;
import org.titan.argus.auth.model.auth.AuthUser;
import org.titan.argus.auth.service.PermissionService;
import org.titan.argus.auth.service.RoleService;
import org.titan.argus.auth.service.UserService;

import java.util.List;

/**
 * @author starboyate
 */
@Service
public class ArgusUserDetailService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = this.userService.findByName(s);
		if (user != null) {
			List<String> permissions = this.permissionService.findUserPointByUserName(user.getUsername());
			boolean notLocked = false;
			if (UserStatus.AVAILABLE.getCode().equals(user.getStatus())) {
				notLocked = true;
			}
			List<Role> roleList = this.roleService.findRoleByUserName(user.getUsername());
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList(StringUtils.join(permissions, ","));
			roleList.forEach(item -> {
				if (item.getName().equalsIgnoreCase("admin")) {
					grantedAuthorities.clear();
					grantedAuthorities.add(new SimpleGrantedAuthority("*"));
				}
			});
			AuthUser authUser = new AuthUser(user.getUsername(), user.getPassword(), true, true, true, notLocked,
					grantedAuthorities);
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.map(user, authUser);
			return authUser;
		} else {
			throw new UsernameNotFoundException("请检查用户名是否存在!");
		}
	}
}
