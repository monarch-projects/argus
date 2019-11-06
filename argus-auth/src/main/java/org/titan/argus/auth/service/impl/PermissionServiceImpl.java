package org.titan.argus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.titan.argus.auth.mapper.PermissionMapper;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.model.Permission;
import org.titan.argus.auth.service.PermissionService;
import org.titan.argus.auth.util.TreeUtil;
import org.titan.argus.auth.vo.DeptVO;
import org.titan.argus.auth.vo.PermissionVO;
import org.titan.argus.auth.vo.Tree;
import org.titan.argus.auth.vo.UserPermissionVO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Override
	public List<String> findUserPointByUserName(String username) {
		return this.baseMapper.findUserPointByUserName(username);
	}

	@Override
	@Transactional
	public UserPermissionVO findPermissionsByUserName(String username) {
		List<Permission> permissions = this.baseMapper.findUserPermissionByUserName(username);
		return convertToUserPermissionVO(permissions);
	}

	@Override
	@Transactional
	public UserPermissionVO findAllPermission() {
		List<Permission> permissions = this.baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("`order`"));
		return convertToUserPermissionVO(permissions);
	}

	@Override
	public List<? extends Tree> findPermissionTree() {
		List<Permission> permissionList = this.list(new QueryWrapper<Permission>().orderByAsc("`order`"));
		List<PermissionVO> permissionVOList = permissionList.stream().map(permission -> {
			PermissionVO vo = new PermissionVO();
			vo.setId(permission.getId());
			vo.setParentId(permission.getParentId());
			vo.setName(permission.getName());
			vo.setOrder(permission.getOrder());
			vo.setExtra(permission.getExtra());
			return vo;
		}).collect(Collectors.toList());
		return TreeUtil.build(permissionVOList);
	}

	private UserPermissionVO convertToUserPermissionVO(List<Permission> permissions) {
		Map<Long, List<String>> pointMap = new HashMap<>();
		List<Permission> menus = new ArrayList<>(permissions.size());
		permissions.forEach(item -> {
			if (item.getType().equals(0)) {
				menus.add(item);
			} else {
				if (StringUtils.isNotBlank(item.getPoint())){
					if (pointMap.get(item.getParentId()) != null) {
						pointMap.get(item.getParentId()).add(item.getPoint());
					} else {
						List<String> points = new ArrayList<>();
						points.add(item.getPoint());
						pointMap.put(item.getParentId(), points);
					}
				}
			}
		});
		List<UserPermissionVO.Menu> menuList = menus.stream().map(item -> {
			UserPermissionVO.Menu menu = new UserPermissionVO.Menu();
			menu.setName(item.getName());
			menu.setOrder(item.getOrder());
			menu.setId(item.getId());
			menu.setParentId(item.getParentId());
			menu.setPoints(pointMap.get(item.getId()) == null ? new ArrayList<>() : pointMap.get(item.getId()));
			return menu;
		}).collect(Collectors.toList());
		UserPermissionVO userPermissionVO = new UserPermissionVO();
		userPermissionVO.setMenus(TreeUtil.buildMenu(menuList));
		return userPermissionVO;
	}


}
