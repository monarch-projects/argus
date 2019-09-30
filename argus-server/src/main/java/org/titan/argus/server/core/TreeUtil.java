package org.titan.argus.server.core;

import org.titan.argus.model.entities.Dept;
import org.titan.argus.model.vo.DeptVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
public class TreeUtil {

	public static void generateTree(List<Dept> nodes) {
		if (null == nodes || nodes.isEmpty()) {
		}
		List<DeptVO> deptVOList = nodes.stream().map(Dept::convertToDeptVO).collect(Collectors.toList());

		List<DeptVO> result = new ArrayList<>(nodes.size());
		deptVOList.forEach(item -> {
			Long parentId = item.getParentId();
			if (null == parentId || parentId.equals(0L)) {
				deptVOList.add(item);
			}
			deptVOList.forEach(node -> {
				Long id = node.getId();
				if (id != null && id.equals(parentId)) {
					item.getChildren().add(node);
					return;
				}
			});
		});

	}

}
