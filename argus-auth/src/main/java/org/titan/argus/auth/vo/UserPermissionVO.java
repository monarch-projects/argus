package org.titan.argus.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@Data
@ApiModel(description = "用户权限信息")
public class UserPermissionVO {

	@ApiModelProperty(value="菜单栏", name="menus")
	private List<Menu> menus = new ArrayList<>();

	@EqualsAndHashCode(callSuper = true)
	@Data
	@ApiModel(description = "菜单信息")
	public static class Menu extends Tree<Menu> {
		@ApiModelProperty(value="菜单唯一标识", name="name")
		private String name;

		@ApiModelProperty(value="扩展信息", name="name", example="用户管理")
		private String extra;

		@ApiModelProperty(value="权限点", name="ponits")
		private List<String> points = new ArrayList<>();
	}



}
