package org.titan.argus.model.entities;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

/**
 * @author starboyate
 */
@Data
public class User implements UserDetails, Serializable {
	private Long id;
	private String username;
	private String password;
	private List<Role> authorities;

	@Override
	public List<Role> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

	/**
	 * 用户账号是否过期
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 用户账号是否被锁定
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 用户密码是否过期
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 用户是否可用
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}