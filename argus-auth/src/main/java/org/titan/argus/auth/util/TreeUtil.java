package org.titan.argus.auth.util;

import org.springframework.util.CollectionUtils;
import org.titan.argus.auth.vo.DeptVO;
import org.titan.argus.auth.vo.Tree;
import org.titan.argus.auth.vo.UserPermissionVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
public class TreeUtil {

	public static List<UserPermissionVO.Menu> buildMenu(List<UserPermissionVO.Menu> menuList) {
		if (CollectionUtils.isEmpty(menuList)) {
			return null;
		}
		List<UserPermissionVO.Menu> menus = new ArrayList<>();
		menuList.forEach(item -> {
			Long parentId = item.getParentId();
			if (parentId == null || parentId.equals(0L)) {
				menus.add(item);
				return;
			}
			for (UserPermissionVO.Menu menu : menuList) {
				Long id = menu.getId();
				if (id != null && id.equals(parentId)) {
					menu.getChildren().add(item);
					return;
				}
			}
		});
		return menus;
	}


	public static<T> List<? extends Tree<T>> build(List<? extends Tree<T>> trees) {
		if (CollectionUtils.isEmpty(trees)) {
			return null;
		}
		List<Tree<T>> result = new ArrayList<>();
		trees.forEach(item -> {
			Long parentId = item.getParentId();
			if (parentId == null || parentId.equals(0L)) {
				result.add(item);
				return;
			}
			for (Tree<T> vo : trees) {
				Long id = vo.getId();
				if (id != null && id.equals(parentId)) {
					vo.getChildren().add(item);
					return;
				}
			}
			if (result.isEmpty()) {
				result.add(item);
			}
		});
		return result;
	}
}
