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

	public static List<DeptVO> generateTree(List<Dept> nodes) {
		if (null == nodes || nodes.isEmpty()) {
		}
		List<DeptVO> deptVOList = nodes.stream().map(Dept::convertToDeptVO).collect(Collectors.toList());

		List<DeptVO> result = new ArrayList<>();
		deptVOList.forEach(item -> {
			Long parentId = item.getParentId();
			if (null == parentId || parentId.equals(0L)) {
				result.add(item);
				return;
			}
			for (DeptVO node : deptVOList) {
				Long id = node.getId();
				if (id != null && id.equals(parentId)) {
					item.getChildren().add(node);
					return;
				}
			}
		});
		return result;

	}

}
