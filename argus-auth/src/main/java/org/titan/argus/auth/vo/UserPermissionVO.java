package org.titan.argus.auth.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@Data
public class UserPermissionVO {
	private List<String> points = new ArrayList<>();


	private List<Menu> menus = new ArrayList<>();

	@EqualsAndHashCode(callSuper = true)
	@Data
	public static class Menu extends Tree<Menu> {
		private String name;

		private String extra;
	}
}
