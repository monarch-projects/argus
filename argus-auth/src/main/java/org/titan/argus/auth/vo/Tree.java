package org.titan.argus.auth.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@Data
public class Tree<T> {
	private Long id;

	private Long parentId;

	private Integer order;

	private List<Tree<T>> children = new ArrayList<>();
}
